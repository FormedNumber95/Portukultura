package com.icjardinapps.dm2.portukultura

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Ejer5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejer5)

        val incorrectSound = MediaPlayer.create(this, R.raw.txarto_egin)


        val question1Group = findViewById<RadioGroup>(R.id.question1_group)
        val question2Group = findViewById<RadioGroup>(R.id.question2_group)

        question1Group.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.q1_correct) {
                    Toast.makeText(this, "Pregunta 1: ¡Correcto!", Toast.LENGTH_SHORT).show()
                } else {
                    playIncorrectSound(incorrectSound)
                }
            }
        }

        question2Group.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.q2_correct) {
                    Toast.makeText(this, "Pregunta 2: ¡Correcto!", Toast.LENGTH_SHORT).show()
                } else {
                    playIncorrectSound(incorrectSound)
                }
            }
        }
    }

    private fun playIncorrectSound(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
        Toast.makeText(this, "Txarto egin duzu. Saiatu berriro.", Toast.LENGTH_SHORT).show()
    }
}
