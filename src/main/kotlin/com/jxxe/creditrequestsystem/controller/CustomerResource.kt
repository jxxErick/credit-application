package com.jxxe.creditrequestsystem.controller

import com.jxxe.creditrequestsystem.domain.Customer
import com.jxxe.creditrequestsystem.dto.CustomerDto
import com.jxxe.creditrequestsystem.dto.CustomerUpdateDto
import com.jxxe.creditrequestsystem.dto.CustomerView
import com.jxxe.creditrequestsystem.service.impl.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerResource (
    private val customerService: CustomerService
){

    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto) : String {
       val savedCustomer = this.customerService.save(customerDto.toEntity())
        return "Customer ${savedCustomer.email} saved!"

    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): CustomerView {
    val customer : Customer = this.customerService.findById(id)
    return CustomerView(customer)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long) = this.customerService.delete(id)

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId") id: Long, @RequestBody customerUpdateDto: CustomerUpdateDto): CustomerView{
        val customer: Customer = this.customerService.findById(id)
        val customerToUpdate: Customer = customerUpdateDto.toEntity(customer)
        val customerUpdate: Customer = this.customerService.save(customerToUpdate)
        return CustomerView(customerUpdate)
    }
}