package com.diegocunha.baseapplication.view.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

@JvmOverloads
fun AppCompatActivity.setToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = true) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
}