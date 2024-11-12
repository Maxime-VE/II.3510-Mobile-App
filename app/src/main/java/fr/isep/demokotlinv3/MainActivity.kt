package fr.isep.demokotlinv3

import android.content.pm.LauncherActivityInfo
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadMediaAudioGranted = false
    private var isRecordAudioPermissionGranted = false
    private var isReadContactPermissionGranted = false
    private var isAccessCoarsePermissionGranted = false
    private var isAccessFinePermissionGranted = false

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

        }

        requestPermission()
    }

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
    }

}