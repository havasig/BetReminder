package hu.havasig.betreminder.data

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class Bet(
    val bet: String,
    val date: LocalDateTime,
    val deadline: LocalDateTime,
    val description: String,
    val name: String,
    val participants: List<String>
) {
}