package com.syndicate.ptkscheduleapp.feature.schedule

import com.syndicate.ptkscheduleapp.core.di.coreModule
import com.syndicate.ptkscheduleapp.core.di.networkModule
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetReplacementTest : KoinTest {

    private val getReplacementCase: TestGetReplacementCase by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                listOf(
                    coreModule,
                    networkModule,
                    module { singleOf(::TestGetReplacementCase) }
                )
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test GetReplacementCase with real API returns non-empty listReplacement for teacher`() = runBlocking {

        val teacherName = "Цымбалюк Л. Н."
        val userIdentifier = UserIdentifier.Teacher(teacherName)

        val result = getReplacementCase(userIdentifier)

        assertTrue("Result should be success") { result is CaseResult.Success }

        val replacementList = (result as CaseResult.Success).data

        assertTrue("Replacement list should not be empty") { replacementList.isNotEmpty() }

        replacementList.forEach { replacementItem ->

            assertTrue(
                "listReplacement for date ${replacementItem.date} should not be empty"
            ) { replacementItem.listReplacement.isNotEmpty() }

            replacementItem.listReplacement.forEach { pairList ->
                assertTrue(
                    "Each pair list for date ${replacementItem.date} should not be empty"
                ) { pairList.isNotEmpty() }
            }
        }
    }
}