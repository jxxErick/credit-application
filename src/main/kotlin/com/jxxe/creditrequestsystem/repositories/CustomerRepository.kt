package com.jxxe.creditrequestsystem.repositories

import com.jxxe.creditrequestsystem.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
}