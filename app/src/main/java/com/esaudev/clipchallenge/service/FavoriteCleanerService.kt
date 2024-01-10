package com.esaudev.clipchallenge.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCleanerService: Service() {

    @Inject
    lateinit var pokemonRepository: PokemonRepository

    private var job: Job? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = GlobalScope.launch(Dispatchers.Default) {
            while (isActive) {
                pokemonRepository.cleanFavorites()
                delay(5000)
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
