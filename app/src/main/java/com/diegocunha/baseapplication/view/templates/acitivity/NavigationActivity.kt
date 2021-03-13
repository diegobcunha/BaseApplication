package com.diegocunha.baseapplication.view.templates.acitivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.diegocunha.baseapplication.R
import com.diegocunha.baseapplication.view.extensions.find
import com.diegocunha.baseapplication.view.extensions.getIntOrThrow

/**
 * A base activity used when you have to use Navigation Component
 */
open class NavigationActivity : BaseActivity(), AppBarConfiguration.OnNavigateUpListener {

    protected lateinit var navigationController: NavController
    protected open val navigationGraphId: Int by lazy { intent.getIntOrThrow(GRAPH_ID_KEY) }
    protected open val showArrowBack = true

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_navigation)
        supportActionBar?.setDisplayHomeAsUpEnabled(showArrowBack)
        supportFragmentManager.configureNavigationController()
    }

    open fun getInitialArguments(): Bundle? = intent.extras

    @IdRes
    open fun getStartDestination(): Int? = null

    open fun configureAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration.Builder()
            .setFallbackOnNavigateUpListener(this)
            .build()
    }

    private fun FragmentManager.configureNavigationController() {
        find<NavHostFragment>(R.id.nav_host_fragment_container)?.let {
            navigationController = it.navController.apply {
                val graph = navInflater.inflate(navigationGraphId)
                getStartDestination()?.let { startDestination ->
                    graph.startDestination = startDestination
                }

                setGraph(graph, getInitialArguments())
            }

            if (showArrowBack) {
                NavigationUI.setupActionBarWithNavController(
                    this@NavigationActivity,
                    navigationController,
                    configureAppBarConfiguration()
                )
            } else {
                toolbar?.let { toolbar ->
                    NavigationUI.setupWithNavController(toolbar, navigationController)
                }
            }
        }
    }

    companion object {
        private const val GRAPH_ID_KEY = "GRAPH_ID_KEY"

        fun launch(
            from: Context,
            @NavigationRes graphId: Int,
            block: (() -> Bundle)? = null
        ) {
            val bundle = (block?.invoke() ?: Bundle()).apply {
                putInt(GRAPH_ID_KEY, graphId)
            }
            val intent = Intent(from, NavigationActivity::class.java)
            intent.putExtras(bundle)
            from.startActivity(intent)
        }
    }
}