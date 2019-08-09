package com.aman.biometricpromptsample.biometrics

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat


class BiomatricUtils {
    companion object{
        fun isBiometricPromptEnabled(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }

        fun isSdkVersionSupported(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        fun isHardwareSupported(context: Context): Boolean {
            val fingerprintManager = FingerprintManagerCompat.from(context)
            return fingerprintManager.isHardwareDetected
        }

        fun isFingerprintAvailable(context: Context): Boolean {
            val fingerprintManager = FingerprintManagerCompat.from(context)
            return fingerprintManager.hasEnrolledFingerprints()
        }

        @RequiresApi(Build.VERSION_CODES.P)
        fun isPermissionGranted(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(
                context,
                arrayOf(Manifest.permission.USE_BIOMETRIC).toString()
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

}