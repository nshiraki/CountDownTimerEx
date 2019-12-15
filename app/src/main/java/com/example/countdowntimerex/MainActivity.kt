package com.example.countdowntimerex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countDownTimer = CountDownTimer()

        button.setOnClickListener {
            countDownTimer.stop()
            countDownTimer.start(10, object : CountDownTimer.CountDownTimerListener {
                override fun onStart() {
                    button.isEnabled=false
                    textOut("===============> onStart()")
                }

                override fun onTick(currentCount: Int) {
                    textOut("onTick(): count=$currentCount")
                }

                override fun onTimeLimit() {
                    button.isEnabled=true
                    textOut("===============> onTimeLimit()")
                }
            })
        }
    }

    private fun textOut(text: String?) {
        val newLine = System.getProperty("line.separator")
        text?.let {
            Timber.d(it)
            textView.append("$it$newLine")
            scrollView.post {
                scrollView.fullScroll(View.FOCUS_DOWN)
            }
        }
    }
}
