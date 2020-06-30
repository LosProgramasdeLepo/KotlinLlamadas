package com.example.kotlinllamadas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinllamadas.R
import com.example.kotlinllamadas.objetos.Contacto
import com.google.android.material.snackbar.Snackbar

class AdapterContactos (private var listaContactos: MutableList<Contacto>,val adapterOnClick : () -> Unit) : RecyclerView.Adapter<AdapterContactos.ContactosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcontacto, parent, false)
        return ContactosHolder(view)
    }

    //Cuenta los items de la lista
    override fun getItemCount(): Int {
        return listaContactos.size
    }

    override fun onBindViewHolder(holder: AdapterContactos.ContactosHolder, position: Int) {
        holder.setNombre(listaContactos[position].nombre)
        holder.setIcono(listaContactos[position].icono)

        val item = listaContactos?.get(position)

        //OnClick
        holder.getCardLayout().setOnClickListener {

            adapterOnClick()
            object: View.OnClickListener {
                override fun onClick(view: View) {
                    Snackbar.make(holder.view,"click${position}", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun setData(newData: ArrayList<Contacto>) {
        this.listaContactos = newData
        this.notifyDataSetChanged()
    }

    //Muestra el nombre y el ícono apropiadamente
    class ContactosHolder(v: View) : RecyclerView.ViewHolder(v) {

        public var view: View = v

        fun setNombre(name: String) {
            val txt: TextView = view.findViewById(R.id.txtNombre)
            txt.text = name
        }

        fun setIcono(icon: Int) {
            val ico: ImageView = view.findViewById(R.id.imgIcono);
            ico.setImageResource(icon)
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.cardView)
        }

    }



}
