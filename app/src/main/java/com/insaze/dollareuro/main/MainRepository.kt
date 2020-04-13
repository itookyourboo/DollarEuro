package com.insaze.dollareuro.main

import com.insaze.dollareuro.utils.DateUtils

class MainRepository: MainContract.Repository {

    override fun populateDates(): ArrayList<Long> {
        val list = ArrayList<Long>()
        val today = System.currentTimeMillis()
        for (offset in 0 until 15)
            list.add(today + offset * DateUtils.DAY)

        return list
    }

    override fun loadDates(list: ArrayList<Long>) {
        val last = list.last()
        for (offset in 1..15)
            list.add(last + offset * DateUtils.DAY)
    }
}