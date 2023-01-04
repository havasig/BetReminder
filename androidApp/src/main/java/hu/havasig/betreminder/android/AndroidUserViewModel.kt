package hu.havasig.betreminder.android

import androidx.lifecycle.ViewModel
import hu.havasig.betreminder.data.DefaultData
import hu.havasig.betreminder.data.UserRepository

class AndroidUserViewModel(private val repository: UserRepository) : ViewModel() {

    fun sayHello() : String{
        val name = DefaultData.DEFAULT_USER.name
        val foundUser = repository.getUser(name)
        return foundUser?.let { "Hello '$it' from $this" } ?: "User '$name' not found!"
    }
}