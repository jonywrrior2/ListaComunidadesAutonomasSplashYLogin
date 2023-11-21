package com.example.listacomunidadesautonomas

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listacomunidadesautonomas.ListaComunidades.Companion.listaComunidades
import com.example.listacomunidadesautonomas.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(){
    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private var nombreComunidad ="Sin nombre"
    private lateinit var binding: ActivityMainBinding
    private var id: Int = 0
    private lateinit var adapter:Adapter
    private lateinit var miDAO: ComunidadDAO
    var ultimoId=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(this , object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        miDAO= ComunidadDAO()
        listaComunidades=miDAO.cargarLista(this)
        binding.rvComunidad.layoutManager=LinearLayoutManager(this)
        binding.rvComunidad.setHasFixedSize(true)
        binding.rvComunidad.itemAnimator=DefaultItemAnimator()
        binding.rvComunidad.adapter = Adapter(ListaComunidades.listaComunidades){
            comunidad ->  onItemSelected(comunidad)
        }
        val txtCambioNombre = findViewById<TextView>(R.id.txtCambioNombre)
        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                nombreComunidad = result.data?.extras?.getString("nombre").toString()
                id = result.data?.extras?.getInt("id") as Int
                ListaComunidades.listaComunidades[id].nombre = nombreComunidad
                adapter = Adapter(ListaComunidades.listaComunidades){
                        comunidad ->  onItemSelected(comunidad)
                }
                adapter.notifyItemChanged(id)
                miDAO.actualizarBBDD(this, listaComunidades[id])
                binding.rvComunidad.adapter=adapter

            }
        }

    }

    private fun onItemSelected(comunidad: Comunidad) {
        Toast.makeText(this, "Soy de ${comunidad.nombre}", Toast.LENGTH_SHORT).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.opcionAñadir ->{
                addComunidad()
                true
            }
            R.id.opcionRecargar->{
               //listaComunidades
                crearListaNueva()
                binding.rvComunidad.adapter?.notifyDataSetChanged()
                true
            }
            R.id.opcionBorrar->{
                ListaComunidades.listaComunidades.clear()
                binding.rvComunidad.adapter?.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun ultimoID(){
        ultimoId=0
        for(comunidad in listaComunidades){
            if (ultimoId<comunidad.id)
                ultimoId=comunidad.id
        }
    }

    private fun addComunidad(){
        listaComunidades.add(listaComunidades.size, Comunidad(
            listaComunidades.size+1,"Nueva Comunidad ${listaComunidades.size}",
            R.drawable.andalucia))
            binding.rvComunidad.adapter?.notifyItemInserted (listaComunidades.size)
            binding.rvComunidad.layoutManager?.scrollToPosition(listaComunidades.size)
    }



    override fun onContextItemSelected(item: MenuItem): Boolean {
        lateinit var comunidadAfectada: Comunidad
        lateinit var miIntent: Intent
        comunidadAfectada = listaComunidades[item.groupId]
        when(item.itemId){
            0 ->{
                val alert =
                    AlertDialog.Builder(this).setTitle("Eliminar ${comunidadAfectada.nombre}")
                        .setMessage("Estas seguro de que quieres eliminar ${comunidadAfectada.nombre}?")
                        .setNeutralButton("Cerrar", null).setPositiveButton(
                            "Aceptar"
                        ){_,_ ->
                            display("Se ha eliminado ${comunidadAfectada.nombre}")
                            listaComunidades.removeAt(item.groupId)
                            binding.rvComunidad.adapter?.notifyItemRemoved(item.groupId)
                            binding.rvComunidad.adapter?.notifyItemRangeChanged(item.groupId, listaComunidades.size)
                            binding.rvComunidad.adapter = Adapter(listaComunidades){comunidad ->

                            }
                        }.create()
                alert.show()
            }
            1 ->{
                miIntent = Intent (this, MainActivity2::class.java)
                miIntent.putExtra("nombreComunidad", ListaComunidades.listaComunidades[item.groupId].nombre)
                miIntent.putExtra("id", item.groupId)
                miIntent.putExtra("imagen", ListaComunidades.listaComunidades[item.groupId].imagen)
                intentLaunch.launch(miIntent)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun display (message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun crearListaNueva(){
       // ListaComunidades.listaComunidades.clear()
        //ListaComunidades.listaComunidades.addAll(ListaComunidades.nuevaListaComunidad)
        listaComunidades.clear()
        listaComunidades.addAll(ListaComunidades.nuevaListaComunidad)
    }


}
