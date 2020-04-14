package com.insaze.dollareuro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.insaze.dollareuro.model.Valute

class InfoFragment(val date: String, val dollar: Valute, val euro: Valute): Fragment() {
    private lateinit var root: View
    private lateinit var title: TextView
    private lateinit var dollarInfo: TextView
    private lateinit var euroInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_info, container, false)
        title = root.findViewById(R.id.titleInfo)
        dollarInfo = root.findViewById(R.id.dollarInfo)
        euroInfo = root.findViewById(R.id.euroInfo)

        showData(date, dollar, euro)
        return root
    }

    private fun showData(date: String, dollar: Valute, euro: Valute){
        title.text = getString(R.string.today_exchange_rate).format(date)
        dollarInfo.text = getString(R.string.valute_info).format(dollar.name, dollar.value)
        euroInfo.text = getString(R.string.valute_info).format(euro.name, euro.value)
    }
}