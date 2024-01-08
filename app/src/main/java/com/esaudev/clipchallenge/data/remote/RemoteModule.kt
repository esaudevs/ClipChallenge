package com.esaudev.clipchallenge.data.remote

import com.esaudev.clipchallenge.data.remote.api.PokemonApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    const val POKEMON_BASE_URL = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(
        moshi: Moshi
    ): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun providePokemonApi(
        client: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): PokemonApi {
        return Retrofit.Builder()
            .baseUrl(POKEMON_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create()
    }
}