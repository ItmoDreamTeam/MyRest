package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        nav.setupWithNavController(navController)
        val menu = nav.menu
        signInRepository.employeeMode.observe(this) { employeeMode ->
            Log.i(javaClass.name, "Employee mode $employeeMode")
            menu.findItem(R.id.reservationListFragment).isVisible = employeeMode
        }
    }
}
