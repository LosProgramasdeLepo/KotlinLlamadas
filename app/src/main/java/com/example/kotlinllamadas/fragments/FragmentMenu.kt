package com.example.kotlinllamadas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinllamadas.R
import com.example.kotlinllamadas.adapter.AdapterContactos
import com.example.kotlinllamadas.objetos.Contacto
import com.google.android.material.snackbar.Snackbar

class FragmentMenu : Fragment() {

    lateinit var v: View
    lateinit var recyclerContacto: RecyclerView
    var contactos: MutableList<Contacto> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contactosListAdapter: AdapterContactos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerContacto = v.findViewById(R.id.recycler)

        //Items de la lista por defecto
        contactos.add(Contacto("EMERGENCIAS", 107, R.drawable.ic_baseline_local_taxi_24))
        contactos.add(Contacto("POLICÍA", 911, R.drawable.ic_baseline_local_pizza_24))
        contactos.add(Contacto("BOMBEROS", 100, R.drawable.ic_baseline_fireplace_24))
        contactos.add(Contacto("AGREGAR", null, R.drawable.ic_baseline_add_24))

        recyclerContacto.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)
        recyclerContacto.layoutManager = linearLayoutManager

        contactosListAdapter = AdapterContactos(contactos!!){onItemClick()}

        recyclerContacto.adapter = contactosListAdapter

        return v
    }

    public fun onItemClick (){
        Snackbar.make(v,"click", Snackbar.LENGTH_SHORT).show()
    }

}

