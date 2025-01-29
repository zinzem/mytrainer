package com.mytrainer.kmp.manager

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class AndroidTextToSpeechManager(context: Context) : TextToSpeechManager {
    private lateinit var textToSpeech: TextToSpeech

    init {
        textToSpeech = TextToSpeech(context) { res ->
            if (res != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.getDefault())
            }
        }
    }

    override fun say(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH,null, null)
    }
}