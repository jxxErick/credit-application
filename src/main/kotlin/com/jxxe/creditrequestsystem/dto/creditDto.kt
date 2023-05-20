package com.jxxe.creditrequestsystem.dto

import com.jxxe.creditrequestsystem.domain.Credit
import com.jxxe.creditrequestsystem.domain.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class creditDto(
    val creditValue: BigDecimal,
    val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,
    val customerId: Long
)
{
fun toEntity(): Credit = Credit(
    creditValue = this.creditValue,
    dayFisrtInstallment = this.dayFirstOfInstallment,
    numberOfInstallments = this.numberOfInstallments,
    customer = Customer(id = this.customerId)
)
}
