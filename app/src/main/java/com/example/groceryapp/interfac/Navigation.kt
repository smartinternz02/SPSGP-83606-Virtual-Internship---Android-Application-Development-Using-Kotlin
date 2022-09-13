package com.example.groceryapp.interfac

import androidx.fragment.app.Fragment

interface Navigation {
    fun nav(fragment: Fragment, addtoStack : Boolean)
}