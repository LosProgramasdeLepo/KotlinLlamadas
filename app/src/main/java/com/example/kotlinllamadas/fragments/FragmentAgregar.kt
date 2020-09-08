package com.example.kotlinllamadas.fragments

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
import com.example.kotlinllamadas.objetos.Contacto
import com.google.android.material.snackbar.Snackbar


class FragmentAgregar : Fragment() {

    private lateinit var v: View
    private lateinit var btnAgregarContacto: Button
    private lateinit var nombreContacto: EditText
    private lateinit var numeroContacto: EditText
    private lateinit var nombre: String
    lateinit var numero: String

    //private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        v =  inflater.inflate(R.layout.fragment_agregar, container, false)
        btnAgregarContacto = v.findViewById(R.id.botonAgregaContacto)
        nombreContacto = v.findViewById(R.id.editTextNombre)
        numeroContacto = v.findViewById(R.id.editTextNumero)

        return v
    }

    override fun onStart() {
        super.onStart()

        //Agrega el contacto
        btnAgregarContacto.setOnClickListener{

            //Toma los EditText
            nombre = nombreContacto.text.toString()
            numero = numeroContacto.text.toString()

            if (nombre.isNotEmpty() && numero.isNotEmpty()) {
                //Lo pasa si los espacios no están vacios
                contactos.add(Contacto(nombre, numero, R.drawable.ic_baseline_local_pizza_24, "#FFFFFF"))
                Log.d("lcenagregar", contactos.toString())

                /*
                var tinydb = TinyDB(mContext)
                tinydb.getListaContactos("Contactos")
                */

                val action21 = FragmentAgregarDirections.actionFragmentAgregarToFragmentMenu()
                v.findNavController().navigate(action21)
            }

            else { Snackbar.make(v, "Debe escribir algo...", Snackbar.LENGTH_SHORT).show() }

        }
    }

    /*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }
    */

}