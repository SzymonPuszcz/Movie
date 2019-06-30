package com.movies.repository

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


class AppExecutors {
    private val mDiskIO: Executor
    private val mNetworkIO: Executor
    private val mMainThread: Executor


    @Inject
    constructor() {
        mDiskIO = Executors.newSingleThreadExecutor()
        mNetworkIO = Executors.newFixedThreadPool(3)
        mMainThread = MainThreadExecutor()

    }
    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
