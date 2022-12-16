package hu.havasig.betreminder.di

import hu.havasig.betreminder.data.UserRepository
import hu.havasig.betreminder.data.UserRepositoryImpl
import hu.havasig.betreminder.presenter.KMPUserPresenter
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule() = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    factoryOf(::KMPUserPresenter)
}