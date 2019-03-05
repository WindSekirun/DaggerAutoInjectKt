package com.github.windsekirun.daggerautoinjectkt.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.windsekirun.daggerautoinjectkt.InjectActivity
import com.github.windsekirun.daggerautoinjectkt.R
import com.github.windsekirun.daggerautoinjectkt.base.BaseActivity
import com.github.windsekirun.daggerautoinjectkt.viewmodel.MainViewModel

import javax.inject.Inject

@InjectActivity
class MainActivity : BaseActivity() {

    @Inject lateinit var sharedPreferences: SharedPreferences
//    @Inject lateinit var viewModelProvideFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val viewModel = ViewModelProviders.of(this, viewModelProvideFactory).get(MainViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        println(sharedPreferences.all)
    }

}
