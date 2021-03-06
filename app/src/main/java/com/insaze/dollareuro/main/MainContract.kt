package com.insaze.dollareuro.main

import com.insaze.dollareuro.adapter.DateAdapter
import com.insaze.dollareuro.model.Valute

interface MainContract {
    interface View {
        fun showInfo(date: String, dollar: Valute?, euro: Valute?)
        fun showError()
    }

    interface Presenter {
        fun onItemWasClicked(date: String)
        fun onScrolledToBottom()
    }

    interface Repository {
        fun populateDates(): ArrayList<Long>
        fun loadDates(list: ArrayList<Long>)
    }
}