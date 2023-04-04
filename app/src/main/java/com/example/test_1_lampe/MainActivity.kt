package com.example.test_1_lampe

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private lateinit var smsReceiver: SmsReceiver
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //permission camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),1)
        }

        //permission sms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS), 1)
        }

        // Initialize camera manager
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // Initialize SMS receiver
        smsReceiver = SmsReceiver()
    }

    override fun onResume() {
        super.onResume()

        // Register SMS receiver
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, filter)

    }

    override fun onPause() {
        super.onPause()

//         Unregister SMS receiver
//        unregisterReceiver(smsReceiver)

        // Register SMS receiver
//        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
//        registerReceiver(smsReceiver, filter)
    }

    private inner class SmsReceiver : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent) {
            // Allumer la lampe
            turnOnFlashLight()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun turnOnFlashLight() {
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, true)
    }




}
