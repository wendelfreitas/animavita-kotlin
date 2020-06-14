package br.com.ite.animavita

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pet_item.view.*

class AnimalAdapter(val context: Context, private val animalList: ArrayList<Animal>) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {
    var exclude = false;
    var itemsToExclude: ArrayList<Animal> = ArrayList();

    class AnimalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val container: RelativeLayout = itemView.animal_container
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
        holder.container.setBackgroundColor(Color.parseColor("#ffffff"))

        holder.itemView.setOnClickListener {
            if(exclude) {
                var colorVal = "#ffffff"
                if(!currentItem.selected) {
                    colorVal = "#e2e2e2"
                    currentItem.selected = true;
                    itemsToExclude.add(currentItem)
                }else {
                    currentItem.selected = false;
                    itemsToExclude = itemsToExclude.filter { animal -> animal.id != currentItem.id } as ArrayList<Animal>
                }
                holder.container.setBackgroundColor(Color.parseColor(colorVal))

            } else {

                val createAnimalIntent = Intent(context, EditAnimal::class.java)
                createAnimalIntent.putExtra("index", position)
                createAnimalIntent.putExtra("photo", currentItem.photo)
                createAnimalIntent.putExtra("name", currentItem.name)
                createAnimalIntent.putExtra("type", currentItem.type)
                (context as Activity).startActivityForResult(createAnimalIntent, 2)
            }

        }
    }

    fun setExcludeMode(mode: Boolean) {
        if(!exclude) {
            itemsToExclude = ArrayList()
        }
        exclude = mode;
    }

    fun getExcludeMode(): Boolean {
        return exclude
    }

    fun getAnimalsToExclude(): ArrayList<Animal> {
        return itemsToExclude
    }
}