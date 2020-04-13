package com.insaze.dollareuro.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.insaze.dollareuro.InfoActivity
import com.insaze.dollareuro.R
import com.insaze.dollareuro.adapter.DateAdapter
import com.insaze.dollareuro.model.Valute
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var adapter: DateAdapter
    private var presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = DateAdapter(presenter.list, object: DateAdapter.OnItemClickListener{
            override fun onItemClicked(date: String) {
                presenter.onItemWasClicked(date)
            }
        })

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == presenter.list.size - 1)
                    presenter.onScrolledToBottom(adapter)
            }
        })
    }

    override fun showInfo(date: String, dollar: Valute?, euro: Valute?) {
        val intent = Intent(this, InfoActivity::class.java)
        intent.putExtra("date", date)
        intent.putExtra("dollar", dollar)
        intent.putExtra("euro", euro)
        startActivity(intent)
    }

    override fun showError() {
        Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show()
    }
}
