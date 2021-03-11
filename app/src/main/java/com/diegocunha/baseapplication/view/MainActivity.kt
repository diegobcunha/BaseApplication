package com.diegocunha.baseapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diegocunha.baseapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}