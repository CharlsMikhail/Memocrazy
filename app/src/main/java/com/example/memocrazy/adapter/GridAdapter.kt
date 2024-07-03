package com.example.memocrazy.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
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
import kotlin.properties.Delegates

/**
 * @problemDescription Es el encargado de mostrar una cuadrícula de imágenes en un GridView.
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class GridAdapter(private val mContext: Context, private val scoreTextView: TextView) : BaseAdapter() {

    // Atributos
    private var puntuacion = 0
    private var tiempoMaximo = 0
    private var tiempoInicio = 0L
    private var tiempoFinal = 0L
    private var tiempoTranscurrido = 0L
    private var ganoPartida = false
    private var unaCartaLevantada = false
    private var dosCartas: Pair<ImageView?, ImageView?> = Pair(null, null)
    var primeraCartaImagen by Delegates.notNull<Int>()
    var segundaCartaImagen by Delegates.notNull<Int>()

    private val mGridItems = arrayOf(
        android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_gallery,
        android.R.drawable.ic_menu_manage,
        android.R.drawable.ic_menu_search,
        android.R.drawable.ic_menu_send,
        android.R.drawable.ic_menu_view,
        android.R.drawable.ic_menu_zoom,
        android.R.drawable.ic_media_ff,
        android.R.drawable.ic_media_next,
        android.R.drawable.ic_media_pause,
        android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_gallery,
        android.R.drawable.ic_menu_manage,
        android.R.drawable.ic_menu_search,
        android.R.drawable.ic_menu_send,
        android.R.drawable.ic_menu_view,
        android.R.drawable.ic_menu_zoom,
        android.R.drawable.ic_media_ff,
        android.R.drawable.ic_media_next,
        android.R.drawable.ic_media_pause
    )

    // Lista de recursos de imagen proporcionados por Android
    private val mThumbIds = MutableList(20) { android.R.drawable.ic_menu_help }

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
            imageView.isEnabled = false

            if (!unaCartaLevantada) {
                primeraCartaImagen = mGridItems[position]
                unaCartaLevantada = true
                animateCardFlipFirstHalf(imageView, primeraCartaImagen, parent!!)
                dosCartas = Pair(imageView, null)
            } else {
                segundaCartaImagen = mGridItems[position]
                animateCardFlipFirstHalf(imageView, segundaCartaImagen, parent!!)
                dosCartas = Pair(dosCartas.first, imageView)
                Handler(Looper.getMainLooper()).postAtTime({
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
                },
                    500) // Espera 900ms antes de validar la jugada
                if (puntuacion == 9) {
                    Toast.makeText(mContext, "GANASTE", Toast.LENGTH_SHORT).show()
                    scoreTextView.findNavController().navigate(R.id.action_gameFragment_to_scoreFragment)
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


    // Función para animar solo la primera mitad del volteo de la carta
    private fun animateCardFlipFirstHalf(imageView: ImageView, newImageResId: Int, parent: ViewGroup) {
        // Bloquear clics
        toggleClickability(parent, false)

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
                // Desbloquear clics después de que la animación haya terminado
                toggleClickability(parent, true)
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
