package com.insaze.dollareuro.main

import com.insaze.dollareuro.network.ValuteService
import kotlinx.coroutines.*

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    private val repository = MainRepository()
    val list = repository.populateDates()

    override fun onItemWasClicked(date: String) {
        val service = ValuteService()
        GlobalScope.launch {
            val data = service.getData(date)
            withContext(Dispatchers.Main) {
                data?.let {
                    view.showInfo(date, service.getDollar(data), service.getEuro(data))
                } ?: view.showError()
            }
        }
    }

    override fun onScrolledToBottom() {
        repository.loadDates(list)
    }
}