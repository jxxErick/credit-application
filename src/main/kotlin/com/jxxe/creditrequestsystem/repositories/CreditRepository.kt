package com.jxxe.creditrequestsystem.repositories

import com.jxxe.creditrequestsystem.domain.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CreditRepository: JpaRepository<Credit, Long> {
}