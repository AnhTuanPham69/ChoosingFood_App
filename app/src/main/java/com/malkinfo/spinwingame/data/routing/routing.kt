package com.malkinfo.spinwingame.data.routing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.malkinfo.spinwingame.R


sealed class Screen(val titleResId: Int) {
    object Home : Screen(R.string.app_name)
    object FoodScreen : Screen(R.string.app_name)
}
object AppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(
        Screen.Home
    )

    private var previousScreen: MutableState<Screen> = mutableStateOf(
        Screen.Home
    )

    fun navigateTo(destination: Screen) {
        previousScreen.value = currentScreen.value
        currentScreen.value = destination
    }

    fun goBack() {
        currentScreen.value = previousScreen.value
    }
}