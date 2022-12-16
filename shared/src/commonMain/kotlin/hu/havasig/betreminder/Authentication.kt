package hu.havasig.betreminder

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser

interface Authentication {
    val auth: FirebaseAuth
}

expect suspend fun createFirebaseUser(email: String, password: String): FirebaseUser?

expect suspend fun loginFirebaseUser(email: String, password: String): FirebaseUser?
