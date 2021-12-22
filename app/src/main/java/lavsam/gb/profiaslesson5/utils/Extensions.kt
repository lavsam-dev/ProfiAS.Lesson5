package lavsam.gb.profiaslesson5.utils

import android.view.View

fun String.Companion.getEmptyString(): String = ""

fun View.makeVisible(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.makeGone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}