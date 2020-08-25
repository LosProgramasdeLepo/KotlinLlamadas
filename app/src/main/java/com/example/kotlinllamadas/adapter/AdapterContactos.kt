package com.example.kotlinllamadas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinllamadas.R
import com.example.kotlinllamadas.objetos.Contacto

class AdapterContactos (var listaContactos : MutableList<Contacto>, val onItemClick : (Int) -> Unit) : RecyclerView.Adapter<AdapterContactos.ContactosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcontacto, parent, false)
        return ContactosHolder(view)
    }

    //Cuenta los items de la lista
    override fun getItemCount(): Int {
        return listaContactos.size
    }

    override fun onBindViewHolder(holder: AdapterContactos.ContactosHolder, position: Int) {
        //Detecta la imágen y el ícono de cada ítem
        holder.setNombre(listaContactos[position].nombre)
        holder.setNumero(listaContactos[position].numero)
        holder.setIcono(listaContactos[position].icono)

        //OnClick
        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }
    }

    fun setData(newData: ArrayList<Contacto>) {
        this.listaContactos = newData
        this.notifyDataSetChanged()
    }

    //Muestra el nombre y el ícono apropiadamente
    class ContactosHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v

        fun setNombre(name: String) {
            val txt: TextView = view.findViewById(R.id.txtNombre)
            txt.text = name
        }

        fun setIcono(@DrawableRes icon: Int) {
            val ico: ImageView = view.findViewById(R.id.imgIcono);
            ico.setImageResource(icon)
        }

        fun setNumero(numero: String) {
            val num: TextView = view.findViewById(R.id.txtNumero)
            num.text = numero
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.cardView)
        }

    }

    fun addData (data: Contacto) {
        this.listaContactos.add(data)
        this.notifyDataSetChanged()
    }

}
