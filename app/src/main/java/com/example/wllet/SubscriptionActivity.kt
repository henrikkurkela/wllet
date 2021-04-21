package com.example.wllet

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity: AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var subs: ArrayList<Subscription>
    private lateinit var rcSubsList: RecyclerView
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        rcSubsList = findViewById(R.id.rcSubsList)
        subs = arrayListOf<Subscription>()

        database = Firebase.database.reference
        user = intent.getParcelableExtra("user")

        val subListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val subsFromDatabase = snapshot.child("subscriptions")
                subs.clear()

                for (snapshot in subsFromDatabase.children) {

                    val deep = snapshot.value as HashMap<String, String>
                    val sub: Subscription = Subscription.from(deep)
                    if (sub.email == user?.email.toString()) {
                        subs.add(sub)
                    }
                }
                rcSubsList.adapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Wllet", error.toString())
            }
        }
        database.addValueEventListener(subListener)

        rcSubsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcSubsList.adapter = SubscriptionAdapter(subs)

       /* val intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aPrice = intent.getStringExtra("iPrice")
        val aImageView = intent.getIntExtra("iImageView", 0)

        a_title.text = aTitle
        a_price.text = aPrice
        imageView.setImageResource(aImageView)*/
    }
}