package com.example.wllet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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


class WalletActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var cards: ArrayList<CreditCard>
    private lateinit var rcCardList: RecyclerView
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        rcCardList = findViewById(R.id.rcCardList)
        cards = arrayListOf<CreditCard>()

        database = Firebase.database.reference
        user = intent.getParcelableExtra("user")
        val cardListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cardsFromDatabase = snapshot.child("cards")
                cards.clear()

                for (snapshot in cardsFromDatabase.children) {

                    val deep = snapshot.value as HashMap<String, String>

                    val card: CreditCard = CreditCard.from(deep)
                    if (card.email == user?.email.toString()) {
                        cards.add(card)
                    }

                }

                rcCardList.adapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Wllet", error.toString())
            }
        }

        database.addValueEventListener(cardListener)

        rcCardList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcCardList.adapter = CardAdapter(cards)
    }

    fun addCard(view: View) {
        val intent = Intent(this, AddCardActivity::class.java).putExtra("user", user)
        view.context.startActivity(intent)
    }
    fun gotoexchange(view: View) {
        val intent = Intent(this, ExchangeActivity::class.java).putExtra("user", user)
        view.context.startActivity(intent)
    }
}
