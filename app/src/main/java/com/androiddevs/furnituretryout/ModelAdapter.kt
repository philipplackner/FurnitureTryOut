package com.androiddevs.furnituretryout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_model.view.*

const val SELECTED_MODEL_COLOR = Color.YELLOW
const val UNSELECTED_MODEL_COLOR = Color.LTGRAY

class ModelAdapter(
    val models: List<Model>
) : RecyclerView.Adapter<ModelAdapter.ModelViewHolder>() {

    var selectedModel = MutableLiveData<Model>()
    private var selectedModelIndex = 0
    private var modelViewHolders = mutableListOf<ModelViewHolder>()

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if(!modelViewHolders.contains(holder)) {
            modelViewHolders.add(holder)
        }
        holder.itemView.apply {
            ivThumbnail.setImageResource(models[position].imageResourceId)
            tvTitle.text = models[position].title
            if(selectedModelIndex == position) {
                setBackgroundColor(SELECTED_MODEL_COLOR)
                selectedModel.value = models[position]
            }
            setOnClickListener {
                selectModel(position)
            }
        }
    }

    private fun selectModel(position: Int) {
        modelViewHolders[selectedModelIndex].itemView.setBackgroundColor(UNSELECTED_MODEL_COLOR)
        selectedModelIndex = position
        modelViewHolders[selectedModelIndex].itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
        selectedModel.value = models[position]
    }
}