package com.example.memocrazy.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
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
 * Adaptador para gestionar la lógica del juego de memoria en una grilla de imágenes.
 *
 * @param mContext Contexto de la aplicación.
 * @param timeTextView TextView donde se muestra el tiempo restante.
 * @param scoreTextView TextView donde se muestra la puntuación actual.
 * @param images Lista de recursos de imágenes para el juego.
 * @param username Nombre de usuario del jugador.
 */
class GridAdapter(
    private val mContext: Context,
    private val timeTextView: TextView,
    private val scoreTextView: TextView,
    private val images: List<Int>,
    private val username: String
) : BaseAdapter() {

    // Atributos del juego
    private var puntuacion = 0
    private var tiempoMaximo = 180000L // 3 minutos en milisegundos
    private var ganoPartida = false
    private var unaCartaLevantada = false
    private var dosCartas: Pair<ImageView?, ImageView?> = Pair(null, null)
    var primeraCartaImagen by Delegates.notNull<Int>()
    var segundaCartaImagen by Delegates.notNull<Int>()
    private lateinit var countDownTimer: CountDownTimer

    // Duplicar las imágenes para el juego de memoria
    private val mGridItems = images + images

    // Lista de recursos de imagen predeterminados por Android
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
            // Crear una nueva ImageView si no está reciclada
            imageView = ImageView(mContext)
            imageView.layoutParams = ViewGroup.LayoutParams(210, 240)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(2, 3, 2, 3)
        } else {
            // Reutilizar la ImageView existente
            imageView = convertView as ImageView
        }

        imageView.setImageResource(mThumbIds[position])

        imageView.setOnClickListener {
            if (!unaCartaLevantada) {
                primeraCartaImagen = mGridItems[position]
                unaCartaLevantada = true
                dosCartas = Pair(imageView, null)
                dosCartas.first!!.isEnabled = false
                animateCardFlipFirstHalf(imageView, primeraCartaImagen, parent!!)
            } else {
                segundaCartaImagen = mGridItems[position]
                animateCardFlipFirstHalf(imageView, segundaCartaImagen, parent!!)
                dosCartas = Pair(dosCartas.first, imageView)
                toggleClickability(parent, false) // Deshabilitar clics durante la validación
                Handler(Looper.getMainLooper()).postDelayed({
                    if (validarJugada(primeraCartaImagen, segundaCartaImagen)) {
                        unaCartaLevantada = false
                        dosCartas.first!!.setBackgroundColor(Color.GREEN)
                        dosCartas.second!!.setBackgroundColor(Color.GREEN)
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
                    toggleClickability(parent, true) // Re-habilitar clics después de la validación
                }, 500) // Esperar 500ms antes de validar la jugada

                // Verificar si se completaron todas las jugadas
                if (puntuacion == 9) {
                    ganoPartida = true
                    endGame()
                }
            }
        }
        return imageView
    }

    // Validar si las dos cartas seleccionadas son iguales
    private fun validarJugada(imageResId1: Int, imageResId2: Int): Boolean {
        return if (imageResId1 == imageResId2) {
            puntuacion++
            reproducirSonidoExito()
            true
        } else {
            reproducirSonidoError()
            false
        }
    }

    // Actualizar la puntuación mostrada en TextView
    private fun updateScore() {
        scoreTextView.text = "Score: $puntuacion"
    }

    // Iniciar el temporizador del juego
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

    // Finalizar el juego cuando se agota el tiempo o se completa
    private fun endGame() {
        countDownTimer.cancel()
        Toast.makeText(mContext, if (ganoPartida) "¡GANASTE!" else "¡PERDISTE!", Toast.LENGTH_SHORT).show()
        val delivery = Bundle().apply {
            putInt(KEY_SCORE, puntuacion)
            putString("username", username)
        }
        scoreTextView.findNavController().navigate(R.id.action_gameFragment_to_scoreFragment, delivery)
    }

    // Animar solo la primera mitad del volteo de la carta
    private fun animateCardFlipFirstHalf(imageView: ImageView, newImageResId: Int, parent: ViewGroup) {
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

    // Habilitar o deshabilitar clics en todas las ImageView dentro del GridView
    fun toggleClickability(parent: ViewGroup, enable: Boolean) {
        parent.isClickable = enable
        for (i in 0 until parent.childCount) {
            parent.getChildAt(i).isClickable = enable
        }
    }

    // Reproducir sonido de éxito cuando se encuentra una pareja de cartas
    fun reproducirSonidoExito() {
        val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 200)
    }

    // Reproducir sonido de error cuando no coinciden las cartas seleccionadas
    fun reproducirSonidoError() {
        val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
        toneGen.startTone(ToneGenerator.TONE_SUP_ERROR, 200)
    }

}
