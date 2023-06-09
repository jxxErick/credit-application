package com.jxxe.creditrequestsystem.service.impl

import com.jxxe.creditrequestsystem.domain.Credit
import com.jxxe.creditrequestsystem.exception.BusinessException
import com.jxxe.creditrequestsystem.repositories.CreditRepository
import com.jxxe.creditrequestsystem.service.ICreditService
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class CreditService(
    private val customerService: CustomerService,
    private val creditRepository: CreditRepository
): ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomer(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode) ?:
        throw BusinessException("Credit Code $creditCode not found"))
        return if (credit.customer?.id == customerId) credit else throw BusinessException("Contact admin")
    }

}