import kotlinx.browser.sessionStorage
import kotlinx.browser.window

object PreviousPageRedirection {

    private const val KEY = "previousHash"

    fun redirectToPreviousUri() {
        val hash = sessionStorage.getItem(KEY)
        window.location.hash = hash ?: ""
        window.location.reload()
    }

    fun saveCurrentUri() {
        sessionStorage.setItem(KEY, window.location.hash)
    }
}
