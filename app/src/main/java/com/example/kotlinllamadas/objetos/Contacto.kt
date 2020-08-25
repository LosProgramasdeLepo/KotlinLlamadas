package com.example.kotlinllamadas.objetos

import android.os.Parcel
import android.os.Parcelable

class Contacto (var nombre: String, var numero: String, var icono: Int) : Parcelable {

    init {
        this.nombre = nombre!!
        this.numero = numero!!
        this.icono = icono!!
    }

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nombre)
        writeString(numero)
        writeInt(icono)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Contacto> = object : Parcelable.Creator<Contacto> {
            override fun createFromParcel(source: Parcel): Contacto = Contacto(source)
            override fun newArray(size: Int): Array<Contacto?> = arrayOfNulls(size)
        }
    }
}