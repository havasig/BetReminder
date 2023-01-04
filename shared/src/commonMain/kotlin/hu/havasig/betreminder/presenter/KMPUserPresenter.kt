package hu.havasig.betreminder.presenter

import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.data.DefaultData
import hu.havasig.betreminder.data.UserRepository
import kotlin.coroutines.cancellation.CancellationException

class KMPUserPresenter(private val repository: UserRepository) {
    fun sayHello() : String {
        val name = DefaultData.DEFAULT_USER.name
        val foundUser = repository.getUser(name)
        return foundUser?.let { "Hello '$it' from $this" } ?: "User '$name' not found!"
    }

    @Throws(NullPointerException::class, CancellationException::class)
    suspend fun loadMe(): DocumentSnapshot {
        return repository.loadMe()
    }

    @Throws(NullPointerException::class, CancellationException::class)
    suspend fun getFriendUserList(): List<DocumentSnapshot> {
        return repository.getFriendUserList()
    }

    suspend fun getBetParticipants(bet: DocumentSnapshot): List<DocumentSnapshot> {
        return repository.getUserListId(bet.get("participants"))
    }
}
