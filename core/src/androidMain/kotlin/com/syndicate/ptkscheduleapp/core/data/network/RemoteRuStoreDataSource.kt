package com.syndicate.ptkscheduleapp.core.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.data.dto.AppVersionsResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.TokenBodyDTO
import com.syndicate.ptkscheduleapp.core.data.dto.TokenResponseDTO

internal interface RemoteRuStoreDataSource {
    suspend fun getTempToken(tokenBody: TokenBodyDTO): ApiResponse<TokenResponseDTO>
    suspend fun getAppVersions(token: String, packageName: String): ApiResponse<AppVersionsResponseDTO>
}