package hu.havasig.betreminder.data

import kotlinx.datetime.LocalDate

data class Bet(
    val title: String,
    val prize: String,
    val date: LocalDate,
    val deadline: LocalDate,
    val description: String,
    val closed: Boolean,
    val participants: List<Participant>,
) {
    fun toDto(): HashMap<String, Any> {
        return hashMapOf(
            "title" to title,
            "prize" to prize,
            "date" to date,
            "deadline" to deadline,
            "description" to description,
            "closed" to false,
            "participants" to participants.map { it.toDto() },
        )
    }
}
