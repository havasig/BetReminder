package hu.havasig.betreminder.di

import hu.havasig.betreminder.data.BetRepository
import hu.havasig.betreminder.data.BetRepositoryImpl
import hu.havasig.betreminder.data.UserRepository
import hu.havasig.betreminder.data.UserRepositoryImpl
import hu.havasig.betreminder.presenter.KMPBetPresenter
import hu.havasig.betreminder.presenter.KMPUserPresenter
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule() = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::BetRepositoryImpl) { bind<BetRepository>() }
    factory { KMPUserPresenter(get()) }
    factory { KMPBetPresenter(get()) }
}
