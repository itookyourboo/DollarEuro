package com.insaze.dollareuro.main

import android.util.Log
import com.insaze.dollareuro.adapter.DateAdapter
import com.insaze.dollareuro.network.ValuteService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(val view: MainContract.View): MainContract.Presenter {
    private val repository = MainRepository()
    val list = repository.populateDates()
    var dispose: Disposable? = null

    override fun onItemWasClicked(date: String) {
        val service = ValuteService()
        dispose = service.getData(date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ valutes ->
                view.showInfo(date, service.getDollar(valutes), service.getEuro(valutes))
                Log.e("TAG", "SUCCESS")
            }, {
                view.showError()
                Log.e("Tag", it.toString())
            })
    }

    override fun onScrolledToBottom(adapter: DateAdapter) {
        repository.loadDates(list)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        dispose!!.dispose()
    }
}