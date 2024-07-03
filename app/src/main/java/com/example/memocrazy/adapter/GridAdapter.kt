package com.example.memocrazy.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.memocrazy.R

/**
 * @problemDescription Es el encargado de mostrar una cuadrícula de imágenes en un GridView.
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class GridAdapter(private val mContext: Context) : BaseAdapter() {

    // Atributos
    private var puntuacion = 0
    private var tiempoMaximo = 0
    private var tiempoInicio = 0L
    private var tiempoFinal = 0L
    private var tiempoTranscurrido = 0L
    private var ganoPartida = false
    private var unaCartaLevantada = false
    private var dosCartas: Pair<ImageView?, ImageView?> = Pair(null, null)

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
            val newImageResId = mGridItems[position]
            parent!!.isEnabled = false
            animateImageView(parent, imageView, newImageResId)
            Toast.makeText(mContext, "Hola", Toast.LENGTH_SHORT).show()
        }

        return imageView
    }

    private fun validarJugada(): Boolean{
        if (dosCartas.first?.drawable?.constantState == dosCartas.second?.drawable?.constantState) {
            puntuacion++
            Toast.makeText(mContext, "CORRECTO", Toast.LENGTH_SHORT).show()
            return true
        }
        else {
            Toast.makeText(mContext, "INCORRECTO", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // Función para animar el ImageView y bloquear clics temporalmente
    private fun animateImageView(parent: ViewGroup, imageView: ImageView, newImageResId: Int, duration: Long = 2000L) {
        // Bloquear clics
        toggleClickability(parent, false)

        // Primera mitad de la animación (girar a 90 grados)
        val animatorOut = AnimatorInflater.loadAnimator(imageView.context, R.animator.card_flip_out) as AnimatorSet
        // Segunda mitad de la animación (girar de -90 a 0 grados)
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

        // Usar un Handler para volver a la imagen original después de un tiempo y desbloquear clics
        Handler(Looper.getMainLooper()).postDelayed({
            animatorOut.start()
            animatorOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    imageView.setImageResource(android.R.drawable.ic_menu_help)
                    animatorIn.start()

                    // Desbloquear clics después de que la animación haya terminado
                    Handler(Looper.getMainLooper()).postDelayed({
                        toggleClickability(parent, true)
                    }, animatorIn.totalDuration)
                }
            })
        }, duration)
    }

    // Función para bloquear y desbloquear clics
    fun toggleClickability(parent: ViewGroup, enable: Boolean) {
        parent.isClickable = enable
        for (i in 0 until parent.childCount) {
            parent.getChildAt(i).isClickable = enable
        }
    }


}
