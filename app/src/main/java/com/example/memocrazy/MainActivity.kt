package com.example.memocrazy

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * Esta es la clase main quien inicia todo la logica del juego de memoria.
 * @autor Carlos Mijail Mamani Anccasi
 * @creacionFecha 19/06/24
 * @ultimaModificacion 06/07/24
 */

/**
 * Actividad principal de la aplicación Memocrazy.
 *
 * @property mediaPlayer Reproductor de música de fondo.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    /**
     * Se llama cuando se crea la actividad. Configura el contenido de la actividad y
     * inicia la reproducción de música de fondo.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciarMusica()
    }

    /**
     * Inicia la reproducción de la música de fondo. La música se reproduce en bucle.
     */
    private fun iniciarMusica() {
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_fondo)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    /**
     * Detiene la reproducción de la música cuando la actividad entra en pausa.
     */
    private fun pararMusica() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    /**
     * Detiene la reproducción de la música cuando la actividad se destruye.
     */
    override fun onDestroy() {
        super.onDestroy()
        pararMusica()
    }

    /**
     * Detiene la reproducción de la música cuando la actividad pasa a segundo plano.
     */
    override fun onPause() {
        super.onPause()
        pararMusica()
    }
}
