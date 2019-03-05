package com.github.windsekirun.daggerautoinjectkt.main

import android.content.SharedPreferences
import android.os.Bundle
import com.github.windsekirun.daggerautoinjectkt.InjectActivity
import com.github.windsekirun.daggerautoinjectkt.R
import com.github.windsekirun.daggerautoinjectkt.base.BaseActivity

import javax.inject.Inject

@InjectActivity
class MainActivity : BaseActivity() {

    @Inject
    internal var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        println(sharedPreferences!!.all)
    }

}
