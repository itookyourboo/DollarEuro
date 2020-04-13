package com.insaze.dollareuro


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insaze.dollareuro.model.Valute
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val extras = intent.extras

        val date = extras?.get("date")
        titleInfo.text = "Курс валют на $date"

        val dollar = extras?.get("dollar") as Valute
        dollarInfo.text = "${dollar.name}\n${dollar.value}"

        val euro = extras?.get("euro") as Valute
        euroInfo.text = "${euro.name}\n${euro.value}"
    }
}