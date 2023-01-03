package hu.havasig.betreminder.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import io.ktor.util.logging.KtorSimpleLogger
import io.ktor.util.logging.Logger
import io.ktor.util.logging.error
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Repository to provide a "Hello" data
 */

interface BetRepository {

    suspend fun getBets(userId: String): MutableList<DocumentSnapshot>
    suspend fun loadMyBets(userId: String)
    fun addBet(bet: Bet)
}

class BetRepositoryImpl : BetRepository {
    private val _db = Firebase.firestore

    private val _bets = mutableListOf<DocumentSnapshot>()
    private val _myBets = mutableListOf<DocumentSnapshot>()

    override suspend fun getBets(userId: String): MutableList<DocumentSnapshot> {

        //_bets.add(_db.collection("bets").where("name", "My first bet"))

        _bets.clear()
        _bets.addAll(
            _db.collection("bets")
                .where("participants", arrayContains = userId)
                .get()
                .documents
        )

        return _bets
    }

    override suspend fun loadMyBets(userId: String) {
        _myBets.clear()
        _myBets.addAll(
            _db.collection("bets")
                .where("participants", arrayContains = userId)
                .get()
                .documents
        )
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
