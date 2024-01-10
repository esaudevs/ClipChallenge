package com.esaudev.clipchallenge.data.repository

import com.esaudev.clipchallenge.ui.util.MutableClock
import kotlin.time.Duration
import kotlin.time.toJavaDuration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy

@OptIn(ExperimentalCoroutinesApi::class)
fun TestScope.advanceTimeBy(duration: Duration, clock: MutableClock) {
    advanceTimeBy(duration)
    clock.advanceTimeBy(duration.toJavaDuration())
}