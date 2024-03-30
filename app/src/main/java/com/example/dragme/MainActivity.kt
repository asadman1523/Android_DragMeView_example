package com.example.dragme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dragMeView = findViewById<DragMeView>(R.id.dragMeView)
        findViewById<Button>(R.id.btnDrag).setOnClickListener {
            dragMeView.MODE = DragMeView.Mode.MOVE
        }
        findViewById<Button>(R.id.btnScale).setOnClickListener {
            dragMeView.MODE = DragMeView.Mode.SCALE
        }
    }
}