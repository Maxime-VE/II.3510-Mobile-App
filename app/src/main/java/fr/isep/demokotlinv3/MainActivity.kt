package fr.isep.demokotlinv3

import android.content.pm.LauncherActivityInfo
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import io.realm.RealmResults
import fr.isep.demokotlinv3.models.UserModel
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    // Load the Permmissions
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadMediaAudioGranted = false
    private var isRecordAudioPermissionGranted = false
    private var isReadContactPermissionGranted = false
    private var isAccessCoarsePermissionGranted = false
    private var isAccessFinePermissionGranted = false

    // Load the Realm components
    private lateinit var realm: Realm
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            isReadMediaAudioGranted = permissions[Manifest.permission.READ_MEDIA_AUDIO] ?: isReadMediaAudioGranted
            isRecordAudioPermissionGranted = permissions[Manifest.permission.RECORD_AUDIO] ?: isRecordAudioPermissionGranted
            isReadContactPermissionGranted = permissions[Manifest.permission.READ_CONTACTS] ?: isReadContactPermissionGranted
            isAccessCoarsePermissionGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: isAccessCoarsePermissionGranted
            isAccessFinePermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: isAccessFinePermissionGranted

            updatePermissionTexts()
        }
        requestPermission()

        // The following code is commented since an error
        // (io.realm.exceptions.RealmException: UserModel is not part of the schema for this Realm)
        // make the application crash

        // Start Realm transaction
//        Realm.init(this)
//        realm = Realm.getDefaultInstance()
//
//        userRecyclerView = findViewById(R.id.user_recycler_view)
//        userRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Get all the user element in Realm to display in the RecyclerView
//        val users: RealmResults<UserModel> = realm.where(UserModel::class.java).findAll()
//        userAdapter = UserAdapter(users)
//        userRecyclerView.adapter = userAdapter

        //Button to redirect to the form page
        val addButton: Button = findViewById(R.id.add_user_button)
        addButton.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    @SuppressLint("WrongViewCast", "SetTextI18n")
    private fun requestPermission(){
        isReadMediaAudioGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_MEDIA_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        isRecordAudioPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        isReadContactPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        isAccessCoarsePermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isAccessFinePermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED



        val permissionRequest : MutableList<String> = ArrayList()

        if(!isReadMediaAudioGranted){
            permissionRequest.add(Manifest.permission.READ_MEDIA_AUDIO)
        }
        if(!isRecordAudioPermissionGranted){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO)
        }
        if(!isReadContactPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_CONTACTS)
        }
        if(!isAccessCoarsePermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!isAccessFinePermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if(permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

        updatePermissionTexts()
    }

    // Update UI Permission
    private fun updatePermissionTexts() {
        val permissionMedia: TextView = findViewById(R.id.permission_media)
        val permissionRecordAudio: TextView = findViewById(R.id.permission_record_audio)
        val permissionContact: TextView = findViewById(R.id.permission_contact)
        val permissionLocation: TextView = findViewById(R.id.permission_location)

        permissionMedia.text = if (isReadMediaAudioGranted) "Permission Media Granted" else "Permission Media Denied"
        permissionRecordAudio.text = if (isRecordAudioPermissionGranted) "Permission Record Audio Granted" else "Permission Record Audio Denied"
        permissionContact.text = if (isReadContactPermissionGranted) "Permission Contact Granted" else "Permission Contact Denied"
        permissionLocation.text = if (isAccessCoarsePermissionGranted && isAccessFinePermissionGranted) {
            "Permission Location Granted"
        } else {
            "Permission Location Denied"
        }
    }


}