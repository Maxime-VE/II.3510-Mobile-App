package fr.isep.demokotlinv3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import fr.isep.demokotlinv3.models.UserModel

class AddUserActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        //Start Realm transaction
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        //Get UI elements (form fields)
        val nameEditText: EditText = findViewById(R.id.edit_text_name)
        val ageEditText: EditText = findViewById(R.id.edit_text_age)
        val addButton: Button = findViewById(R.id.button_add_user)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString().toIntOrNull()

            if (name.isNotBlank() && age != null) {
                saveUser(name, age)
                finish()
            }
        }
    }

    //Save a new user in database with name and age
    private fun saveUser(name: String, age: Int) {
        realm.executeTransaction {
            val newUser = it.createObject(UserModel::class.java, (realm.where(UserModel::class.java).max("id")?.toInt() ?: 0) + 1)
            newUser.name = name
            newUser.age = age
            newUser.isActive = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
