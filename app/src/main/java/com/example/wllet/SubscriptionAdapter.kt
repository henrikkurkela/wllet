package com.example.wllet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class SubscriptionAdapter(private val arrayList: ArrayList<Subscription>) :
    RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewSubTitle)
        val price: TextView = itemView.findViewById(R.id.textViewSubPrice)
        val remove: Button = itemView.findViewById(R.id.buttonRemoveSub)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView =
            LayoutInflater.from(parent.context).inflate(R.layout.subscription_row, parent, false)
        return MyViewHolder(myView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = arrayList[position].title
        holder.price.text = arrayList[position].price

        holder.remove.setOnClickListener {

            val ref: DatabaseReference = FirebaseDatabase.getInstance().reference
            val query: Query =
                ref.child("subscriptions").orderByChild("title").equalTo(arrayList[position].title)

            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        ds.ref.removeValue()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("Wllet", databaseError.message)
                }
            }
            query.addListenerForSingleValueEvent(valueEventListener)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}