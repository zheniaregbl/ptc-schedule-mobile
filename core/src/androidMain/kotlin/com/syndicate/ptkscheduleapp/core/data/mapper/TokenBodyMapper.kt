package com.syndicate.ptkscheduleapp.core.data.mapper

import com.syndicate.ptkscheduleapp.core.data.dto.TokenBodyDTO
import com.syndicate.ptkscheduleapp.core.domain.model.TokenBodyModel

internal fun TokenBodyModel.toDTO() =
    TokenBodyDTO(keyId, timestamp, signature)