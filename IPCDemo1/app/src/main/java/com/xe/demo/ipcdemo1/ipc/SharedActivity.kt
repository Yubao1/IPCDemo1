package com.xe.demo.ipcdemo1.ipc

import android.os.*
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.xe.demo.ipcdemo1.bean.MyObject
import com.xe.demo.ipcdemo1.ipcdemo1.R
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream

class SharedActivity : AppCompatActivity() {
    var myThread: MyThread? = null
    var myH: MyH? = null
    var mTv: TextView? = null
    var filePath: String = "/filePath"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)
        mTv = findViewById(R.id.tv)
        myThread = MyThread()
        myThread!!.start()
        myH = MyH()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (myH != null) {
            myH!!.removeCallbacksAndMessages(null)
        }
        if (myThread != null) {
            myThread = null
        }
    }

    inner class MyH: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var myObject: MyObject = msg!!.obj as MyObject
            var name: String = myObject.name
            var age: Int = myObject.age
            mTv!!.setText("我的名字叫：" + name + "，年龄是：" + age)
        }
    }

    inner class MyThread: Thread() {
        override fun run() {
            super.run()
            var cachedFile: File = File(Environment.getExternalStorageDirectory().getPath()+filePath + "/my_cache");
            if (cachedFile.exists()) {
                var objectInputStream: ObjectInputStream? = null;
                try {
                    objectInputStream = ObjectInputStream(FileInputStream(cachedFile));
                    var any: Any = objectInputStream!!.readObject();
                    var message: Message = Message.obtain()
                    message.obj = any
                    myH!!.sendMessage(message)
                } catch (e: IOException) {
                    e.printStackTrace();
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace();
                } catch (e: RemoteException) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (objectInputStream != null) {
                            objectInputStream!!.close();
                        }
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
