package hu.havasig.betreminder.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


interface BetRepository {

    suspend fun getBets(userId: String): MutableList<DocumentSnapshot>
    suspend fun getBetById(betId: String): DocumentSnapshot?
    suspend fun loadMyBets(userId: String)

    suspend fun createBet(
        title: String,
        prize: String,
        deadline: LocalDate,
        description: String,
        participants: List<Participant>,
    ): Boolean
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

    override suspend fun getBetById(betId: String): DocumentSnapshot? {
        // TODO loadMyBets("userId: String")
        return _bets.find { it.id == betId }
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

    override suspend fun createBet(
        title: String,
        prize: String,
        deadline: LocalDate,
        description: String,
        participants: List<Participant>,
    ): Boolean {
        _db.collection("bets")
            .add(
                Bet(
                    title = title,
                    prize = prize,
                    date = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
                    deadline = deadline,
                    description = description,
                    closed = false,
                    participants = participants
                ).toDto()
            )
        return true
    }
}
