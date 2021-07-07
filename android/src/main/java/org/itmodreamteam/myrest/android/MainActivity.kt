package org.itmodreamteam.myrest.android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.iterator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.shared.user.Profile
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    @Inject
    lateinit var signInRepository: SignInRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.setPhone,
                R.id.restaurantListFragment,
                R.id.reservationListFragment,
                R.id.settingsFragment
            )
        )
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        nav.setupWithNavController(navController)
        val menu = nav.menu
        signInRepository.employeeMode.observe(this) { employeeMode ->
            fixMenu(menu, signInRepository.signedInUser.value, employeeMode)
        }
        signInRepository.signedInUser.observe(this) {
            fixMenu(menu, it, signInRepository.employeeMode.value ?: false)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun fixMenu(menu: Menu, profile: Profile?, employeeMode: Boolean) {
        val loggedIn = profile != null
        val onlySettings: Boolean = if (profile != null) {
            if (profile.lastName.isBlank() && profile.firstName.isBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Для пользования сервисом необходимо указать имя",
                    LENGTH_LONG
                ).show()
                true
            } else {
                false
            }
        } else {
            false
        }
        Log.i(
            javaClass.name,
            "logged in: $loggedIn, employee: $employeeMode, onlySettings: $onlySettings"
        )
        if (!loggedIn) {
            for (item in menu.iterator()) {
                item.isVisible = false
            }
        } else {
            if (!onlySettings) {
                menu.findItem(R.id.restaurantListFragment).isVisible = !employeeMode
                menu.findItem(R.id.reservationListFragment).isVisible = !employeeMode
                menu.findItem(R.id.settingsFragment).isVisible = true
                menu.findItem(R.id.employeeListFragment).isVisible = employeeMode
            } else {
                for (item in menu.iterator()) {
                    item.isVisible = false
                }
                menu.findItem(R.id.settingsFragment).isVisible = true
            }
        }
    }
}
