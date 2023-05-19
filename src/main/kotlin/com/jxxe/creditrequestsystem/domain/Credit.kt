package com.jxxe.creditrequestsystem.domain

import com.jxxe.creditrequestsystem.ennumeration.Status
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity

data class Credit (
   @Column (nullable = false, unique = true) val creditCode: UUID = UUID.randomUUID(),
   @Column (nullable = false) val creditValue: BigDecimal = BigDecimal.ZERO,
   @Column (nullable = false)  val dayFisrtInstallment: LocalDate,
   @Column (nullable = false)   val numberOfInstallments: Int = 0,
    @Enumerated val status: Status = Status.IN_PROGRESS,
    @ManyToOne  var customer: Customer? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Credit

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
