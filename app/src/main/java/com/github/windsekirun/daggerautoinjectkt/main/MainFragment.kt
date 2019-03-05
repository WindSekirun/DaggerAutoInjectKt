package com.github.windsekirun.daggerautoinjectkt.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.windsekirun.daggerautoinjectkt.InjectFragment
import com.github.windsekirun.daggerautoinjectkt.R

import javax.inject.Inject

@InjectFragment
class MainFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println(sharedPreferences.all)
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
