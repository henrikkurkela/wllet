package com.example.wllet

data class CreditCard(val number: String, val holder: String, val email: String, val cvc: String, val valid: String) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val number by map
            val holder by map
            val email by map
            val cvc by map
            val valid by map

            val data = CreditCard(number, holder, email, cvc, valid)
        }.data
    }
}
