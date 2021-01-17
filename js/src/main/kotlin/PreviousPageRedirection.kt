import kotlinx.browser.sessionStorage
import kotlinx.browser.window

object PreviousPageRedirection {

    private const val KEY = "previousUri"

    fun redirectToPreviousUri() {
        sessionStorage.getItem(KEY)
    }

    fun saveCurrentUri() {
        sessionStorage.setItem(KEY, window.location.pathname)
    }
}
