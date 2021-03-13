package com.diegocunha.baseapplication.view.templates.acitivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.diegocunha.baseapplication.R
import com.diegocunha.baseapplication.view.extensions.setToolbar

abstract class BaseActivity : AppCompatActivity() {

    val toolbar: Toolbar? by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar?.let {
            setToolbar(it)
        }
    }
}