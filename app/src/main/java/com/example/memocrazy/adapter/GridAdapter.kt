package com.example.memocrazy.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView

/**
 * @problemDescription Es el encargado de mostrar una cuadrícula de imágenes en un GridView.
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class GridAdapter(private val mContext: Context) : BaseAdapter() {

    // Lista de recursos de imagen proporcionados por Android
    private val mThumbIds = arrayOf(
        android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_gallery,
        android.R.drawable.ic_menu_manage,
        android.R.drawable.ic_menu_help,
        android.R.drawable.ic_menu_search,
        android.R.drawable.ic_menu_send,
        android.R.drawable.ic_menu_view,
        android.R.drawable.ic_menu_zoom,
        android.R.drawable.ic_media_ff,
        android.R.drawable.ic_media_next,
        android.R.drawable.ic_media_pause,
        android.R.drawable.ic_media_play,
        android.R.drawable.ic_media_previous,
        android.R.drawable.ic_media_rew,
        android.R.drawable.ic_media_rew,
        android.R.drawable.ic_menu_add,
        android.R.drawable.ic_menu_call,
        android.R.drawable.ic_menu_crop,
        android.R.drawable.ic_menu_directions,
        android.R.drawable.ic_menu_send,
        android.R.drawable.ic_menu_crop,
        android.R.drawable.ic_menu_directions,
        android.R.drawable.ic_menu_send,
        android.R.drawable.ic_menu_send,
    )

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
        return imageView
    }
}
