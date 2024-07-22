package com.ighorosipov.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.ighorosipov.currencyconverter.databinding.ActivityMainBinding
import com.ighorosipov.currencyconverter.utils.base.setTitle
import com.ighorosipov.currencyconverter.utils.di.appComponent

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private var navController: NavController? = null

    /** [destinationListener] слушает изменения мест назначений
     * для того, чтобы изменить текст топ бара, так как используется кастомный топ бар
     */
    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            supportActionBar?.title = ""
            binding.toolbar.setTitle(destination.label, binding.toolbarTextView, arguments)
            supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        inject()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navController?.addOnDestinationChangedListener(destinationListener)
        onCustomToolbarBackPress()
    }

    private fun onCustomToolbarBackPress() {
        binding.toolbar.setNavigationOnClickListener {
            navController?.popBackStack()
        }
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestination = graph.startDestinationId
        return destination.id == startDestination
    }

    fun inject() {
        appComponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}