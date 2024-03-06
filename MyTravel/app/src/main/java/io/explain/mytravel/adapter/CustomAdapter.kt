package io.explain.mytravel.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.explain.mytravel.R
import io.explain.mytravel.models.LocationData

class CustomAdapter(
    private val dataList: List<LocationData>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteIcon)

        init {
            // Configurar um ouvinte de clique para o botão de exclusão
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val locationData = dataList[position]
                    onItemClickListener.onDeleteClick(locationData.description)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationData = dataList[position]
        holder.descriptionTextView.text = locationData.description

        // Adicione um clique no item
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(locationData)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
