package com.example.memocrazy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.adapter.BestScoresAdapter
import com.example.memocrazy.entities.BestScore

/**
 * Fragmento que muestra los mejores puntajes (TOP 10) de los jugadores.
 *
 * @property bestScoresList Lista mutable de los mejores puntajes estáticos.
 */
class BestScoresFragment : Fragment(R.layout.fragment_best_scores) {

    // Lista de los mejores puntajes, inicializados estáticamente
    val bestScoresList = mutableListOf(
        BestScore("ShadowWarrior", 1500),
        BestScore("CyberPilot", 1400),
        BestScore("DragonSlayer", 1300),
        BestScore("GhostRecon", 1200),
        BestScore("SkyrimMaster", 1100),
        BestScore("SonicSpeedster", 1000),
        BestScore("PixelPirate", 900),
        BestScore("SpaceInvader", 800),
        BestScore("FortniteChampion", 700),
        BestScore("ApexLegend", 600)
    )

    /**
     * Se llama cuando la vista del fragmento ha sido creada. Configura el RecyclerView.
     *
     * @param view Vista raíz del fragmento.
     * @param savedInstanceState Estado anterior del fragmento, si existe.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
    }

    /**
     * Inicializa y configura el RecyclerView para mostrar la lista de mejores puntajes.
     *
     * @param view Vista raíz del fragmento.
     */
    private fun initRecyclerView(view: View) {
        // Configura el LinearLayoutManager para el RecyclerView
        val layoutManager = LinearLayoutManager(context)

        // Crea una instancia del adaptador BestScoresAdapter con la lista de puntajes
        val adapter = BestScoresAdapter(bestScoresList)

        // Crea un DividerItemDecoration para añadir líneas divisorias entre elementos
        val decoration = DividerItemDecoration(context, layoutManager.orientation)

        // Obtiene una referencia al RecyclerView desde la vista
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_best_score)

        // Asigna el LinearLayoutManager, el adaptador y el decorador al RecyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(decoration)
    }

}
