package com.dowgalolya.gwenthelper.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<*>.notifyDataChanged() {
    value = value
}