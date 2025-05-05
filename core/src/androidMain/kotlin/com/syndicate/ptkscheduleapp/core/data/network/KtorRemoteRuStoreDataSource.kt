package com.syndicate.ptkscheduleapp.core.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.core.data.dto.AppVersionsResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.TokenBodyDTO
import com.syndicate.ptkscheduleapp.core.data.dto.TokenResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import ptk_schedule_app.core.BuildConfig

internal class KtorRemoteRuStoreDataSource(
    private val httpClient: HttpClient
): RemoteRuStoreDataSource {

    override suspend fun getTempToken(tokenBody: TokenBodyDTO): ApiResponse<TokenResponseDTO> {
        return httpClient.getApiResponse("${BuildConfig.RUSTORE_API_URL}auth") {
            setBody(tokenBody)
        }
    }

    override suspend fun getAppVersions(
        token: String,
        packageName: String
    ): ApiResponse<AppVersionsResponseDTO> {
        return httpClient.getApiResponse("${BuildConfig.RUSTORE_API_URL}v1/application/$packageName/version") {
            header("Public-Token", token)
        }
    }
}