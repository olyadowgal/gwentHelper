package com.dowgalolya.gwenthelper.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.crashlytics.android.Crashlytics
import com.dowgalolya.gwenthelper.R
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(nav_host_fragment).navigateUp()
}
