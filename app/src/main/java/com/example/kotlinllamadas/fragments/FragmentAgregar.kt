package com.example.kotlinllamadas.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.kotlinllamadas.ListaContactos.contactos
import com.example.kotlinllamadas.R
import com.example.kotlinllamadas.TinyDB
import com.example.kotlinllamadas.objetos.Contacto
import com.google.android.material.snackbar.Snackbar

class FragmentAgregar : Fragment() {

    private lateinit var v: View
    private lateinit var btnAgregarContacto: Button
    private lateinit var nombreContacto: EditText
    private lateinit var numeroContacto: EditText
    private lateinit var nombre: String
    private lateinit var numero: String
    private lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_agregar, container, false)
        btnAgregarContacto = v.findViewById(R.id.botonAgregaContacto)
        nombreContacto = v.findViewById(R.id.editTextNombre)
        numeroContacto = v.findViewById(R.id.editTextNumero)

        //Obtiene la lista
        mContext = requireActivity()
        val tinydb = TinyDB(mContext)
        contactos = tinydb.getListaContactos("Contactos")

        return v
    }

    //Acá está lo relacionado a agregar el contacto
    override fun onStart() {
        super.onStart()

        //Botón para agregar el contacto
        btnAgregarContacto.setOnClickListener{

            //Toma los EditText
            nombre = nombreContacto.text.toString()
            numero = numeroContacto.text.toString()

            //Lo pasa si los espacios no están vacios
            if (nombre.isNotEmpty() && numero.isNotEmpty()) {

                //Agrega el contacto
                contactos.add(Contacto(nombre, numero, R.drawable.ic_baseline_local_pizza_24, "#FFFFFF"))
                Log.d("lcenagregar", contactos.toString())

                //Guarda en TinyDB
                mContext = requireActivity()
                val tinydb = TinyDB(mContext)
                tinydb.putListaContactos("Contactos", contactos)

                //Vuelve al menú
                val action21 = FragmentAgregarDirections.actionFragmentAgregarToFragmentMenu()
                v.findNavController().navigate(action21)
            }

            //En caso de estar vacío alguno de los espacios
            else { Snackbar.make(v, "Debe escribir algo...", Snackbar.LENGTH_SHORT).show() }

        }
    }

}