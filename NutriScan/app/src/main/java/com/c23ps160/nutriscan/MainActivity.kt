package com.c23ps160.nutriscan

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val openScanButton: FloatingActionButton by lazy {
        findViewById(R.id.open_scan)
    }

    private val openCameraButton: FloatingActionButton by lazy {
        findViewById(R.id.open_camera)
    }

    private val openGalleryButton: FloatingActionButton by lazy {
        findViewById(R.id.open_gallery)
    }

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.to_bottom_anim
        )
    }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.status_bar_color)
        }
        val profileIcon: ImageView = findViewById(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        openScanButton.setOnClickListener {
            onOpenScanButtonClicked()
        }
        openCameraButton.setOnClickListener {
            Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show()
        }
        openGalleryButton.setOnClickListener {
            Toast.makeText(this, "Open Gallery", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onOpenScanButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            openCameraButton.visibility = View.VISIBLE
            openGalleryButton.visibility = View.VISIBLE
        } else {
            openCameraButton.visibility = View.INVISIBLE
            openGalleryButton.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            openCameraButton.startAnimation(fromBottom)
            openGalleryButton.startAnimation(fromBottom)
            openScanButton.startAnimation(rotateOpen)
        } else {
            openCameraButton.startAnimation(toBottom)
            openGalleryButton.startAnimation(toBottom)
            openScanButton.startAnimation(rotateClose)
        }
    }
}
