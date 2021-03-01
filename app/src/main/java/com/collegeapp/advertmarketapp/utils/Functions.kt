package com.collegeapp.advertmarketapp.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.fragment.app.Fragment
import com.collegeapp.advertmarketapp.App


val Fragment.app: App
    get() {
        return (requireContext().applicationContext as App)
    }

val View.app: App
    get() {
        return context.applicationContext as App
    }

val View.activity : Activity?
    get() {
        var context: Context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }