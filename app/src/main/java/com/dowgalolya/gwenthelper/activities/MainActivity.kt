package com.dowgalolya.gwenthelper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dowgalolya.gwenthelper.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //NavHostFragment needs to be updated with a new nav_graph when you have more than 1 graphs
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //This will make our navController accessable from any fragment where we have a reference to mainActivity
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(nav_host_fragment).navigateUp()
}
