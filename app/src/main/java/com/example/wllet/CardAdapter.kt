package com.example.wllet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class CardAdapter(private val myDataset: ArrayList<CreditCard>) :
    RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById(R.id.textViewNumber)
        val holder: TextView = itemView.findViewById(R.id.textViewHolder)
        val valid: TextView = itemView.findViewById(R.id.textViewValid)
        val remove: Button = itemView.findViewById(R.id.buttonRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.card_row, parent, false)
        return MyViewHolder(myView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.number.text = myDataset[position].number
        holder.holder.text = myDataset[position].holder
        holder.valid.text = myDataset[position].valid

        holder.remove.setOnClickListener {

            val ref: DatabaseReference = FirebaseDatabase.getInstance().reference
            val query: Query =
                ref.child("cards").orderByChild("number").equalTo(myDataset[position].number)

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
        return myDataset.size
    }
}
