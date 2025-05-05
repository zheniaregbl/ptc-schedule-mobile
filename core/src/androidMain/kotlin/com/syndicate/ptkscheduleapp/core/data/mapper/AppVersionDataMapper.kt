package com.syndicate.ptkscheduleapp.core.data.mapper

import com.syndicate.ptkscheduleapp.core.data.dto.AppVersionData
import com.syndicate.ptkscheduleapp.core.domain.model.AppVersionDataModel

internal fun AppVersionData.toModel() =
    AppVersionDataModel(versionName, versionCode, status, info)