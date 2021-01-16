package security

import kotlinx.browser.localStorage
import org.itmodreamteam.myrest.shared.AccessTokenProvider

class LocalStorageAccessTokenProvider : AccessTokenProvider {

    override val accessToken: String?
        get() = localStorage.getItem(KEY)

    fun setAccessToken(token: String) = localStorage.setItem(KEY, token)

    companion object {
        private const val KEY = "accessToken"
    }
}
