package com.mytrainer.kmp.manager

import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

class iOSTextToSpeechManager : TextToSpeechManager {

    private val synthesizer = AVSpeechSynthesizer()

    override fun say(text: String) {
        val utterance = AVSpeechUtterance(string = text)
        synthesizer.speakUtterance(utterance)
    }
}