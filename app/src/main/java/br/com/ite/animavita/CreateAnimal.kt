package br.com.ite.animavita

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_animal.*
import kotlin.random.Random


class CreateAnimal : AppCompatActivity() {


    companion object {
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_animal)
        supportActionBar?.title = "Cadastrar Animal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val spinner: Spinner = findViewById(R.id.animal_types)
        val also = ArrayAdapter.createFromResource(
            this,
            R.array.animal_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        animalImage.setOnClickListener {
            4
//
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_SELECT_IMAGE_IN_ALBUM)
            createAnimal()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.create_button, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                createAnimal()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM){
            animalImage.setImageURI(data?.data) // handle chosen image
        }
    }

    private fun createAnimal() {

        val index: Int = Random.nextInt(8)
        val animal = Animal("Rogerinho", R.drawable.ic_add_black_24dp, "Description")



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
}
