package com.sultonbek1547.yolharakatiqoidalari

import android.R
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.yolharakatiqoidalari.Constraints.TEMP_USER
import com.sultonbek1547.yolharakatiqoidalari.Constraints.USER_EDITED_STATE
import com.sultonbek1547.yolharakatiqoidalari.Constraints.VIEW_PAGER_ITEM_POSITION
import com.sultonbek1547.yolharakatiqoidalari.Constraints.WHICH_TYPE_ARRAY
import com.sultonbek1547.yolharakatiqoidalari.databinding.ActivityAddBinding
import com.sultonbek1547.yolharakatiqoidalari.db.MyDbHelper
import java.io.File
import java.io.FileOutputStream

class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var user: MyModel
    private lateinit var photoUri: Uri
    private lateinit var photoPath: String
    private lateinit var lastUserInDb: MyModel
    private lateinit var name: String
    private lateinit var about: String
    var whichTypePosition = 0
    var whichTypeState = false
    var photoSelectedState = false
    var isEdit = false
    var databaseState = false
    private var imageFileName = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        doDefault()
        myDbHelper = MyDbHelper(this)
        if (myDbHelper.getAllUsers().isEmpty()) {
            databaseState = true
        } else {
            lastUserInDb = myDbHelper.getAllUsers().last()
        }


        checkForEdit()

        /** selecting image*/
        binding.image.setOnClickListener {
            getImageContent.launch("image/*")
        }

        /**saving image*/
        binding.btnSave.setOnClickListener {
            name = binding.edtName.text.toString().trim()
            about = binding.edtAbout.text.toString().trim()

            /** checking for isEDIT */
            if (isEdit) {
                editUser()
            } else {
                addUser()
            }


        }


    }

    private fun editUser() {
        if (name.isNotEmpty()) {
            /** checking for new image selectedState */
            if (photoSelectedState) {
                val inputStream = contentResolver?.openInputStream(photoUri)
                val file = File(filesDir, "${user.id}.jpg")
                photoPath = file.absolutePath
                val fileOutputStream = FileOutputStream(file)
                inputStream?.copyTo(fileOutputStream)
                inputStream?.close()
                fileOutputStream.close()

                /** changing user.photoPath */
                user.photoPath = photoPath
            }

            user.name = name
            user.about = about
            user.whichType = whichTypePosition

            /**editing in database */
            myDbHelper.editUser(user)

            USER_EDITED_STATE = true
            TEMP_USER = user
            Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
            VIEW_PAGER_ITEM_POSITION = user.whichType!!
            finish()
        }
    }

    private fun addUser() {
        if (whichTypeState && name.isNotEmpty() && photoSelectedState) {
            /**saving photo*/
            savePhotoToDir()

            user = MyModel(name, about, whichTypePosition, 0, photoPath)
            myDbHelper.addUser(user)

            Toast.makeText(this@AddActivity, "Saqlandi", Toast.LENGTH_SHORT).show()
            VIEW_PAGER_ITEM_POSITION = user.whichType!!
            finish()
        } else {
            Toast.makeText(this@AddActivity, "To'ldiring", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkForEdit() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            user = intent.getSerializableExtra("user") as MyModel
            binding.apply {
                edtName.setText(user.name)
                edtAbout.setText(user.about)
                spinnerWhichType.setText(WHICH_TYPE_ARRAY[user.whichType!!])
                image.setImageURI(Uri.parse(user.photoPath))
                whichTypePosition = user.whichType!!
                val adapter2 = ArrayAdapter<String>(
                    this@AddActivity, R.layout.simple_spinner_dropdown_item, WHICH_TYPE_ARRAY
                )
                spinnerWhichType.setAdapter(adapter2)

            }
        }
    }

    private fun savePhotoToDir() {
        imageFileName = if (databaseState) {
            1
        } else {
            lastUserInDb.id!! + 1
        }
        val inputStream = contentResolver?.openInputStream(photoUri)
        val file = File(filesDir, "$imageFileName.jpg")
        photoPath = file.absolutePath
        val fileOutputStream = FileOutputStream(file)
        inputStream?.copyTo(fileOutputStream)
        inputStream?.close()
        fileOutputStream.close()
    }

    private fun doDefault() {
        val adapter = ArrayAdapter<String>(
            this, R.layout.simple_spinner_dropdown_item, WHICH_TYPE_ARRAY
        )
        binding.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }
            spinnerWhichType.setAdapter(adapter)

            spinnerWhichType.setOnItemClickListener { _, _, i, _ ->
                whichTypePosition = i
                whichTypeState = true

            }
        }

    }

    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: return@registerForActivityResult
        binding.image.setImageURI(it)
        photoUri = it
        photoSelectedState = true
    }
}