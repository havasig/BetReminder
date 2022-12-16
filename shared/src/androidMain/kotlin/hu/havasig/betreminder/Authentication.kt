package hu.havasig.betreminder

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth

class AndroidAuthentication : Authentication {
    override val auth: FirebaseAuth = Firebase.auth
}

actual suspend fun createFirebaseUser(email: String, password: String) =
    AndroidAuthentication().auth.createUserWithEmailAndPassword(email, password).user

actual suspend fun loginFirebaseUser(email: String, password: String) =
    AndroidAuthentication().auth.signInWithEmailAndPassword(email, password).user
