import com.jxxe.creditrequestsystem.controller.CreditResource
import com.jxxe.creditrequestsystem.domain.Address
import com.jxxe.creditrequestsystem.domain.Credit
import com.jxxe.creditrequestsystem.domain.Customer
import com.jxxe.creditrequestsystem.dto.CreditView
import com.jxxe.creditrequestsystem.dto.CreditViewList
import com.jxxe.creditrequestsystem.dto.CreditDto
import com.jxxe.creditrequestsystem.ennumeration.Status
import com.jxxe.creditrequestsystem.service.impl.CreditService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import java.util.stream.Collectors

class CreditResourceTest {

    private lateinit var creditService: CreditService
    private lateinit var creditResource: CreditResource

    @BeforeEach
    fun setup() {
        creditService = mockk(relaxed = true)
        creditResource = CreditResource(creditService)
    }
    fun `should save credit`() {
        // given
        val creditDto = CreditDto(
            creditValue = BigDecimal.valueOf(1000),
            dayFirstOfInstallment = LocalDate.now().plusDays(1),
            numberOfInstallments = 12,
            customerId = 1
        )
        val credit = Credit(
            creditValue = creditDto.creditValue,
            dayFirstInstallment = creditDto.dayFirstOfInstallment,
            numberOfInstallments = creditDto.numberOfInstallments,
            customer = Customer(id = creditDto.customerId)
        )
        val expectedResponse = ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${credit.creditCode} - customer ${credit.customer?.firstName} saved")

        every { creditService.save(any()) } returns credit

        // when
        val response = creditResource.saveCredit(creditDto)

        // then
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `should find all credits by customer id`() {
        // given
        val customerId = 1L
        val credits = listOf(buildCredit())
        val creditViewList = credits.stream()
            .map { credit: Credit -> CreditViewList(credit) }
            .collect(Collectors.toList())
        val expectedResponse = ResponseEntity.status(HttpStatus.OK).body(creditViewList)

        every { creditService.findAllByCustomer(customerId) } returns credits

        // when
        val response = creditResource.findAllByCustomerId(customerId)

        // then
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `should find credit by credit code`() {
        // given
        val customerId = 1L
        val creditCode = UUID.randomUUID()
        val credit = buildCredit()
        val expectedResponse = ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))

        every { creditService.findByCreditCode(customerId, creditCode) } returns credit

        // when
        val response = creditResource.findByCreditCode(customerId, creditCode)

        // then
        assertEquals(expectedResponse, response)
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

    private fun buildCreditDto(
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