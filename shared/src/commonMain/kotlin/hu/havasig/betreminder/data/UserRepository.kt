package hu.havasig.betreminder.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlin.coroutines.cancellation.CancellationException

/**
 * Repository to provide a "Hello" data
 */

interface UserRepository {

    fun getUser(name: String): User?
    fun addUsers(users: List<User>)

    suspend fun getUserById(userId: String): DocumentSnapshot

    suspend fun getUserListId(userIds: List<String>): List<DocumentSnapshot>

    @Throws(NullPointerException::class, CancellationException::class)
    suspend fun getFriendsOfUser(): List<DocumentSnapshot>

    @Throws(NullPointerException::class, CancellationException::class)
    suspend fun loadMe(): DocumentSnapshot

    suspend fun getCurrentUser(): DocumentSnapshot?
}

class UserRepositoryImpl : UserRepository {
    private val _db = Firebase.firestore
    private var _me: DocumentSnapshot? = null

    private val _users = arrayListOf<User>()

    override fun getUser(name: String): User? {
        return _users.firstOrNull { it.name == name }
    }

    override fun addUsers(users: List<User>) {
        _users.addAll(users)
    }

    @Throws(NullPointerException::class, CancellationException::class)
    override suspend fun loadMe(): DocumentSnapshot {
        val currentUserId =
            Firebase.auth.currentUser?.uid ?: throw NullPointerException("Firebase current user is null")
        _me = _db.collection("users").where("firebaseId", currentUserId).get().documents.first()
        return _me!!
    }

    override suspend fun getCurrentUser(): DocumentSnapshot? {
        return _me
    }

    override suspend fun getUserById(userId: String): DocumentSnapshot {
        return _db.collection("users").document(userId).get()
    }

    override suspend fun getUserListId(userIds: List<String>): List<DocumentSnapshot> {
        val results = mutableListOf<DocumentSnapshot>()
        for (userId in userIds) {
            results.add(_db.collection("users").document(userId).get())
        }
        return results
    }

    @Throws(NullPointerException::class, CancellationException::class)
    override suspend fun getFriendsOfUser(): List<DocumentSnapshot> { // TODO this is only the current user
        val currentUserId =
            Firebase.auth.currentUser?.uid ?: throw NullPointerException("Firebase current user is null")
        val friendUserIds = _db.collection("users")
            .where("firebaseId", currentUserId)
            .limit(1)
            .get()
            .documents
            .first()
            .get("friends") as List<String>

        return friendUserIds.map { friendUserId -> _db.collection("users").document(friendUserId).get() }
    }
}
