package com.aman.biometricpromptsample

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.aman.biometricpromptsample.biometrics.BiomatricUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptInfo : BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBiometricPrompt()
        onClick()

    }

    private fun initBiometricPrompt() {
        val executor = Executors.newSingleThreadExecutor()

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    runOnUiThread { showToast("$errString button clicked by user") }
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                runOnUiThread { showToast("Authentication Successfull") }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                runOnUiThread { showToast("Authentication Failed") }
            }
        })

         promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(TITLE)
            .setSubtitle(SUBTITLE)
            .setDescription(DISCRIPTION)
            .setNegativeButtonText(CANCEL)
            .build()
    }

    private fun onClick() {
        bt_authenticate.setOnClickListener {
            if (checkFingerPrintSetting()){
                showBiometricPrompts()
            } else{
                showToast("This feature is not Available for your Device")
            }
        }
    }

    private fun showBiometricPrompts() {
        biometricPrompt.authenticate(promptInfo)
    }

    private fun checkFingerPrintSetting() : Boolean {
        return BiomatricUtils.isHardwareSupported(this)
                && BiomatricUtils.isSdkVersionSupported()
                && BiomatricUtils.isFingerprintAvailable(this)
    }

    private fun Activity.showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object{
        private const val TITLE : String = "Authenticate Finger"
        private const val SUBTITLE : String = ""
        private const val DISCRIPTION : String = ""
        private const val CANCEL : String = "Cancel"
    }
}
