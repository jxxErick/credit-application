package com.jxxe.creditrequestsystem.dto

import com.jxxe.creditrequestsystem.domain.Customer
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CustomerUpdateDto(
    @field:NotEmpty(message = "O campo não pode ficar em branco") val firstName: String,
    @field:NotEmpty(message = "O campo não pode ficar em branco") val lastName: String,
    @field:NotNull(message = "invalid") val income: BigDecimal,
    @field:NotEmpty(message = "O campo não pode ficar em branco") val zipCode: String,
    @field:NotEmpty(message = "O campo não pode ficar em branco") val street: String
) {
    fun toEntity(customer: Customer): Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.street = this.street
        customer.address.zipCode = this.zipCode
        return customer
    }
}
