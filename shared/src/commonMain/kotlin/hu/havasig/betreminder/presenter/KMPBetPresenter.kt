package hu.havasig.betreminder.presenter

import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.data.BetRepository

class KMPBetPresenter(private val repository: BetRepository) {

    suspend fun getBets(userId: String) : List<DocumentSnapshot> {
        return repository.getBets(userId)
    }

    suspend fun getBetById(betId: String) : DocumentSnapshot? {
        return repository.getBetById(betId)
    }

    suspend fun loadMyBets(userId: String) {
        repository.getBets(userId)
    }
}
