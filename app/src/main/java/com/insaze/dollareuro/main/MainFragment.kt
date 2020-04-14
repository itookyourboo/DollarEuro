package com.insaze.dollareuro.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.insaze.dollareuro.InfoFragment
import com.insaze.dollareuro.R
import com.insaze.dollareuro.adapter.DateAdapter
import com.insaze.dollareuro.model.Valute

class MainFragment: Fragment(), MainContract.View{
    private var presenter = MainPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val adapter = DateAdapter(presenter.list) { date -> presenter.onItemWasClicked(date)}

        val layoutManager = LinearLayoutManager(context)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == presenter.list.size - 1) {
                    presenter.onScrolledToBottom()
                    recyclerView.post { adapter.notifyDataSetChanged() }
                }
            }
        })

        return root
    }

    override fun showInfo(date: String, dollar: Valute?, euro: Valute?) {
        val infoFragment = InfoFragment(date, dollar!!, euro!!)
        fragmentManager!!.beginTransaction().replace(R.id.container, infoFragment, "info")
            .addToBackStack("info").commit()
    }

    override fun showError() {
        Toast.makeText(context, R.string.internet_error, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }
}