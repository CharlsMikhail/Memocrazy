package com.example.memocrazy.baseDeDatos

import android.app.Application
import com.google.firebase.FirebaseApp

class FireBaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}