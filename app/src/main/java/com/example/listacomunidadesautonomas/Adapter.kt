package com.example.listacomunidadesautonomas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class Adapter(private val comunidadLista:List<Comunidad>,
              private val onClickListener:(Comunidad)->Unit): RecyclerView.Adapter<ComunidadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComunidadViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return ComunidadViewHolder(layoutInflater.inflate(R.layout.item_comunidad,parent,false))
    }

    override fun getItemCount(): Int {
        return comunidadLista.size
    }


    override fun onBindViewHolder(holder: ComunidadViewHolder, position: Int) {
        val item=comunidadLista[position]
        holder.render(item,onClickListener)
    }


}