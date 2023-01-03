package hu.havasig.betreminder.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Repository to provide a "Hello" data
 */

interface BetRepository {

    suspend fun getBets(userId: String): MutableList<DocumentSnapshot>
    fun addBet(bet: Bet)
}

class BetRepositoryImpl : BetRepository {
    private val _db = Firebase.firestore

    private val _bets = mutableListOf<DocumentSnapshot>()

    override suspend fun getBets(userId: String): MutableList<DocumentSnapshot> {
        _bets.clear()
        val a: DocumentSnapshot = _db.collection("bets").where("name", "My first bet").get().documents.first()
        println("id value: $a")
        //_bets.add(_db.collection("bets").where("name", "My first bet"))
        _bets.add(a)
        return _bets
    }

    override fun addBet(bet: Bet) {
        runBlocking {
            _db.collection("bets").add(
                Bet(
                    "",
                    Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    "",
                    "",
                    mutableListOf()
                )
            )
        }
    }
}
