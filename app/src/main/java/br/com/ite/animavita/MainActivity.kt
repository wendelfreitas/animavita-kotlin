package br.com.ite.animavita

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_ADD_ANIMAL = 1
        private const val REQUEST_CODE_EDIT_ANIMAL = 2
        private val uriAnimal = ""
    }

    private val animalList: ArrayList<Animal> = ArrayList();
    private val adapter: AnimalAdapter = AnimalAdapter(this, animalList)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ADD_ANIMAL -> when (resultCode) {
                Activity.RESULT_CANCELED -> {
                }
                Activity.RESULT_OK -> {
                    val animal_image = data?.getStringExtra("animal_image")!!
                    val information_description = data?.getStringExtra("information_description")!!
                    val animal_types = data?.getStringExtra("animal_types")!!

                    val animal = Animal(
                        information_description,
                        animal_image,
                        animal_types
                    )
                    animalList.add(0, animal)
                    adapter.notifyDataSetChanged()
                }
            }
            REQUEST_CODE_EDIT_ANIMAL -> when (resultCode) {
                Activity.RESULT_CANCELED -> {
                    println("RESULT_CANCELED")
                }
                Activity.RESULT_OK -> {
                    val animal_name = data?.getStringExtra("animal_name")!!
                    val animal_type_edit = data?.getStringExtra("animal_type_edit")!!
                    val animalImage = data?.getStringExtra("animalImage")!!
                    val index = data?.getIntExtra("index", 0)

                    val animal =
                        Animal(animal_name, animalImage, animal_type_edit)
                    animalList.removeAt(index);
                    adapter.notifyItemRemoved(0);
                    animalList.add(0, animal)
                    adapter.notifyItemInserted(0);
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("onCreate")
        val actionBar = supportActionBar
        actionBar!!.title = "Animais Registrados"

        recycler_view.adapter = adapter;
        recycler_view.layoutManager = LinearLayoutManager(this);
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.buttons, menu)
        println("onCreateOptionsMenu")
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.create -> {
            // User chose the "Settings" item, show the app settings UI...
            val createAnimalIntent = Intent(this, CreateAnimal::class.java)
            startActivityForResult(createAnimalIntent, 1)
            //startActivity(createAnimalIntent)
            true
        }

        R.id.delete -> {
            if (adapter.getExcludeMode()) {
                println(adapter.getAnimalsToExclude())

                adapter.getAnimalsToExclude().forEach { animal ->
                    animalList.removeIf { anim -> anim.id == animal.id }
                }
                
                adapter.setExcludeMode(false)
                adapter.notifyDataSetChanged()
            } else {
                adapter.setExcludeMode(true)
            }
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


}
