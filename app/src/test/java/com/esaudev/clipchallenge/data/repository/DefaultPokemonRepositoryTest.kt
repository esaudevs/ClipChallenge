package com.esaudev.clipchallenge.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.esaudev.clipchallenge.data.local.model.PokemonNameEntity
import com.esaudev.clipchallenge.data.local.model.toPokemonNameEntity
import com.esaudev.clipchallenge.data.remote.api.PokemonApi
import com.esaudev.clipchallenge.data.remote.api.SavePokemonApi
import com.esaudev.clipchallenge.data.remote.model.PokemonListDto
import com.esaudev.clipchallenge.data.remote.model.PokemonListItemDto
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.util.Date
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response

class DefaultPokemonRepositoryTest {

    private lateinit var pokemonApi: PokemonApi
    private lateinit var savePokemonApi: SavePokemonApi
    private lateinit var pokemonNameDao: PokemonNameDaoFake
    private lateinit var repository: PokemonRepository

    private val pokemonListSuccessResponse = PokemonListDto(
        count = null,
        next = null,
        previous = null,
        results = listOf(
            PokemonListItemDto(
                name = "bulbasaur",
                url = "/bulbasaur"
            )
        )
    )

    @BeforeEach
    fun setUp() {
        pokemonApi = mockk(relaxed = true)
        savePokemonApi = SavePokemonApi()
        pokemonNameDao = PokemonNameDaoFake()
        repository = DefaultPokemonRepository(
            pokemonApi = pokemonApi,
            pokemonNameDao = pokemonNameDao,
            savePokemonApi = savePokemonApi
        )
    }

    @Test
    fun `on fetch pokemon response error, no pokemon are saved in db`() = runBlocking {
        coEvery { pokemonApi.fetchPokemonNames() } throws mockk<HttpException>() {
            every { printStackTrace() } answers { println("Http exception stack trace") }
        }

        repository.fetchPokemonNames()

        assertThat(pokemonNameDao.observeAll().first().isEmpty()).isTrue()
    }

    @Test
    fun `on fetch pokemon response success, pokemon list should be saved in db`() = runBlocking {
        coEvery { pokemonApi.fetchPokemonNames() } answers {
            Response.success(pokemonListSuccessResponse)
        }

        repository.fetchPokemonNames()

        val actualPokemonList = pokemonNameDao.observeAll().first()

        assertThat(actualPokemonList.isNotEmpty()).isTrue()
        assertThat(actualPokemonList.size).isEqualTo(1)
    }

    @Test
    fun `on save pokemon, pokemon should be saved in db`() = runBlocking {
        val pokemonToSave = PokemonName(
            id = "bulbasaur",
            name = "bulbasaur"
        )

        val pokemonListBeforeSave = pokemonNameDao.observeAll().first()

        assertThat(pokemonListBeforeSave.isEmpty()).isTrue()

        repository.savePokemon(
            pokemonName = pokemonToSave
        )

        val pokemonListAfterSave = pokemonNameDao.observeAll().first()

        assertThat(pokemonListAfterSave.isEmpty()).isFalse()
        assertThat(pokemonListAfterSave.first().pokemonName).isEqualTo(pokemonToSave.name)
    }

    @Test
    fun `on get pokemon names with empty db, fetch pokemon should be called`() = runBlocking {
        coEvery { pokemonApi.fetchPokemonNames() } answers {
            Response.success(pokemonListSuccessResponse)
        }

        repository.getPokemonNames()

        coVerify(exactly = 1) { pokemonApi.fetchPokemonNames() }
    }

    @Test
    fun `on get pokemon names with data in db, fetch pokemon should not be called`() = runBlocking {
        coEvery { pokemonApi.fetchPokemonNames() } answers {
            Response.success(pokemonListSuccessResponse)
        }

        pokemonNameDao.upsert(
            pokemonNameEntity = PokemonNameEntity(
                nameId = "bulbasaur",
                pokemonName = "bulbasaur",
                favoriteTimeStamp = null
            )
        )

        repository.getPokemonNames()

        coVerify(exactly = 0) { pokemonApi.fetchPokemonNames() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `on clean pokemon favorites, only ones saved for more than 5 seconds are updated`() = runTest {
        val pokemonToUpdate = PokemonName(
            id = "bulbasaur",
            name = "bulbasaur"
        )

        val originalPokemonList = listOf(
            pokemonToUpdate,
            PokemonName(
                id = "ivysaur",
                name = "ivysaur"
            )
        )

        pokemonNameDao.upsert(originalPokemonList.map { it.toPokemonNameEntity() })

        val pokemonUpdated = pokemonToUpdate.copy(
            name = "S-Bulbasaur"
        )

        repository.updatePokemon(
            pokemonName = pokemonUpdated.name,
            pokemonId = pokemonUpdated.id,
            timeStamp = Date()
        )

        advanceTimeBy(6000)

        repository.cleanFavorites()

        val pokemonListAfterCleanUp = pokemonNameDao.observeAll().first().map {
            it.pokemonName
        }

        assertThat(pokemonListAfterCleanUp.size).isEqualTo(originalPokemonList.size)
        assertThat(pokemonListAfterCleanUp.contains(pokemonUpdated.name)).isFalse()
    }

    @Test
    fun `on clean pokemon favorites, pokemon favorites for not more than 5 seconds should not be modified`() = runTest {
        val pokemonToUpdate = PokemonName(
            id = "bulbasaur",
            name = "bulbasaur"
        )

        val originalPokemonList = listOf(
            pokemonToUpdate,
            PokemonName(
                id = "ivysaur",
                name = "ivysaur"
            )
        )

        pokemonNameDao.upsert(originalPokemonList.map { it.toPokemonNameEntity() })

        val pokemonUpdated = pokemonToUpdate.copy(
            name = "S-Bulbasaur"
        )

        repository.updatePokemon(
            pokemonName = pokemonUpdated.name,
            pokemonId = pokemonUpdated.id,
            timeStamp = Date()
        )

        repository.cleanFavorites()

        val pokemonListAfterCleanUp = pokemonNameDao.observeAll().first().map {
            it.pokemonName
        }

        assertThat(pokemonListAfterCleanUp.size).isEqualTo(originalPokemonList.size)
        assertThat(pokemonListAfterCleanUp.contains(pokemonUpdated.name)).isTrue()
    }
}