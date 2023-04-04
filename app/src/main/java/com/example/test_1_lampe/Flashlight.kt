package com.example.test_1_lampe


import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi

class Flashlight(context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    @RequiresApi(Build.VERSION_CODES.M)
    fun turnOnFlashlight() {
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun turnOffFlashlight() {
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, false)
    }
}
