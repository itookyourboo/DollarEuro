package com.insaze.dollareuro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.insaze.dollareuro.adapter.DateAdapter
import com.insaze.dollareuro.network.ValuteService
import com.insaze.dollareuro.utils.DateUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    // Количество загружаемых дат
    private val LOAD_COUNT = 15

    private val items: ArrayList<Long> = ArrayList()
    private lateinit var adapter: DateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        populateData()
        initList()
    }

    // Инициализация адаптера и настройка RecyclerView
    fun initList(){
        adapter = DateAdapter(items, object : DateAdapter.OnItemClickListener {
                override fun onItemClicked(date: String) {
                    showInfo(date)
                }
            })

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == items.size - 1)
                    loadMore()
            }
        })
    }

    // Начальные даты
    private fun populateData(){
        val today = System.currentTimeMillis()
        for (offset in 0 until LOAD_COUNT)
            items.add(today - offset * DateUtils.DAY)
    }

    // Загрузка дат
    fun loadMore(){
        val last = items.last()
        for (offset in 1..LOAD_COUNT)
            items.add(last - offset * DateUtils.DAY)
        adapter.notifyDataSetChanged()
    }

    // Получение списка валют и передача инф-ии в InfoActivity
    private fun showInfo(date: String) {
        val service = ValuteService()
        val dispose = service.getData(date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ valutes ->
                Log.e("TAG", "SUCCESS")

                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra("date", date)
                intent.putExtra("dollar", service.getDollar(valutes))
                intent.putExtra("euro", service.getEuro(valutes))
                startActivity(intent)

            }, {
                Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show()
                Log.e("Tag", it.toString())
            })
    }
}
