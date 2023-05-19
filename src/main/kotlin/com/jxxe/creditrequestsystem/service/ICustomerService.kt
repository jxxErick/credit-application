package com.jxxe.creditrequestsystem.service

import com.jxxe.creditrequestsystem.domain.Customer

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long): Customer
}