package com.syndicate.ptkscheduleapp.core.domain.repository

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.domain.model.AppVersionDataModel
import com.syndicate.ptkscheduleapp.core.domain.model.TokenBodyModel

internal interface RuStoreRepository {
    suspend fun getTempToken(tokenBodyModel: TokenBodyModel): ApiResponse<String>
    suspend fun getAppVersions(
        token: String,
        packageName: String
    ): ApiResponse<List<AppVersionDataModel>>
}