package com.example.lab18

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.lab18.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.URL
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ValueAdapter
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnGetValue.setOnClickListener {
            loadData()
        }

    }

    private fun setupRecyclerView() {
        adapter = ValueAdapter()
        binding.rvValueList.adapter = adapter
    }

    private fun loadData() {
        screenLoad()

        scope.launch {
            val valueList = mutableListOf<ValueItem>()
            val xml = URL("https://www.cbr.ru/scripts/XML_daily.asp")
                .readText(Charset.forName("Windows-1251"))
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            parser.setInput(StringReader(xml))

            var eventType = parser.eventType
            var charCode = ""
            var name = ""
            var value = ""



            while (eventType != XmlPullParser.END_DOCUMENT) {

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "CharCode" -> charCode = parser.nextText()
                            "Name" -> name = parser.nextText()
                            "Value" -> value = parser.nextText()
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == "Valute") {
                            valueList.add(
                                ValueItem(
                                    name,
                                    value
                                )
                            )
                        }
                    }
                }

                eventType = parser.next()
            }


            runOnUiThread {
                showContent(valueList)
            }

        }


    }

    private fun screenLoad() {
        with(binding) {
            pbLoadValueList.isVisible = true
            rvValueList.isVisible = false
        }
    }

    private fun showContent(valueList: List<ValueItem>) {
        with(binding) {
            pbLoadValueList.isVisible = false
            rvValueList.isVisible = true
        }

        adapter.valueList = valueList
    }
}