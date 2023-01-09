package com.example.dixitstore.fragment

data class AddProductModel(
    val productName:String? = "",
    val productDescription:String? = "",
    val productCoverImg:String? = "",
    val productCategory:String? = "",
    val productID:String? = "",
    val productMrp:String? = "",
    val productSp:String? = "",
    val productImages:ArrayList<String> =ArrayList()


)
