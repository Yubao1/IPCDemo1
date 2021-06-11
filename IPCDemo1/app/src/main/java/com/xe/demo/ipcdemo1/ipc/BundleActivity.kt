package com.xe.demo.ipcdemo1.ipc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.xe.demo.ipcdemo1.ipcdemo1.R

class BundleActivity : AppCompatActivity() {
    var mTv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bundle)
        mTv = findViewById(R.id.tv)
        var bundle: Bundle = intent.getBundleExtra("bundle")
        var s: CharSequence = bundle.getCharSequence("key")
        mTv!!.setText(s)
    }
}
