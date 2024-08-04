package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
    }

    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Here","Here123")

        // Request audio recording permission if not granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
         Log.d("Here","Here123")
        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(MyRecognitionListener())

        // Start listening on app launch
        startListening()

        Log.d("Listener","startListening()")
    }

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)


        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en") // Use a more generic English setting
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        Log.d("Listener","startListening()")

        speechRecognizer.startListening(intent)
    }

    private inner class MyRecognitionListener : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
            // Speech recognition is ready
            Log.d("SpeechRecognition", "onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            // User started speaking
            Log.d("SpeechRecognition", "onBeginningOfSpeech")
        }

        override fun onRmsChanged(p0: Float) {
            TODO("Not yet implemented")
        }

        override fun onBufferReceived(p0: ByteArray?) {
            TODO("Not yet implemented")
        }

        override fun onEndOfSpeech() {
            // User stopped speaking
            // You can choose to restart listening here for continuous listening
            startListening()
        }

        override fun onError(error: Int) {
            // Handle recognition errors
            Log.e("SpeechRecognition", "onError: $error")
            Toast.makeText(this@MainActivity, "Error in recognition: $error", Toast.LENGTH_SHORT).show()
            Toast.makeText(this@MainActivity, "Error in recognition", Toast.LENGTH_SHORT).show()
        }

        override fun onResults(results: Bundle) {
            // Speech recognition results
            Log.d("SpeechRecognition", "onResults")
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d("SpeechRecognition", "Matches: $matches")
            if (!matches.isNullOrEmpty()) {
                for (result in matches) {
                    Toast.makeText(this@MainActivity, "Activated", Toast.LENGTH_SHORT).show()
                    if (result.equals("Hi", ignoreCase = true)) {
                        // Keyword detected, perform your desired action (e.g., beep or activation)
                        // You can add more conditions or actions here
                        Toast.makeText(this@MainActivity, "Activated", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        override fun onPartialResults(p0: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            TODO("Not yet implemented")
        }

        // Implement other RecognitionListener methods as needed
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize SpeechRecognizer
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
                speechRecognizer.setRecognitionListener(MyRecognitionListener())
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Permission denied. App may not function properly.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
