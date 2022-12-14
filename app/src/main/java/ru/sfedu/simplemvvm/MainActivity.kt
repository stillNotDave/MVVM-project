package ru.sfedu.simplemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.sfedu.simplemvvm.model.views.currentcolor.CurrentColorFragment
import ru.sfedu.simplemvvm.views.HasScreenTitle
import ru.sfedu.simplemvvm.views.base.BaseFragment


/**
 * This application is a single-activity app. MainActivity is a container
 * for all screens.
 */
class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<MainViewModel> { AndroidViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // define the initial screen that should be launched when app starts.
            activityViewModel.launchFragment(
                activity = this,
                screen = CurrentColorFragment.Screen(),
                addToBackStack = false
            )
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        // execute navigation actions only when activity is active
        activityViewModel.whenActivityActive.resource = this
    }

    override fun onPause() {
        super.onPause()
        // postpone navigation actions if activity is not active
        activityViewModel.whenActivityActive.resource = null
    }

    // Отображение и работа верхнего Тулбара
    // Вызывается каждый раз когда какой-то экран становится активным
    fun notifyScreenUpdates() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (supportFragmentManager.backStackEntryCount > 0) {
            // more than 1 screen -> show back button in the toolbar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        if (fragment is HasScreenTitle && fragment.getScreenTitle() != null) {
            // fragment has custom screen title -> display it
            supportActionBar?.title = fragment.getScreenTitle()
        } else {
            supportActionBar?.title = getString(R.string.app_name)
        }

        val result = activityViewModel.result.value?.getValue() ?: return
        if (fragment is BaseFragment) {
            // has result that can be delivered to the screen's view-model
            fragment.viewModel.onResult(result)
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            notifyScreenUpdates()
        }
    }

}