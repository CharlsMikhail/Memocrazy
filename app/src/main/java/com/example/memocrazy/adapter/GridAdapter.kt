package com.example.memocrazy.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.memocrazy.R
import com.example.memocrazy.utils.KEY_SCORE
import kotlin.properties.Delegates

/**
 * @problemDescription Grilla encargada de manejar logica del juego en si.
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 06/07/24
 */
class GridAdapter(
    private val mContext: Context,
    private val timeTextView: TextView,
    private val scoreTextView: TextView,
    private val images: List<Int>,
    private val username: String
) : BaseAdapter() {

    // Atributos
    private var puntuacion = 0
    private var tiempoMaximo = 180000L // 3 minutos en milisegundos
    private var tiempoInicio = 0L
    private var tiempoFinal = 0L
    private var tiempoTranscurrido = 0L
    private var ganoPartida = false
    private var unaCartaLevantada = false
    private var dosCartas: Pair<ImageView?, ImageView?> = Pair(null, null)
    var primeraCartaImagen by Delegates.notNull<Int>()
    var segundaCartaImagen by Delegates.notNull<Int>()
    private lateinit var countDownTimer: CountDownTimer

    private val mGridItems = images + images // Duplicar las imágenes para el juego de memoria

    // Lista de recursos de imagen proporcionados por Android
    private val mThumbIds = MutableList(20) { android.R.drawable.ic_menu_help }

    init {
        startTimer()
    }

    override fun getCount(): Int {
        return mThumbIds.size
    }

    override fun getItem(position: Int): Any {
        return mThumbIds[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = ImageView(mContext)
            imageView.layoutParams = ViewGroup.LayoutParams(210, 240)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(2, 3, 2, 3)
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(mThumbIds[position])

        imageView.setOnClickListener {
            if (!unaCartaLevantada) {
                primeraCartaImagen = mGridItems[position]
                unaCartaLevantada = true
                animateCardFlipFirstHalf(imageView, primeraCartaImagen, parent!!)
                dosCartas = Pair(imageView, null)
            } else {
                segundaCartaImagen = mGridItems[position]
                animateCardFlipFirstHalf(imageView, segundaCartaImagen, parent!!)
                dosCartas = Pair(dosCartas.first, imageView)
                toggleClickability(parent!!, false) // Disable clicks during validation
                Handler(Looper.getMainLooper()).postDelayed({
                    if (validarJugada(primeraCartaImagen, segundaCartaImagen)) {
                        unaCartaLevantada = false
                        dosCartas.first!!.isEnabled = false
                        dosCartas.second!!.isEnabled = false
                        updateScore()
                    } else {
                        animateCardFlipFirstHalf(dosCartas.first!!, android.R.drawable.ic_menu_help, parent)
                        animateCardFlipFirstHalf(dosCartas.second!!, android.R.drawable.ic_menu_help, parent)
                        dosCartas.first!!.isEnabled = true
                        dosCartas.second!!.isEnabled = true
                        unaCartaLevantada = false
                    }
                    toggleClickability(parent, true) // Re-enable clicks after validation
                }, 500) // Espera 2000ms antes de validar la jugada
                if (puntuacion == 9) {
                    ganoPartida = true
                    endGame()
                }
            }
        }
        return imageView
    }

    private fun validarJugada(imageResId1: Int, imageResId2: Int): Boolean {
        return if (imageResId1 == imageResId2) {
            puntuacion++
            Toast.makeText(mContext, "CORRECTO", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(mContext, "INCORRECTO", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun updateScore() {
        scoreTextView.text = "Score: $puntuacion"
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(tiempoMaximo, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                scoreTextView.text = "Score: $puntuacion"
                timeTextView.text = "Time: $secondsRemaining"
            }

            override fun onFinish() {
                if (!ganoPartida) {
                    puntuacion = 0
                    endGame()
                }
            }
        }.start()
    }

    private fun endGame() {
        countDownTimer.cancel()
        Toast.makeText(mContext, if (ganoPartida) "GANASTE" else "PERDISTE", Toast.LENGTH_SHORT).show()
        val delivery = Bundle().apply {
            putInt(KEY_SCORE, puntuacion)
            putString("username", username)
        }
        scoreTextView.findNavController().navigate(R.id.action_gameFragment_to_scoreFragment, delivery)
    }

    // Función para animar solo la primera mitad del volteo de la carta
    private fun animateCardFlipFirstHalf(imageView: ImageView, newImageResId: Int, parent: ViewGroup) {
        // Primera mitad de la animación (girar a 90 grados)
        val animatorOut = AnimatorInflater.loadAnimator(imageView.context, R.animator.card_flip_out) as AnimatorSet
        val animatorIn = AnimatorInflater.loadAnimator(imageView.context, R.animator.card_flip_in) as AnimatorSet

        animatorOut.setTarget(imageView)
        animatorIn.setTarget(imageView)

        animatorOut.start()
        animatorOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                imageView.setImageResource(newImageResId)
                animatorIn.start()
            }
        })
    }

    // Función para bloquear y desbloquear clics
    fun toggleClickability(parent: ViewGroup, enable: Boolean) {
        parent.isClickable = enable
        for (i in 0 until parent.childCount) {
            parent.getChildAt(i).isClickable = enable
        }
    }
}
