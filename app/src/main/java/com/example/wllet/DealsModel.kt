package com.example.wllet

data class DealsModel(val title: String, val price: String, val email: String, val image: Int) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val title by map
            val price by map
            val email by map
            val image by map

            val data = DealsModel(title, price, email, image = R.drawable.placeholder)
        }.data
    }
}