package security

import kotlinx.browser.localStorage
import org.itmodreamteam.myrest.shared.AccessTokenProvider

class LocalStorageAccessTokenProvider : AccessTokenProvider, AccessTokenHolder {

    override val accessToken: String?
        get() = value

    override var value: String?
        get() = localStorage.getItem(KEY)
        set(value) {
            if (value == null) {
                localStorage.removeItem(KEY)
            } else {
                localStorage.setItem(KEY, value)
            }
        }

    companion object {
        private const val KEY = "accessToken"
    }
}
