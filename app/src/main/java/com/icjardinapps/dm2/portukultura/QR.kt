package com.icjardinapps.dm2.portukultura

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class QR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr)
        val img:ImageView=findViewById(R.id.imgQR)
        img.setOnClickListener {
            img.setImageResource(R.drawable.fin)
        }
    }
}