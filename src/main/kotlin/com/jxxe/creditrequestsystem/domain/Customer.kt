package com.jxxe.creditrequestsystem.domain

data class Customer(
    var fisrtName: String = "",
    var lastName: String = "",
    val cpf: String,
    var email: String = "",
    var password: String = "",
    var address: Address = Address(),
    var credits: List <Credit> = mutableListOf(),
    val id: Long? = null

)
