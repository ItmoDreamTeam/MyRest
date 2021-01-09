package org.itmodreamteam.myrest.android.data

import org.itmodreamteam.myrest.shared.AccessTokenProvider

class AccessTokenHolder : AccessTokenProvider, AccessTokenMutator {
    private var _accessToken: String? = null

    override val accessToken: String?
        get() = _accessToken

    override fun setAccessToken(accessToken: String) {
        _accessToken = accessToken
    }
}