package hu.havasig.betreminder.data

data class Participant(
    val id: String,
    val name: String,
    val inviteAccepted: Boolean?,
    val winner: Boolean?,
) {
    fun toDto(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "invite_accepted" to inviteAccepted,
            "winner" to winner
        )
    }
}
