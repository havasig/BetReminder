package hu.havasig.betreminder.android.di

import hu.havasig.betreminder.android.AndroidUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::AndroidUserViewModel)
}