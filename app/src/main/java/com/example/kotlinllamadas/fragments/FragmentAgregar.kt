package com.example.kotlinllamadas.fragments

import android.content.Context
import android.content.SharedPreferences
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
import java.util.*

class FragmentAgregar : Fragment() {

    private lateinit var v: View
    private lateinit var btnAgregarContacto: Button
    private lateinit var nombreContacto: EditText
    private lateinit var numeroContacto: EditText
    private var elColorEstaBien = false
    private lateinit var nombre: String
    private lateinit var numero: String
    private lateinit var color: String
    private lateinit var mContext: Context
    private var colorAnterior: String = "#F04C29"
    private val posiblesColores = mutableListOf("#F3F3F3", "#45B6FE", "#F04C29", "#78C422", "#F1FC53", "#FFB41F", "#B854FF")

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

        //Inicializo SharedPreferences para el color anterior
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("Color Anterior", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //Setea el color anterior guardado
        colorAnterior = sharedPref.getString("Color Anterior", "default").toString()
        editor.apply()

        //Botón para agregar el contacto
        btnAgregarContacto.setOnClickListener{

            Log.d("mesi", colorAnterior)

            //Toma los EditText
            nombre = nombreContacto.text.toString().toUpperCase(Locale.ROOT)
            numero = numeroContacto.text.toString()

            //Selecciona y chequea el color
            elegirColor()
            chequearColor()
            Log.d("mesi2", colorAnterior)

            //Guarda el nuevo color anterior
            editor.putString("Color Anterior", colorAnterior)
            editor.apply()

            //Lo pasa si los espacios no están vacios
            if (nombre.isNotEmpty() && numero.isNotEmpty() && elColorEstaBien) {

                //Acorta la string de ser muy larga
                if (nombre.length > 13) {
                    nombre = nombre.substring(0, 13) + "..."
                }

                //Agrega el contacto
                contactos.add(Contacto(nombre, numero, R.drawable.vacio, color))

                //Para la próxima
                elColorEstaBien = false

                //Guarda en TinyDB
                mContext = requireActivity()
                val tinydb = TinyDB(mContext)
                tinydb.putListaContactos("Contactos", contactos)

                //Vuelve al menú
                val action21 = FragmentAgregarDirections.actionFragmentAgregarToFragmentMenu()
                v.findNavController().navigate(action21)
            }

            //Esto existe por las dudas
            else if(!elColorEstaBien){ elegirColorDistinto () }

            //En caso de estar vacío alguno de los espacios
            else { Snackbar.make(v, "Debe escribir algo...", Snackbar.LENGTH_SHORT).show() }

        }
    }

    private fun elegirColor () {
        posiblesColores.shuffle()
        color = posiblesColores [1]
    }

    private fun chequearColor () {
        //Si está bien genial
        if (color != colorAnterior) {
            colorAnterior = color
            elColorEstaBien = true
        }

        //Sino elige uno distinto
        else { elegirColorDistinto() }

        return
    }

    private fun elegirColorDistinto () {
        //Vuelve a seleccionar
        elegirColor()
        chequearColor()
        return
    }

}