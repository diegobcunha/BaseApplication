package com.diegocunha.baseapplication.coroutines

import org.koin.dsl.module

val coroutineModule = module {
    factory <DispatchersProvider> { ProductionDispatcherProvider }
}