package com.example.listacomunidadesautonomas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: MainActivity2
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val nombre = intent.getStringExtra("nombreComunidad")
        val imagen = intent.getIntExtra("imagen",0)
        val cambioNombre = findViewById<EditText>(R.id.txtCambioNombre)
        val cambioImagen = findViewById<ImageView>(R.id.ivNuevaImg)
        cambioImagen.setImageResource(imagen)
        cambioNombre.hint = nombre
        id = intent.getIntExtra("id",0)

        val btnCambiar = findViewById<Button>(R.id.btnCambiar)
        btnCambiar.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            val name = cambioNombre.text.toString()
            intent.putExtra("nombre", name)
            intent.putExtra("id", id)
            setResult(RESULT_OK,intent)
            finish()
        }
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}