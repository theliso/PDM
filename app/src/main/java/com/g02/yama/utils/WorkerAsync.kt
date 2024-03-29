package com.g02.yama.utils

import android.annotation.SuppressLint
import android.os.AsyncTask


class WorkerAsync<T>(private val work: () -> T) {

    private var completion: ((T) -> Unit)? = null

    init {
        @SuppressLint("StaticFieldLeak")
        val worker = object : AsyncTask<Unit, Unit, T>() {
            override fun doInBackground(vararg params: Unit?): T = work()
            override fun onPostExecute(result: T) {
                completion?.let { it(result) }
            }
        }
        worker.execute()
    }

    infix fun andThen(completion: (T) -> Unit) {

        this.completion = completion

    }

}

fun <T> runAsync(work: () -> T): WorkerAsync<T> = WorkerAsync(work)