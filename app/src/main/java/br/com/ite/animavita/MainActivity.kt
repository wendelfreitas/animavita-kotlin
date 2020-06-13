package br.com.ite.animavita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val animalList: ArrayList<Animal> = generateDummyList(3);
    private val adapter:AnimalAdapter = AnimalAdapter(this, animalList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Animais Registrados"

        recycler_view.adapter = adapter;
        recycler_view.layoutManager = LinearLayoutManager(this);
        recycler_view.setHasFixedSize(true)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.buttons, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.create -> {
//            // User chose the "Settings" item, show the app settings UI...
//            val const = Animal("Itaai", R.drawable.ic_launcher_background, "Type 2")
//            animalList.add(0,const)
//            adapter.notifyItemInserted(0)

            val createAnimalIntent = Intent(this, CreateAnimal::class.java)
            startActivity(createAnimalIntent)
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun generateDummyList(size: Int): ArrayList<Animal> {
        val list = ArrayList<Animal>()

        for(i in 0 until size) {
            val drawable = when (i%3) {
                0 -> R.drawable.ic_add_black_24dp
                1 -> R.drawable.ic_delete_black_24dp
                else -> R.drawable.ic_launcher_background
            }

            val item = Animal("Item $i", drawable, "Type 2")
            list += item
        }

        return list

    }

    fun createAnimal(animal: Animal) {
        adapter.addAnimal(animal)
        animalList.add(animal)
    }

}
