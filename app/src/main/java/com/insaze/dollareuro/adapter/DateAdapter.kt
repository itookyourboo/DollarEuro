package com.insaze.dollareuro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.insaze.dollareuro.R
import com.insaze.dollareuro.utils.DateUtils

class DateAdapter(var items: ArrayList<Long>, var onItemWasClicked: (date: String) -> Unit):
    RecyclerView.Adapter<DateAdapter.DateHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = DateHolder(LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateText = itemView.findViewById<TextView>(R.id.date_text)

        fun bind(item: Long) {
            dateText.text = DateUtils.longToDate(item)
            itemView.setOnClickListener { onItemWasClicked(dateText.text.toString()) }
        }
    }
}