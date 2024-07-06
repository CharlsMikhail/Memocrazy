package com.example.memocrazy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.adapter.ThemeAdapter
import com.example.memocrazy.entities.Theme
import com.example.memocrazy.utils.KEY_THEME
import com.example.memocrazy.utils.ThemesProvider

class ThemeFragment : Fragment(R.layout.fragment_theme) {

    private lateinit var userAdapter: ThemeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView(view)

    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        userAdapter = ThemeAdapter(ThemesProvider.listaTemas) { user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.recycler_theme)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(item:Theme) {
        val delivery = Bundle()
        delivery.putInt(KEY_THEME, item.images)
        requireView().findNavController().navigate(R.id.action_themeFragment_to_gameFragment, delivery)
    }

}