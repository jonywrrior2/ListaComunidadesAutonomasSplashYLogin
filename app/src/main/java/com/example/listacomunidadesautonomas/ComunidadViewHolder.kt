package com.example.listacomunidadesautonomas

import android.view.ContextMenu
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.listacomunidadesautonomas.databinding.ItemComunidadBinding


class ComunidadViewHolder(view: View):ViewHolder(view), View. OnCreateContextMenuListener {

    private val binding = ItemComunidadBinding.bind(view)
    private lateinit var comunidad:Comunidad

    fun render (item:Comunidad,onClickListener: (Comunidad)->Unit){
        comunidad = item
        binding.tvComunidadNombre.text=item.nombre
        binding.ivComunidad.setImageResource(item.imagen)
        Glide.with(binding.ivComunidad.context).load(item.imagen).into(binding.ivComunidad)
        itemView.setOnClickListener{
            onClickListener(item)

        }
        itemView.setOnCreateContextMenuListener(this)

        }
    override fun onCreateContextMenu(
        menu : ContextMenu?,
        v : View?,
        menuInfo : ContextMenu.ContextMenuInfo?
    ){
        menu!!.setHeaderTitle(comunidad.nombre)
        menu.add(this.adapterPosition, 0, 0, "Eliminar")
        menu.add(this.adapterPosition,1,1,"Editar")
    }
}
