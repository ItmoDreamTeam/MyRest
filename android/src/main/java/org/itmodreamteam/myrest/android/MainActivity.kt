package org.itmodreamteam.myrest.android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
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
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.setPhone, R.id.restaurantListFragment, R.id.reservationListFragment, R.id.settingsFragment))
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        nav.setupWithNavController(navController)
        val menu = nav.menu
        signInRepository.employeeMode.observe(this) { employeeMode ->
            fixMenu(menu, signInRepository.isLoggedIn, employeeMode)
        }
        signInRepository.signedInUser.observe(this) {
            fixMenu(menu, it != null, signInRepository.employeeMode.value?:false)
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

    private fun fixMenu(menu: Menu, loggedIn: Boolean, employeeMode: Boolean) {
        Log.i(javaClass.name, "logged in: $loggedIn, employee: $employeeMode")
        if (!loggedIn) {
            for (item in menu.iterator()) {
                item.isVisible = false
            }
        } else {
            for (item in menu.iterator()) {
                item.isVisible = true
            }
            menu.findItem(R.id.reservationListFragment).isVisible = employeeMode
        }
    }
}
