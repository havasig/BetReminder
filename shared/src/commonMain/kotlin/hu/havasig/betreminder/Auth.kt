package hu.havasig.betreminder

import dev.gitlive.firebase.auth.FirebaseUser

class Auth {
    var currentUser: FirebaseUser? = null

    @Throws(Exception::class)
    suspend fun createUser(email: String, password: String) {
        currentUser = createFirebaseUser(email, password)
    }

    @Throws(Exception::class)
    suspend fun loginUser(email: String, password: String) {
        currentUser = loginFirebaseUser(email, password)
    }
}
