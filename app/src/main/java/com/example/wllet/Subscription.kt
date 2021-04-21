package com.example.wllet

data class Subscription(val title: String, val price: String, val email: String) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val title by map
            val price by map
            val email by map

            val data = Subscription(title, price, email)
        }.data
    }
}
