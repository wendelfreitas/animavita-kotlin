package br.com.ite.animavita

import android.R.attr.path
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_animal.*


class EditAnimal : AppCompatActivity() {
    private var uriAnimal = ""

    companion object {
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_animal)
        supportActionBar?.title = "Editar Animal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val index = intent.getIntExtra("index", 0)
        val name = intent.getStringExtra("name")
        val photo = intent.getStringExtra("photo")!!
        uriAnimal = photo
        val type = intent.getStringExtra("type")

        Picasso.with(this).load(Uri.parse(photo)).into(this.animalImage)
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

        animalImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_SELECT_IMAGE_IN_ALBUM)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM){
            uriAnimal = data?.data.toString()
            Picasso.with(this).load(data?.data).into(this.animalImage)
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
                val intent = Intent()

                val body = "Nome do Animal: ${animal_name}, Tipo: ${animal_type_edit}"
                val sub = "Quer ser zika na sua vida real? Salve um animal!"
                val imgSend = Uri.parse(uriAnimal)
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_STREAM, imgSend)
                intent.putExtra(Intent.EXTRA_SUBJECT, body)
                intent.putExtra(Intent.EXTRA_TEXT, sub)
                intent.type = "*/*"
                startActivity(Intent.createChooser(intent, "Compartilhe o Animal!"))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun editAnimal() {
        println("editAnimal")
        val animalImage = uriAnimal
        val index = intent.getIntExtra("index", 0)
        val animal_name = animal_name.text.toString()
        val animal_type_edit = animal_type_edit.selectedItem.toString();
        
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra("index", index)
                putExtra("animal_name", animal_name)
                putExtra("animal_type_edit", animal_type_edit)
                putExtra("animalImage",animalImage)
            }
        )

        finish()
    }
}
