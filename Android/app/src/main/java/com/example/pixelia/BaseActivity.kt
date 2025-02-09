package com.example.pixelia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        setupBottomNavigation()
    }

    abstract fun getLayoutResourceId(): Int

    private fun setupBottomNavigation() {
        findViewById<Button>(R.id.button1)?.setOnClickListener {
            if (this !is MainActivity) {
                startActivity(Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        findViewById<Button>(R.id.button2)?.setOnClickListener {
            if (this !is MyGamesActivity) {
                startActivity(Intent(this, MyGamesActivity::class.java))
                if (this !is MainActivity) finish()
            }
        }

        findViewById<Button>(R.id.addGameButton)?.setOnClickListener {
            if (this !is AddGameActivity) {
                startActivity(Intent(this, AddGameActivity::class.java))
                if (this !is MainActivity) finish()
            }
        }
    }
}