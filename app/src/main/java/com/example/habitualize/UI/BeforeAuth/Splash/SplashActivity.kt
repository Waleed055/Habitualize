package com.example.habitualize.UI.BeforeAuth.Splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val background = object : Thread(){
            override fun run() {
                try {

                    sleep(5000)
//                    val intent = Intent(this@SplashScreen, SelectServiceAct::class.java)
//                    startActivity(intent)
                    finish()


                }catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }

        }
        background.start()


    }
}