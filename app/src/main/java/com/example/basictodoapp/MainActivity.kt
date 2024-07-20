package com.example.basictodoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn: Button = findViewById(R.id.btnAdd)
        btn.setOnClickListener {
            showAddScreen()
        }
        val recycleView=findViewById<RecyclerView>(R.id.recycleView)
        recycleView.adapter= RvAdapter()
    }

    private fun showAddScreen() {

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)

        // Create the popup window
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        val rootView = findViewById<View>(android.R.id.content)
        popupWindow.showAtLocation(rootView, android.view.Gravity.CENTER, 0, 0)

        val btnDismiss: Button = popupView.findViewById(R.id.save)
        btnDismiss.setOnClickListener {
            popupWindow.dismiss()
        }
    }
}
