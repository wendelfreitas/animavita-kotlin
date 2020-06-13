package br.com.ite.animavita

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pet_item.view.*

class AnimalAdapter(val context: Context, private val animalList: ArrayList<Animal>) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.animal_image
        val name: TextView = itemView.name
        val type: TextView = itemView.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return AnimalViewHolder(item)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val currentItem = animalList[position]
        holder.photo.setImageResource(currentItem.photo)
        holder.name.text = currentItem.name
        holder.type.text = currentItem.type

        holder.itemView.setOnClickListener {
            val createAnimalIntent = Intent(context, EditAnimal::class.java)
            createAnimalIntent.putExtra("index", position)
            createAnimalIntent.putExtra("photo", currentItem.photo)
            createAnimalIntent.putExtra("name", currentItem.name)
            createAnimalIntent.putExtra("type", currentItem.type)
            context.startActivity(createAnimalIntent)
        }

    }

    fun addAnimal(animal: Animal) {
        animalList.add(animal)
        notifyItemInserted(0);
    }
}