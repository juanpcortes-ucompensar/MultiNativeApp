package com.example.multinativeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.multinativeapp.R
import com.example.multinativeapp.adapters.CategoriaAdapter
import com.example.multinativeapp.models.Categoria
import com.example.multinativeapp.models.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.navigation.fragment.findNavController

class CategoriasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categorias: List<Categoria>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        recyclerView = view.findViewById(R.id.recycler_categorias)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        categorias = obtenerCategoriasConCantidad()
        recyclerView.adapter = CategoriaAdapter(categorias) { categoria ->
            val bundle = Bundle().apply {
                putString("categoriaNombre", categoria.nombre)
            }

            findNavController().navigate(R.id.productosFragment, bundle)
        }

        return view
    }

    private fun obtenerCategoriasConCantidad(): List<Categoria> {
        val prefs = requireContext().getSharedPreferences("productos_pref", Context.MODE_PRIVATE)
        val json = prefs.getString("productos", null) ?: return emptyList()
        val productos = Gson().fromJson<List<Producto>>(json, object : TypeToken<List<Producto>>() {}.type)

        fun contar(nombreCategoria: String) = productos.count {
            it.categoria.equals(nombreCategoria, ignoreCase = true)
        }

        return listOf(
            Categoria("1", "Electrónica", "cat_electronica", contar("Electrónica")),
            Categoria("2", "Ropa", "cat_ropa", contar("Ropa")),
            Categoria("3", "Hogar", "cat_hogar", contar("Hogar")),
            Categoria("4", "Deportes", "cat_deportes", contar("Deportes")),
            Categoria("5", "Accesorios", "cat_accesorios", contar("Accesorios"))
        )
    }
}
