package com.example.wllet

class DealsModel(val title: String, val price: String, val image: Int) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val title by map
            val price by map
            val image by map

            val data = DealsModel(title, price, image = R.drawable.placeholder)
        }.data
    }
}