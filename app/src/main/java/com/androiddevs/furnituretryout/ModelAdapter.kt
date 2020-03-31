package com.androiddevs.furnituretryout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_model.view.*

class ModelAdapter(
    val models: List<Model>
) : RecyclerView.Adapter<ModelAdapter.ModelViewHolder>() {
    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.itemView.apply {
            ivThumbnail.setImageResource(models[position].imageResourceId)
            tvTitle.text = models[position].title
        }
    }
}