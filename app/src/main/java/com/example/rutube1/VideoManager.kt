package com.example.rutube1

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import java.util.regex.Pattern

class VideoManager(private val context: Context) {
    
    fun isValidYoutubeUrl(url: String): Boolean {
        val pattern = Pattern.compile(
            "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/.+$"
        )
        return pattern.matcher(url).matches()
    }

    fun loadVideo(
        url: String,
        onSuccess: (MediaItem) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            // For this implementation, we'll use the direct URL
            // In a production environment, you would need to extract the actual video URL
            // using YouTube Data API or a similar service
            val mediaItem = MediaItem.fromUri(url)
            onSuccess(mediaItem)
        } catch (e: Exception) {
            onError(e)
        }
    }
}
