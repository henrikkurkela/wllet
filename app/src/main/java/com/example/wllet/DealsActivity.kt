package com.example.wllet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_deals.*

class DealsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deals)

        val arrayList = ArrayList<DealsModel>()

        arrayList.add(DealsModel("Netflix", "7.99€", R.drawable.placeholder))
        arrayList.add(DealsModel("Spotify", "10.99€", R.drawable.placeholder))
        arrayList.add(DealsModel("Disney+", "8.99€", R.drawable.placeholder))
        arrayList.add(DealsModel("HBO", "10.95€", R.drawable.placeholder))

        val dealsAdapter = DealsAdapter(arrayList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dealsAdapter
    }
}

