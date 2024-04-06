package com.example.spotifysdk20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.net.Uri
import com.spotify.android.appremote.*;
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val switchButton: Button = findViewById(R.id.button2)

        switchButton.setOnClickListener {
            // Create an Intent with the custom URI scheme and host
            val intent = Intent(Intent.ACTION_VIEW).apply {
                // Set the custom URI scheme and host
                data = Uri.parse("com.example.spotifysdk20://open")
            }

            // Check if there's an activity that can handle this intent
            if (intent.resolveActivity(packageManager) != null) {
                // Start the activity associated with the intent
                startActivity(intent)
            } else {
                // Handle case where no activity can handle the intent
                // (e.g., show an error message)
            }
        }

    }
}