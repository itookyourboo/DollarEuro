package com.insaze.dollareuro.network

import com.insaze.dollareuro.model.Valute
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.URL
import java.nio.charset.Charset

class ValuteService {

    private val DOLLAR_ID = "R01235"
    private val EURO_ID = "R01239"

    fun getData(date: String): ArrayList<Valute>? = try {
        val xml = URL("https://www.cbr.ru/scripts/XML_daily.asp?date_req=$date").readText(
            Charset.forName("windows-1251")
        )
        valuteList(xml)
    } catch (t: Throwable) {
        null
    }

    fun valuteList(xml: String): ArrayList<Valute> {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader(xml))

        val valuteList: ArrayList<Valute> = ArrayList()
        lateinit var currentValute: Valute

        while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.eventType == XmlPullParser.START_TAG) {
                when (xpp.name) {
                    "Valute" -> {
                        currentValute = Valute()
                        currentValute.id = xpp.getAttributeValue(0)
                    }
                    "Name" -> currentValute.name = xpp.nextText()
                    "Value" -> {
                        currentValute.value = xpp.nextText()
                        valuteList.add(currentValute)
                    }
                }
            }
            xpp.next()
        }
        return valuteList
    }

    fun getDollar(list: ArrayList<Valute>) = list.find { v -> v.id == DOLLAR_ID }
    fun getEuro(list: ArrayList<Valute>) = list.find { v -> v.id == EURO_ID }
}