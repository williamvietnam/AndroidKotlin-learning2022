package com.android.vncalling.utilities.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProviderImplement: SchedulerProvider {
    override fun ui(): Scheduler? {
        return AndroidSchedulers.mainThread()
    }

    override fun computation(): Scheduler? {
        return Schedulers.computation()
    }

    override fun io(): Scheduler? {
        return Schedulers.io()
    }
}