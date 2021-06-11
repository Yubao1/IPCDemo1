package com.xe.demo.ipcdemo1.ipcdemo1

import android.app.VoiceInteractor
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.xe.demo.ipcdemo1.ipc.BundleActivity
import android.os.Environment.getExternalStorageDirectory
import android.os.Handler
import android.os.Message
import com.xe.demo.ipcdemo1.bean.MyObject
import com.xe.demo.ipcdemo1.ipc.SharedActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.*


/**
 * Created by 86188 on 2021/6/7.
 */
class MainActivity: AppCompatActivity() {
    var filePath: String = "/filePath"
    var myHandler: MyHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myHandler = MyHandler()
    }

    fun onClick(v: View) {
        if (v.id == R.id.btn_1) {
            var s: String = "使用Bundle进行IPC跨进程通信"
            var intent: Intent = Intent(this, BundleActivity::class.java)
            var bundle: Bundle = Bundle()
            bundle.putCharSequence("key",s)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        } else if (v.id == R.id.btn_2) {
            saveData()
        }
    }

    fun saveData() {
        Thread(Runnable {
            var myObject: MyObject = MyObject("公众号小二玩编程",21)
            val dir = File(getExternalStorageDirectory().getPath() + filePath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val cachedFile = File(Environment.getExternalStorageDirectory().getPath() + filePath + "/my_cache")
            var objectOutputStream: ObjectOutputStream? = null
            try {
                if (!cachedFile.exists()) {
                    cachedFile.createNewFile()
                }
                objectOutputStream = ObjectOutputStream(FileOutputStream(cachedFile))
                objectOutputStream!!.writeObject(myObject)
                myHandler!!.sendEmptyMessage(0)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    if (objectOutputStream != null) {
                        objectOutputStream!!.close()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }).start()
    }

    inner class MyHandler: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            this@MainActivity.startSharedActivity()
        }
    }

    fun startSharedActivity() {
        var intent: Intent = Intent(this@MainActivity, SharedActivity::class.java)
        startActivity(intent)
    }
}