package com.syndicate.ptkscheduleapp.core.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.syndicate.ptkscheduleapp.core.data.mapper.toDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.core.data.network.RemoteRuStoreDataSource
import com.syndicate.ptkscheduleapp.core.domain.model.AppVersionDataModel
import com.syndicate.ptkscheduleapp.core.domain.model.TokenBodyModel
import com.syndicate.ptkscheduleapp.core.domain.repository.RuStoreRepository

internal class DefaultRuStoreRepository(
    private val remoteRuStoreDataSource: RemoteRuStoreDataSource
): RuStoreRepository {

    override suspend fun getTempToken(tokenBodyModel: TokenBodyModel): ApiResponse<String> {
        return remoteRuStoreDataSource
            .getTempToken(tokenBodyModel.toDTO())
            .suspendMapSuccess { body.jwe }
    }

    override suspend fun getAppVersions(
        token: String,
        packageName: String
    ): ApiResponse<List<AppVersionDataModel>> {
        return remoteRuStoreDataSource
            .getAppVersions(token, packageName)
            .suspendMapSuccess { body.content.map { it.toModel() } }
    }
}