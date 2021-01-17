package org.itmodreamteam.myrest.android.data

import android.util.Log
import org.itmodreamteam.myrest.shared.AccessTokenProvider

class AccessTokenHolder : AccessTokenProvider, AccessTokenMutator {
    private var _accessToken: String? = null

    override val accessToken: String?
        get() = _accessToken

    override fun setAccessToken(accessToken: String) {
        // TODO debug
        Log.i(javaClass.name, "Setting access-token \"$accessToken\"")
        _accessToken = accessToken
    }
}