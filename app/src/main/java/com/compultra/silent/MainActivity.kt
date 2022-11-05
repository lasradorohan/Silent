package com.compultra.silent

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.compultra.silent.ui.add.AddFragment
import com.compultra.silent.ui.chats.ChatsFragment
import com.compultra.silent.databinding.ActivityMainBinding
import com.compultra.silent.ui.messages.MessagesFragment
import com.compultra.silent.ui.requests.RequestsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var selectedIdx = 0

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController


        setupBottomBar()



    }

    fun setupBottomBar() {
        binding.textChats.setOnClickListener {
            if (selectedIdx != 0) {
                selectedIdx = 0
                navController.popBackStack(R.id.chats_fragment, false)
            }

        }
        binding.textRequests.setOnClickListener {
            if (selectedIdx != 1) {
                selectedIdx = 1
                navController.navigate(R.id.requests_fragment)
            }
        }

        binding.textAdd.setOnClickListener {
            navController.navigate(R.id.add_fragment)
        }

        val animationDuration = resources.getInteger(R.integer.anim_duration).toLong()

        val bottomNavText1Animator = ObjectAnimator.ofArgb(
            binding.textChats,
            "textColor",
            ContextCompat.getColor(applicationContext, R.color.dark_primary_text),
            ContextCompat.getColor(applicationContext, R.color.light_primary_text)
        ).apply { duration = animationDuration }

        val bottomNavText2Animator = ObjectAnimator.ofArgb(
            binding.textRequests,
            "textColor",
            ContextCompat.getColor(applicationContext, R.color.light_primary_text),
            ContextCompat.getColor(applicationContext, R.color.dark_primary_text),
        ).apply { duration = animationDuration }

        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fragmentManager: FragmentManager,
                fragment: Fragment,
                view: View,
                savedInstanceState: Bundle?
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    TransitionSet().apply {
                        ordering = TransitionSet.ORDERING_TOGETHER
                        addTransition(Slide(Gravity.BOTTOM).addTarget(binding.bottomnav))
                        addTransition(Slide(Gravity.TOP).addTarget(binding.headerBar))
                    }

                )
                when (fragment) {
                    is AddFragment -> {
                        binding.bottomnav.visibility = View.GONE
                        binding.headerBar.visibility = View.VISIBLE
                    }
                    is RequestsFragment -> {
                        binding.bottomnav.visibility = View.VISIBLE
                        binding.headerBar.visibility = View.VISIBLE
                        selectedIdx = 1
                        binding.bottomnavMotionlayout.transitionToEnd()
                        bottomNavText1Animator.start()
                        bottomNavText2Animator.start()
                    }
                    is ChatsFragment -> {
                        binding.bottomnav.visibility = View.VISIBLE
                        binding.headerBar.visibility = View.VISIBLE
                        selectedIdx = 0
                        binding.bottomnavMotionlayout.transitionToStart()
                        bottomNavText1Animator.reverse()
                        bottomNavText2Animator.reverse()
                    }
                    is MessagesFragment -> {
                        binding.bottomnav.visibility = View.GONE
                        binding.headerBar.visibility = View.GONE
                    }
                    else -> {
                        binding.bottomnav.visibility = View.VISIBLE
                        binding.headerBar.visibility = View.VISIBLE

                    }
                }
            }
        }, true)

    }

//    override fun onSupportNavigateUp(): Boolean {
//        Toast.makeText(applicationContext, "what", Toast.LENGTH_SHORT).show()
//        return super.onSupportNavigateUp()
//    }
}