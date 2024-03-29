package br.com.ite.animavita

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_animal.*
import kotlinx.android.synthetic.main.pet_item.*
import kotlin.random.Random


class CreateAnimal : AppCompatActivity() {
    private var uriAnimal = ""

    companion object {
        val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
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


        animalImageCreate.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.create_button, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("onOptionsItemSelected")
        return when (item.itemId) {
            R.id.finish -> {
                println("onOptionsItemSelected finish")
                createAnimal()
                true
            }
            R.id.home -> {
                println("onOptionsItemSelected home")
                createAnimal()
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                finish()
                true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("onActivityResult 2")
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM){
            uriAnimal = data?.data.toString()
            Picasso.with(this).load(data?.data).into(this.animalImageCreate)
        }
    }

    private fun createAnimal() {
        val animalImage = uriAnimal
        val information_description = information_description.text.toString()
        val animal_types = animal_types.selectedItem.toString();

        if(information_description.isBlank() || animalImage.isBlank()) {
            Toast.makeText(this,"O nome do animal deve ser preenchido e uma imagem deve ser escolhida!",Toast.LENGTH_SHORT).show()
        }else {
            setResult(
                Activity.RESULT_OK,
                Intent().apply {
                    putExtra("animal_image", animalImage)
                    putExtra("information_description", information_description)
                    putExtra("animal_types", animal_types)
                }
            )

            finish()
        }


    }


}
