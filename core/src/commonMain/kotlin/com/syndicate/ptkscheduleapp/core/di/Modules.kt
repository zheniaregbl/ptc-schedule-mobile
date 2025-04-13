package com.syndicate.ptkscheduleapp.core.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ptk_schedule_app.core.BuildConfig
import com.syndicate.ptkscheduleapp.core.data.repository.DefaultPreferencesRepository
import com.syndicate.ptkscheduleapp.core.data.repository.DefaultScheduleRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.data.network.KtorRemoteScheduleDataSource
import com.syndicate.ptkscheduleapp.core.data.network.RemoteScheduleDataSource
import com.syndicate.ptkscheduleapp.core.presentation.AppViewModel
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.SIMPLE
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.EMPTY
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 30000
                requestTimeoutMillis = 30000
            }
            defaultRequest {
                host = BuildConfig.BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
                    path("api/v2/")
                }
                contentType(ContentType.Application.Json)
            }
        }
    }
}

val coreModule = module {
    singleOf(::KtorRemoteScheduleDataSource).bind<RemoteScheduleDataSource>()
    singleOf(::DefaultScheduleRepository).bind<ScheduleRepository>()
    singleOf(::DefaultPreferencesRepository).bind<PreferencesRepository>()
    single { AppViewModel(get()) }
}