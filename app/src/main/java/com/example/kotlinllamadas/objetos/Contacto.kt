package com.example.kotlinllamadas.objetos

class Contacto (nombre : String, numero : Int?  , icono : Int) {

    var nombre : String
    var numero : Int?
    var icono  : Int

    init {
        this.nombre = nombre
        this.numero = numero
        this.icono  = icono
    }
}