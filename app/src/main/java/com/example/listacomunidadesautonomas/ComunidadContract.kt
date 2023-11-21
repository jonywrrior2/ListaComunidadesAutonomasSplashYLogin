package com.example.listacomunidadesautonomas

import android.provider.BaseColumns

class ComunidadContract {

    companion object{
        const val NOMBRE_BD = "comunidades"
        const val VERSION = 1
        class Entrada: BaseColumns{
            companion object{
                const val NOMBRE_TABLA = "comunidad"
                const val COLUMNA_ID = "id"
                const val COLUMNA_NOMBRE = "nombre"
                const val COLUMNA_IMAGEN = "imagen"

            }
        }
    }
}