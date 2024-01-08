package com.esaudev.clipchallenge.ext

import java.util.Locale

fun String.capitalizeByLocale(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}