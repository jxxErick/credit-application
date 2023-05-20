package com.jxxe.creditrequestsystem.controller

import com.jxxe.creditrequestsystem.domain.Credit
import com.jxxe.creditrequestsystem.dto.CreditView
import com.jxxe.creditrequestsystem.dto.CreditViewList
import com.jxxe.creditrequestsystem.dto.creditDto
import com.jxxe.creditrequestsystem.service.impl.CreditService
import org.springframework.web.bind.annotation.*
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/ap/credits")
class CreditResource (
    private val creditService: CreditService
){
 @PostMapping
 fun saveCredit(@RequestBody creditDto: creditDto): String{
    val credit: Credit = this.creditService.save(creditDto.toEntity())
     return "Credit ${credit.creditCode} - customer ${credit.customer?.firstName} saved"
 }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): List<CreditViewList>{
        return this.creditService.findAllByCustomer(customerId).stream()
            .map { credit: Credit -> CreditViewList(credit) }.collect(
            Collectors.toList())
    }

    @GetMapping
    fun findByCreditCode(@RequestParam(value = "customerId")customerId: Long, @PathVariable creditCode: UUID ) : CreditView {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return CreditView(credit)
    }
}