package com.jxxe.creditrequestsystem.dto

import com.jxxe.creditrequestsystem.domain.Address
import com.jxxe.creditrequestsystem.domain.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto (
   @field:NotEmpty(message = "O campo não pode ficar em branco") val firstName: String,
   @field:NotEmpty(message = "O campo não pode ficar em branco")  val lastName: String,
   @field:NotEmpty(message = "O campo não pode ficar em branco")  @field:CPF (message = "cpf invalido") val cpf: String,
   @field:NotNull(message = "invalid") val income: BigDecimal,
   @field:NotEmpty(message = "O campo não pode ficar em branco")  @field:Email(message = "email invalido") val email: String,
   @field:NotEmpty(message = "O campo não pode ficar em branco")   val password: String,
   @field:NotEmpty(message = "O campo não pode ficar em branco")   val zipCode: String,
   @field:NotEmpty(message = "O campo não pode ficar em branco")  val street: String
)
{
    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}