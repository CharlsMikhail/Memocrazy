package com.example.memocrazy.utils

import com.example.memocrazy.entities.Theme

class ThemesProvider {
    companion object {
        val listaTemas = arrayListOf(
            Theme("Dragon Ball", android.R.drawable.ic_dialog_info, 0),
            Theme("Futbol", android.R.drawable.ic_menu_gallery, 1),
            Theme("Música", android.R.drawable.ic_media_play, 2),
            Theme("Cine", android.R.drawable.ic_menu_camera, 3),
            Theme("Naturaleza", android.R.drawable.ic_menu_compass, 4),
            Theme("Viajes", android.R.drawable.ic_menu_myplaces, 5),
            Theme("Comida", android.R.drawable.ic_menu_manage, 6),
            Theme("Libros", android.R.drawable.ic_menu_view, 7)
        )
    }
}
