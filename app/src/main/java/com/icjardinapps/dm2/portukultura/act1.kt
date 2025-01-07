package com.icjardinapps.dm2.portukultura

import android.content.ClipData
import android.net.Uri
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity is the main entry point of the application.
 * It handles video playback and a drag-and-drop mini-game.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act1)

        // VideoView setup
        val videoView = findViewById<VideoView>(R.id.introVideoView)
        val gameLayout = findViewById<View>(R.id.gameLayout)

        // Set the video path
        val videoPath = "android.resource://" + packageName + "/" + R.raw.espacioAmbiental
        val uri = Uri.parse(videoPath)

        videoView.setVideoURI(uri)

        // Video completion listener to switch to the game
        videoView.setOnCompletionListener {

            videoView.visibility = View.GONE
            gameLayout.visibility = View.VISIBLE
        }

        videoView.start()

    /**
     * Sets up drag-and-drop listeners for game elements.
     */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act1)

        // References to images and text
        val imagen1 = findViewById<ImageView>(R.id.imagen1)
        val texto1 = findViewById<TextView>(R.id.texto1)

        val imagen2 = findViewById<ImageView>(R.id.imagen2)
        val texto2 = findViewById<TextView>(R.id.texto2)

        val imagen3 = findViewById<ImageView>(R.id.imagen3)
        val texto3 = findViewById<TextView>(R.id.texto3)

        val imagen4 = findViewById<ImageView>(R.id.imagen4)
        val texto4 = findViewById<TextView>(R.id.texto4)

        val imagen5 = findViewById<ImageView>(R.id.imagen5)
        val texto5 = findViewById<TextView>(R.id.texto5)

        // Configure drag-and-drop for each image-text pair
        imagen1.setOnLongClickListener {
            val clipData = ClipData.newPlainText("id", "imagen1")
            val dragShadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(clipData, dragShadow, it, 0)
            true
        }

        texto1.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val itemId = event.clipData.getItemAt(0).text.toString()
                    if (itemId == "imagen1") {
                        playAudio(R.raw.lehenengo_audioa)
                    } else {
                        playAudio(R.raw.bigarrenaudioa)
                    }
                }
            }
            true
        }

        imagen2.setOnLongClickListener {
            val clipData = ClipData.newPlainText("id", "imagen2")
            val dragShadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(clipData, dragShadow, it, 0)
            true
        }

        texto2.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val itemId = event.clipData.getItemAt(0).text.toString()
                    if (itemId == "imagen2") {
                        playAudio(R.raw.lehenengo_audioa)
                    } else {
                        playAudio(R.raw.bigarrenaudioa)
                    }
                }
            }
            true
        }

        imagen3.setOnLongClickListener {
            val clipData = ClipData.newPlainText("id", "imagen3")
            val dragShadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(clipData, dragShadow, it, 0)
            true
        }

        texto3.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val itemId = event.clipData.getItemAt(0).text.toString()
                    if (itemId == "imagen3") {
                        playAudio(R.raw.lehenengo_audioa)
                    } else {
                        playAudio(R.raw.bigarrenaudioa)
                    }
                }
            }
            true
        }
        imagen4.setOnLongClickListener {
            val clipData = ClipData.newPlainText("id", "imagen4")
            val dragShadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(clipData, dragShadow, it, 0)
            true
        }

        texto4.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val itemId = event.clipData.getItemAt(0).text.toString()
                    if (itemId == "imagen4") {
                        playAudio(R.raw.lehenengo_audioa)
                    } else {
                        playAudio(R.raw.bigarrenaudioa)
                    }
                }
            }
            true
        }

        imagen5.setOnLongClickListener {
            val clipData = ClipData.newPlainText("id", "imagen5")
            val dragShadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(clipData, dragShadow, it, 0)
            true
        }

        texto5.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val itemId = event.clipData.getItemAt(0).text.toString()
                    if (itemId == "imagen5") {
                        playAudio(R.raw.lehenengo_audioa)
                    } else {
                        playAudio(R.raw.bigarrenaudioa)
                    }
                }
            }
            true
        }

    }

        /**
         * Plays an audio file.
         *
         * @param audioResId The resource ID of the audio file to be played.
         */
        private fun playAudio(audioResId: Int) {
            // Implementation for playing audio goes here.
        }
    }
}
}