package com.syndicate.ptkscheduleapp.core.update

import android.annotation.SuppressLint
import android.content.Context
import com.syndicate.ptkscheduleapp.core.domain.model.TokenBodyModel
import com.syndicate.ptkscheduleapp.core.domain.use_case.GetLastAppVersion
import ptk_schedule_app.core.BuildConfig
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.Signature
import java.security.SignatureException
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date

actual class UpdateChecker(
    private val context: Context,
    private val getLastAppVersion: GetLastAppVersion
) {

    actual suspend fun checkUpdate(): UpdateInfo? {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return getLastAppVersion(
            generateTokenBody(),
            packageInfo.longVersionCode.toInt(),
            packageInfo.packageName
        )
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class, InvalidKeyException::class, SignatureException::class)
    private fun generateTokenBody(): TokenBodyModel {

        val kf = KeyFactory.getInstance("RSA")
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(BuildConfig.KEY_CONTENT))
        val privateKey = kf.generatePrivate(keySpecPKCS8)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val timestamp = dateFormat.format(Date())

        val messageToSign = BuildConfig.KEY_ID + timestamp

        val signature = Signature.getInstance("SHA512withRSA")
        signature.initSign(privateKey)
        signature.update(messageToSign.toByteArray())

        val signatureBytes = signature.sign()
        val signatureValue = Base64.getEncoder().encodeToString(signatureBytes)

        return TokenBodyModel(
            BuildConfig.KEY_ID,
            timestamp,
            signatureValue
        )
    }
}