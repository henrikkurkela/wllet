package com.example.wllet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.deals_row.view.*

class DealsAdapter(
    private val arrayList: ArrayList<DealsModel>,
    val context: Context,
    private val user: FirebaseUser? = null
) :
    RecyclerView.Adapter<DealsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model: DealsModel) {
            itemView.titleTv.text = model.title
            itemView.priceTv.text = model.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.deals_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        holder.itemView.setOnClickListener {

            val model = arrayList[position]
            val newSub: HashMap<String, Any> = HashMap()

            newSub["title"] = model.title
            newSub["price"] = model.price
            newSub["image"] = model.image
            newSub["email"] = user?.email.toString()

            val ref: DatabaseReference = FirebaseDatabase.getInstance().reference
            ref.child("subscriptions").push().setValue(newSub)

            if (position == 0) {
                Toast.makeText(
                    context,
                    "You subscribed to " + model.title,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (position == 1) {
                Toast.makeText(
                    context,
                    "You subscribed to " + model.title,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (position == 2) {
                Toast.makeText(
                    context,
                    "You subscribed to " + model.title,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (position == 3) {
                Toast.makeText(
                    context,
                    "You subscribed to " + model.title,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
