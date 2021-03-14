package org.itmodreamteam.myrest.android.data

interface AccessTokenMutator {
    fun setAccessToken(accessToken: String)

    fun removeAccessToken()
}