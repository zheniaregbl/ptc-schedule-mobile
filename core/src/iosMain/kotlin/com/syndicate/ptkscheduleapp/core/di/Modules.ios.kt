package com.syndicate.ptkscheduleapp.core.di

import com.syndicate.ptkscheduleapp.core.update.UpdateChecker
import org.koin.dsl.module

val iosCoreModule = module {
    single<UpdateChecker> { UpdateChecker() }
}