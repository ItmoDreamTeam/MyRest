package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [ReservationTest.Config::class])
class ReservationTest {

    @Autowired
    lateinit var reservationService: ReservationService

    @Autowired
    lateinit var reservationRepository: ReservationRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var restaurantTableRepository: RestaurantTableRepository

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @MockBean
    lateinit var notificationService: NotificationService

    @MockBean
    lateinit var currentUserService: CurrentUserService

    private lateinit var reservation: Reservation
    private lateinit var table: RestaurantTable
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User
    private lateinit var manager: Manager

    @Before
    fun setup() {
        val restaurant = restaurantRepository.save(Restaurant("name", "desc", "legal"))
        table = restaurantTableRepository.save(RestaurantTable(restaurant, 1, null, 1))
        user1 = userRepository.save(User("Alex", "Bowie"))
        user2 = userRepository.save(User("Sam", "Ivanov"))
        user3 = userRepository.save(User("Karl", "Marks"))
        manager = employeeRepository.save(Manager(restaurant, user3))
        val now = LocalDateTime.now()
        authorize(user1)
        reservation = reservationRepository.save(Reservation(user1, table, now.plusHours(1), now.plusHours(5)))
    }

    private fun authorize(user: User) {
        Mockito.`when`(currentUserService.currentUserEntity).thenReturn(user)
    }

    @Test
    fun `When create new reservation, then new reservation created`() {
        val reservation = reservationRepository.findByIdOrNull(reservation.id)
        Assertions.assertThat(reservation).isNotNull
        Assertions.assertThat(reservation?.status).isEqualTo(ReservationStatus.PENDING)
        Assertions.assertThat(reservation?.user).isEqualTo(user1)
        Assertions.assertThat(reservation?.table).isEqualTo(table)
    }

    @Test
    fun `When create new reservation right after existing one, then new a reservation created`() {
        authorize(user2)
        val reservation = reservationService.submitReservationForApproval(
            table,
            reservation.activeUntil,
            reservation.activeUntil.plusHours(2)
        )
        Assertions.assertThat(reservation).isNotNull
        Assertions.assertThat(reservation.status).isEqualTo(ReservationStatus.PENDING)
        Assertions.assertThat(reservation.user).isEqualTo(user2)
        Assertions.assertThat(reservation.table).isEqualTo(table)
    }

    @Test(expected = UserException::class)
    fun `Given existing reservation, when create reservation to same table and overlapping time, then failure`() {
        authorize(user2)
        reservationService.submitReservationForApproval(
            table,
            reservation.activeFrom.minusHours(2),
            reservation.activeFrom.plusMinutes(1)
        )
    }

    @Test
    fun `Given rejected existing reservation, when create reservation to same table and time, then a reservation created`() {
        authorize(user1)
        reservationService.reject(reservation)
        reservationService.submitReservationForApproval(table, reservation.activeFrom, reservation.activeUntil)
    }

    @Test(expected = UserException::class)
    fun `When try to reject other user's reservation, then failure`() {
        authorize(user2)
        reservationService.reject(reservation)
    }

    @Test
    fun `Given existing reservation in state PENDING, when manager approves it, then reservation is APPROVED`() {
        authorize(user3)
        val approved = reservationService.approve(reservation)
        Assertions.assertThat(approved.status).isEqualTo(ReservationStatus.APPROVED)
    }

    @Test(expected = UserException::class)
    fun `Given existing PENDING reservation and manager who is not responsible to the reservation, when try to approve it, then failure`() {
        authorize(user1)
        val approved = reservationService.approve(reservation)
        Assertions.assertThat(approved.status).isEqualTo(ReservationStatus.APPROVED)
    }

    @TestConfiguration
    @ComponentScan
    class Config
}