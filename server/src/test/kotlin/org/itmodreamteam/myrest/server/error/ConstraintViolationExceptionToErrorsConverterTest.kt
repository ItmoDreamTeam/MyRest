package org.itmodreamteam.myrest.server.error

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.shared.error.ServerError
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import javax.validation.ConstraintViolationException

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [ErrorConverterTestConfig::class])
class ConstraintViolationExceptionToErrorsConverterTest {

    @Autowired
    lateinit var converter: ThrowableToErrorsConverter

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Test
    fun `Given restaurant with invalid attributes persisted, when convert exception, then return all constraint violations`() {
        val errors = try {
            restaurantRepository.save(Restaurant("X", "", ""))
            emptyList()
        } catch (cve: ConstraintViolationException) {
            converter.convert(cve)
        }
        assertThat(errors).containsExactlyInAnyOrder(
            ServerError("restaurant.name.size", "Длина названия ресторана должна быть от 2 до 50 символов"),
            ServerError("restaurant.description.blank", "Введите описание ресторана"),
            ServerError("restaurant.legal-info.blank", "Введите информацию о юрлице или ИП – владельце ресторана"),
        )
    }
}
