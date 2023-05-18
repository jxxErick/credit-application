package com.jxxe.creditrequestsystem.domain

import com.jxxe.creditrequestsystem.ennumeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class Credit (
    val creditCode: UUID = UUID.randomUUID(),
    val creditValue: BigDecimal = BigDecimal.ZERO,
    val dayFisrtInstallment: LocalDate,
    val numberOfInstallments: Int = 0,
    val status: Status = Status.IN_PROGRESS,
    val customer: Customer? = null,
    val id: Long? = null
)
