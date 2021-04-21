package com.example.wllet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_deals.*

class DealsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var deals: ArrayList<DealsModel>
    private lateinit var rcDealsList: RecyclerView
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deals)

        database = Firebase.database.reference
        deals = arrayListOf<DealsModel>()
        rcDealsList = findViewById(R.id.recyclerView)
        user = intent.getParcelableExtra("user")

        val dealsListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dealsFromDatabase = snapshot.child("deals")
                deals.clear()

                for (snapshot in dealsFromDatabase.children) {
                    val deep = snapshot.value as HashMap<String, String>
                    val deal: DealsModel = DealsModel.from(deep)
                    deals.add(deal)
                }
                rcDealsList.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("wllet", error.toString())
            }
        }
        database.addValueEventListener(dealsListener)

        val dealsAdapter = DealsAdapter(deals, this, user)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dealsAdapter
    }
}