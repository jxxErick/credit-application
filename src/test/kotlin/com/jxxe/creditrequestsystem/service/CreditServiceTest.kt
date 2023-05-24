package com.jxxe.creditrequestsystem.service

import com.jxxe.creditrequestsystem.domain.Address
import com.jxxe.creditrequestsystem.domain.Credit
import com.jxxe.creditrequestsystem.domain.Customer
import com.jxxe.creditrequestsystem.ennumeration.Status
import com.jxxe.creditrequestsystem.exception.BusinessException
import com.jxxe.creditrequestsystem.repositories.CreditRepository
import com.jxxe.creditrequestsystem.service.impl.CreditService
import com.jxxe.creditrequestsystem.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK
    lateinit var customerService: CustomerService

    @MockK
    lateinit var creditRepository: CreditRepository

    lateinit var creditService: CreditService

    private val customerId: Long = 1L
    private val creditCode: UUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        creditService = CreditService(customerService, creditRepository)
    }

    @Test
    fun `should save credit`() {
        // given
        val fakeCustomer = buildCustomer()
        val fakeCredit = buildCredit(customer = fakeCustomer)

        every { customerService.findById(fakeCustomer.id!!) } returns fakeCustomer
        every { creditRepository.save(fakeCredit) } returns fakeCredit

        // when
        val actual = creditService.save(fakeCredit)

        // then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { customerService.findById(fakeCustomer.id!!) }
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun `should find all credits by customer`() {
        // given
        val fakeCredits = listOf(buildCredit(), buildCredit(), buildCredit())

        every { creditRepository.findAllByCustomer(customerId) } returns fakeCredits

        // when
        val actual = creditService.findAllByCustomer(customerId)

        // then
        assertThat(actual).isNotNull
        assertThat(actual).isEqualTo(fakeCredits)
        verify(exactly = 1) { creditRepository.findAllByCustomer(customerId) }
    }

    @Test
    fun `should find credit by credit code for valid customer`() {
        // given
        val fakeCustomer = buildCustomer()
        val fakeCredit = buildCredit(customer = fakeCustomer)

        every { creditRepository.findByCreditCode(creditCode) } returns fakeCredit
        every { customerService.findById(customerId) } returns fakeCustomer

        // when
        val actual = creditService.findByCreditCode(customerId, creditCode)

        // then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { creditRepository.findByCreditCode(creditCode) }
        verify(exactly = 1) { customerService.findById(customerId) }
    }

    @Test
    fun `should throw BusinessException when finding credit by credit code for invalid customer`() {
        // given
        val fakeCustomer = buildCustomer()
        val fakeCredit = buildCredit(customer = fakeCustomer)

        every { creditRepository.findByCreditCode(creditCode) } returns fakeCredit
        every { customerService.findById(customerId) } returns buildCustomer(id = customerId + 1)

        // when
        val exception = assertThrows<BusinessException> {
            creditService.findByCreditCode(customerId, creditCode)
        }

        // then
        assertThat(exception).hasMessage("Contact admin")
        verify(exactly = 1) { creditRepository.findByCreditCode(creditCode) }
        verify(exactly = 1) { customerService.findById(customerId) }
    }

    @Test
    fun `should throw BusinessException when credit code not found`() {
        // given
        every { creditRepository.findByCreditCode(creditCode) } returns null

        // when
        val exception = assertThrows<BusinessException> {
            creditService.findByCreditCode(customerId, creditCode)
        }

        // then
        assertThat(exception).hasMessage("Credit Code $creditCode not found")
        verify(exactly = 1) { creditRepository.findByCreditCode(creditCode) }
    }

    private fun buildCustomer(
        firstName: String = "Erick",
        lastName: String = "Dantas",
        cpf: String = "28475934625",
        email: String = "ericksdantas144@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua do Erick",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        id: Long? = 1L
    ): Customer {
        return Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(
                zipCode = zipCode,
                street = street,
            ),
            income = income,
            id = id
        )
    }

    private fun buildCredit(
        creditCode: UUID = UUID.randomUUID(),
        creditValue: BigDecimal = BigDecimal.ZERO,
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallments: Int = 0,
        status: Status = Status.IN_PROGRESS,
        customer: Customer? = null,
        id: Long? = null
    ): Credit {
        return Credit(
            creditCode = creditCode,
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            status = status,
            customer = customer,
            id = id
        )
    }

}

