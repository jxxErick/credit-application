package com.jxxe.creditrequestsystem.domain

import jakarta.persistence.*
import java.math.BigDecimal

@Entity

data class Customer(

    @Column(nullable = false) var firstName: String = "",
    @Column(nullable = false) var lastName: String = "",
    @Column(nullable = false, unique = true) var cpf: String = "",
    @Column(nullable = false, unique = true) var email: String = "",
    @Column(nullable = false) var password: String = "",
    @Embedded @Column(nullable = false) var address: Address = Address(),
    @Column(nullable = false) var income: BigDecimal = BigDecimal.ZERO,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "customer")
    var credits: List<Credit> = mutableListOf(),

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)