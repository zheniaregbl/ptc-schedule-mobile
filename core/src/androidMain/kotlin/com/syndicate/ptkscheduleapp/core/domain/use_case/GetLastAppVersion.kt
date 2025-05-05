package com.syndicate.ptkscheduleapp.core.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.messageOrNull
import com.syndicate.ptkscheduleapp.core.domain.model.AppVersionDataModel
import com.syndicate.ptkscheduleapp.core.domain.model.TokenBodyModel
import com.syndicate.ptkscheduleapp.core.domain.repository.RuStoreRepository
import com.syndicate.ptkscheduleapp.core.update.UpdateInfo

class GetLastAppVersion(
    private val ruStoreRepository: RuStoreRepository
) {

    suspend operator fun invoke(
        tokenBodyModel: TokenBodyModel,
        currentVersionCode: Int,
        packageName: String
    ): UpdateInfo? {

        return when (val tempTokenResponse = ruStoreRepository.getTempToken(tokenBodyModel)) {

            is ApiResponse.Success<String> -> {

                val token = tempTokenResponse.data

                return when (val appVersionsResponse = ruStoreRepository.getAppVersions(token, packageName)) {

                    is ApiResponse.Success<List<AppVersionDataModel>> -> {

                        val versions = appVersionsResponse.data
                        val lastVersion = versions.find { it.versionCode > currentVersionCode && it.status == "ACTIVE" }

                        if (lastVersion == null) null
                        else UpdateInfo(lastVersion.versionName, lastVersion.info)
                    }

                    else -> null
                }
            }

            else -> {

                println("[main] update error : ${tempTokenResponse.messageOrNull}")

                return null
            }
        }
    }
}