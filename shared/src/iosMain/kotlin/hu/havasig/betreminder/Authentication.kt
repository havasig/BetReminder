package hu.havasig.betreminder

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth

class IOSAuthentication : Authentication {
    override val auth: FirebaseAuth = Firebase.auth
}

actual suspend fun createFirebaseUser(email: String, password: String) =
    IOSAuthentication().auth.createUserWithEmailAndPassword(email, password).user

actual suspend fun loginFirebaseUser(email: String, password: String) =
    IOSAuthentication().auth.signInWithEmailAndPassword(email, password).user
