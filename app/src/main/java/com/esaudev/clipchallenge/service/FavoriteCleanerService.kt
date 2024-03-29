package com.esaudev.clipchallenge.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

const val CLEANUP_REFRESH_DELAY = 5000L

@AndroidEntryPoint
class FavoriteCleanerService : Service() {

    @Inject
    lateinit var pokemonRepository: PokemonRepository

    private var job: Job? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = GlobalScope.launch(Dispatchers.Default) {
            while (isActive) {
                pokemonRepository.cleanFavorites()
                delay(CLEANUP_REFRESH_DELAY)
            }
        }

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}