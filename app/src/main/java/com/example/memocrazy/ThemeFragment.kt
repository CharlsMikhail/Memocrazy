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

/**
 * Fragmento que muestra la lista de temas disponibles para el juego.
 */
class ThemeFragment : Fragment(R.layout.fragment_theme) {

    private lateinit var userAdapter: ThemeAdapter

    /**
     * Método llamado cuando la vista del fragmento se ha creado.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa y configura el RecyclerView
        initRecycleView(view)
    }

    /**
     * Inicializa el RecyclerView con la lista de temas.
     */
    private fun initRecycleView(view: View) {
        // Configura el LinearLayoutManager para el RecyclerView
        val manager = LinearLayoutManager(context)

        // Crea el adaptador con la lista de temas proporcionada por ThemesProvider
        userAdapter = ThemeAdapter(ThemesProvider.listaTemas) { theme -> onItemSelected(theme) }

        // Configura el decorador de divisores para el RecyclerView
        val decoration = DividerItemDecoration(context, manager.orientation)

        // Obtiene una referencia al RecyclerView desde la vista
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_theme)

        // Configura el LinearLayoutManager y el adaptador para el RecyclerView
        recyclerView.layoutManager = manager
        recyclerView.adapter = userAdapter
        recyclerView.addItemDecoration(decoration)
    }

    /**
     * Método llamado cuando se selecciona un tema en el RecyclerView.
     * Navega hacia el fragmento de juego (GameFragment) y pasa el tema seleccionado como argumento.
     */
    private fun onItemSelected(theme: Theme) {
        val bundle = Bundle()
        bundle.putInt(KEY_THEME, theme.images) // Pasa el ID de imágenes del tema seleccionado como argumento
        requireView().findNavController().navigate(R.id.action_themeFragment_to_gameFragment, bundle)
    }

}
