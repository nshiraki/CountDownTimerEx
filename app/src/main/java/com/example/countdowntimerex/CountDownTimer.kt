package com.example.countdowntimerex

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CountDownTimer : CoroutineScope {
    private var job: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job!!

    interface CountDownTimerListener {
        fun onStart()

        fun onTick(currentCount: Int)

        fun onTimeLimit()

        fun onStop() {}
    }

    private var listener: CountDownTimerListener? = null

    fun start(limitCount: Int, countDownTimerListener: CountDownTimerListener?) {
        job = Job()
        listener = countDownTimerListener
        launch {
            withContext(Dispatchers.Main) { listener?.onStart() }
            for (tickCount in limitCount downTo 0) {
                delay(1000)
                withContext(Dispatchers.Main) { listener?.onTick(tickCount) }
            }
            withContext(Dispatchers.Main) {
                listener?.onTimeLimit()
                stop()
            }
        }
    }

    fun stop() {
        job?.cancel()
        listener?.onStop()
        listener = null
    }
}