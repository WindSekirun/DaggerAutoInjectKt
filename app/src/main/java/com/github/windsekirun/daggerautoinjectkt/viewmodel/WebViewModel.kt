package com.github.windsekirun.daggerautoinjectkt.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.github.windsekirun.daggerautoinjectkt.InjectViewModel
import com.github.windsekirun.daggerautoinjectkt.MainApplication
import javax.inject.Inject

/**
 * DaggerAutoInject
 * Class: MainViewModel
 * Created by Pyxis on 2018-02-02.
 *
 *
 * Description:
 */

@InjectViewModel
class WebViewModel @Inject constructor(application: MainApplication) : AndroidViewModel(application)
