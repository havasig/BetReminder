package hu.havasig.betreminder.presenter

import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.data.Bet
import hu.havasig.betreminder.data.BetRepository
import hu.havasig.betreminder.data.DefaultData
import hu.havasig.betreminder.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class KMPBetPresenter(private val repository: BetRepository) {
    suspend fun getMyBets() : List<DocumentSnapshot> {
           return repository.getBets("myId")
    }
}
