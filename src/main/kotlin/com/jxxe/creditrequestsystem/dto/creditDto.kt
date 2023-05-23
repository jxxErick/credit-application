package com.jxxe.creditrequestsystem.dto

import com.jxxe.creditrequestsystem.domain.Credit
import com.jxxe.creditrequestsystem.domain.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class creditDto(
    @field:NotNull(message = "nao pode estar vazio")val creditValue: BigDecimal,
   @field:Future val dayFirstOfInstallment: LocalDate,
   @field:NotNull(message = "nao pode estar vazio") @field:Max(value = 48, message = "o numero de parcelas nao pode ser maior que 48") val numberOfInstallments: Int,
    val customerId: Long
)
{
fun toEntity(): Credit = Credit(
    creditValue = this.creditValue,
    dayFirstInstallment = this.dayFirstOfInstallment,
    numberOfInstallments = this.numberOfInstallments,
    customer = Customer(id = this.customerId)
)
}
