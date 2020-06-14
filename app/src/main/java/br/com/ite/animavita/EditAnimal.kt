package br.com.ite.animavita

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_animal.*
import kotlinx.android.synthetic.main.activity_edit_animal.*
import kotlinx.android.synthetic.main.pet_item.*


class EditAnimal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_animal)
        supportActionBar?.title = "Editar Animal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val index = intent.getIntExtra("index", 0)
        val name = intent.getStringExtra("name")
        val photo = intent.getIntExtra("photo", 0)
        val type = intent.getStringExtra("type")

        animalImage.setImageResource(photo)
        animal_name.setText(name)

        val spinner: Spinner = findViewById(R.id.animal_type_edit)
        val also = ArrayAdapter.createFromResource(
            this,
            R.array.animal_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            val position = adapter.getPosition(type)
            animal_type_edit.setSelection(position)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.edit_buttons, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.finish -> {
                editAnimal()
                finish()
                true
            }
            R.id.share_data -> {
                val animal_name = animal_name.text.toString()
                val animal_type_edit = animal_type_edit.selectedItem.toString();

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "type/palin"
                val body = "Nome do Animal: ${animal_name}, Tipo: ${animal_type_edit}"
                val sub = "Quer ser zika na sua vida real? Salve um animal!"

                intent.putExtra(Intent.EXTRA_SUBJECT, body)
                intent.putExtra(Intent.EXTRA_TEXT, sub)
                startActivity(Intent.createChooser(intent, "Compartilhe o Animal!"))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun editAnimal() {
        println("editAnimal")
        val animalImage = R.drawable.ic_add_black_24dp
        val index = intent.getIntExtra("index", 0)
        val animal_name = animal_name.text.toString()
        val animal_type_edit = animal_type_edit.selectedItem.toString();
        
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra("index", index)
                putExtra("animal_name", animal_name)
                putExtra("animal_type_edit", animal_type_edit)
            }
        )

        finish()
    }
}
