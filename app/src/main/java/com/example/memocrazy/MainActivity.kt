package com.example.memocrazy

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
/**
 * @problemDescription Main activity de la aplicación de Memocrazy
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciarMusica()
    }

    private fun iniciarMusica() {
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_fondo)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    private fun pararMusica() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    override fun onPause() {
        super.onPause()
        pararMusica()
    }

    override fun onDestroy() {
        super.onDestroy()
        pararMusica()
    }
}