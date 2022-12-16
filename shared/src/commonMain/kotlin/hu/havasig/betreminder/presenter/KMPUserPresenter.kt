package hu.havasig.betreminder.presenter

import hu.havasig.betreminder.data.DefaultData
import hu.havasig.betreminder.data.UserRepository

class KMPUserPresenter(private val repository: UserRepository) {
    fun sayHello() : String {
        val name = DefaultData.DEFAULT_USER.name
        val foundUser = repository.findUser(name)
        return foundUser?.let { "Hello '$it' from $this" } ?: "User '$name' not found!"
    }
}
