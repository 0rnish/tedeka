package com.example.turkcesozluk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.turkcesozluk.databinding.ActivityMainBinding
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        suspend fun tdk(word: String): JSONObject{
            val url = "https://sozluk.gov.tr/gts?ara=$word"
            val client = HttpClient(CIO)
            val response: HttpResponse = client.get(url)
            val resultArr = response.receive<String>()
            client.close()
            return JSONArray(resultArr)[0] as JSONObject
        }
         binding.btnSearch.setOnClickListener {
             MainScope().launch {
                 binding.tvMeaning.text = tdk(binding.etInput.text.toString()).toString()
             }
         }

    }
}