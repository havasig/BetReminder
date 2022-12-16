object Versions {
    const val koin = "3.2.2"
}

object Deps {

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val androidCompose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    }

}
