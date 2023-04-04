package com.example.test_1_lampe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Build
import android.telephony.SmsMessage
import androidx.annotation.RequiresApi

class SmsReceiver : BroadcastReceiver() {

    private lateinit var cameraManager: CameraManager
    private val TRIGGER_PHRASE = "allumer la lampe"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        if (bundle != null) {
            val pdus = bundle.get("pdus") as Array<Any>
            for (i in pdus.indices) {
                val smsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                val senderNum = smsMessage.displayOriginatingAddress
                val message = smsMessage.messageBody
                if (message.toLowerCase().contains(TRIGGER_PHRASE)) {
                    // Allumer la lampe
                    turnOnFlashLight(context)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun turnOnFlashLight(context: Context) {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, true)
    }
}
