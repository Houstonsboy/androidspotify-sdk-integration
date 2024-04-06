package com.example.spotifysdk20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

import com.spotify.protocol.client.Subscription
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track

class MainActivity : AppCompatActivity() {

    // Custom URI scheme
    private val customUriScheme = "com.example.spotifysdk20"

    // Other properties and methods remain unchanged
    private val clientId = "ad0911afa57949bba362003f601876b2"
    private val redirectUri = "https://com.spotify.android.spotifysdkkotlindemo/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle custom URI intent
        handleCustomUriIntent(intent)
    }

    private fun handleCustomUriIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.scheme == customUriScheme) {
                // Custom URI scheme is detected, perform necessary action
                Log.d("MainActivity2", "Custom URI detected: $uri")
                // You can add your logic here to handle the custom URI
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity2", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity2", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    private fun connected() {
        spotifyAppRemote?.let {
            // Play a playlist
            val playlistURI = "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL"
            it.playerApi.play(playlistURI)
            // Subscribe to PlayerState
            it.playerApi.subscribeToPlayerState().setEventCallback {
                val track: Track = it.track
                Log.d("MainActivity2", track.name + " by " + track.artist.name)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    // Override onNewIntent to handle new intents sent to the activity
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Handle the new intent
        handleCustomUriIntent(intent)
    }
}
