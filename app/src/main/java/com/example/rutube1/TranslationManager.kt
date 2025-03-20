package com.example.rutube1

import android.content.Context
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.content.Intent
import android.os.Bundle
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslationManager(private val context: Context) {
    private var speechRecognizer: SpeechRecognizer? = null
    private var translator: com.google.mlkit.nl.translate.Translator? = null

    init {
        setupTranslator()
    }

    private fun setupTranslator() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage("en")
            .setTargetLanguage("ru")
            .build()
        translator = Translation.getClient(options)
    }

    fun startTranslation(
        onTranslated: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        setupSpeechRecognition(onTranslated, onError)
    }

    private fun setupSpeechRecognition(
        onTranslated: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.get(0)?.let { text ->
                    translateText(text, onTranslated, onError)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.get(0)?.let { text ->
                    translateText(text, onTranslated, onError)
                }
            }

            override fun onError(error: Int) {
                onError(Exception("Speech recognition error: $error"))
            }

            // Required overrides
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer?.startListening(recognizerIntent)
    }

    private fun translateText(
        text: String,
        onTranslated: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        translator?.translate(text)
            ?.addOnSuccessListener { translatedText ->
                onTranslated(translatedText)
            }
            ?.addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun cleanup() {
        speechRecognizer?.destroy()
        translator?.close()
    }
}
