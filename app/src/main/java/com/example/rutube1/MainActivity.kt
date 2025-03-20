package com.example.rutube1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private lateinit var videoManager: VideoManager
    private lateinit var translationManager: TranslationManager
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var subtitleView: TextView
    private lateinit var errorView: TextView
    private lateinit var youtubeLinkInput: EditText
    private lateinit var translateButton: Button

    private val PERMISSION_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupPlayer()
        checkPermissions()
        
        videoManager = VideoManager(this)
        translationManager = TranslationManager(this)

        translateButton.setOnClickListener {
            handleTranslateButtonClick()
        }
    }

    private fun initializeViews() {
        playerView = findViewById(R.id.videoView)
        subtitleView = findViewById(R.id.subtitleView)
        errorView = findViewById(R.id.errorView)
        youtubeLinkInput = findViewById(R.id.youtubeLinkInput)
        translateButton = findViewById(R.id.translateButton)
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun handleTranslateButtonClick() {
        val youtubeUrl = youtubeLinkInput.text.toString()
        errorView.visibility = View.GONE

        if (!videoManager.isValidYoutubeUrl(youtubeUrl)) {
            showError(getString(R.string.error_invalid_url))
            return
        }

        videoManager.loadVideo(youtubeUrl,
            onSuccess = { mediaItem ->
                player.setMediaItem(mediaItem)
                player.prepare()
                player.play()
                startTranslation()
            },
            onError = { error ->
                showError(getString(R.string.error_video_load))
            }
        )
    }

    private fun startTranslation() {
        translationManager.startTranslation(
            onTranslated = { translatedText ->
                subtitleView.text = translatedText
            },
            onError = { error ->
                showError(getString(R.string.error_translation))
            }
        )
    }

    private fun showError(message: String) {
        errorView.text = message
        errorView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        translationManager.cleanup()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showError(getString(R.string.error_speech_recognition))
            }
        }
    }
}
