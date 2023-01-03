package hu.havasig.betreminder

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.firestore

class Auth {
    var currentUser: FirebaseUser? = null
    val db = Firebase.firestore


    @Throws(Exception::class)
    suspend fun createUser(email: String, password: String) {
        currentUser = createFirebaseUser(email, password)
    }

    @Throws(Exception::class)
    suspend fun loginUser(email: String, password: String) {
        currentUser = loginFirebaseUser(email, password)
    }
}
