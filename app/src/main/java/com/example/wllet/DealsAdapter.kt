package com.example.wllet

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.deals_row.view.*

class DealsAdapter(val arrayList: ArrayList<DealsModel>, val context: Context) :
    RecyclerView.Adapter<DealsAdapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindItems(model: DealsModel) {
                itemView.titleTv.text = model.title
                itemView.priceTv.text = model.price
                itemView.imageIv.setImageResource(model.image)
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
        holder.itemView.setOnClickListener{

            /*if (position == 0) {
                Toast.makeText(
                    context,
                    "You subscribed Netflix",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (position == 2) {
                Toast.makeText(
                    context,
                    "You subscribed Spotify",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (position == 2) {
                Toast.makeText(
                    context,
                    "You subscribed Disney+",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (position == 3) {
                Toast.makeText(
                    context,
                    "You subscribed HBO",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
            val model = arrayList.get(position)

            val gTitle : String = model.title
            val gPrice : String = model.price
            val gImageView : Int = model.image

            val intent = Intent(context, SubscriptionActivity::class.java)

            intent.putExtra("iTitle", gTitle)
            intent.putExtra("iPrice", gPrice)
            intent.putExtra("aImageView", gImageView)

            context.startActivity(intent)
        }
    }
}
