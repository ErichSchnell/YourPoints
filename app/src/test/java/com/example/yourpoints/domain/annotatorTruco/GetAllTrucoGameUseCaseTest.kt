package com.example.yourpoints.domain.annotatorTruco

import app.cash.turbine.test
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.TrucoPlayerDomain
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.TrucoPlayerUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import io.mockk.MockK
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.junit.Before
import org.junit.Test


class GetAllTrucoGameUseCaseTest{

    @RelaxedMockK
    private lateinit var trucoRepository: TrucoRepository

    lateinit var getAllTrucoGameUseCase: GetAllTrucoGameUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getAllTrucoGameUseCase = GetAllTrucoGameUseCase(trucoRepository)
    }

    @Test
    fun `when database return null then get flow empty List TrucoUi`(){
        //Given
        val flowAux = flow<List<TrucoUi>> { emptyList<TrucoUi>() }
        val aux = flow<List<TrucoDomain?>?>{}
        coEvery { trucoRepository.getAllGames() } returns aux

        //When
        val response = getAllTrucoGameUseCase()

        //Then
        assert(response == flowAux)
    }

    @Test
    suspend fun `when database return listGameDomain then listGameDomainToUi`(){
        //Given
        val listDomain = listOf(
            TrucoDomain(
                id = 1,
                dataCreated = "Hello",
                pointLimit = 12,
                player1 = TrucoPlayerUi().toDomain(),
                player2 = TrucoPlayerUi().toDomain(),
                winner = TypePlayer.VACIO,
            ),
            TrucoDomain(
                id = 2,
                dataCreated = "Hello2",
                pointLimit = 15,
                player1 = TrucoPlayerUi().toDomain(),
                player2 = TrucoPlayerUi().toDomain(),
                winner = TypePlayer.VACIO,
            )
        )
        val flowAux = flow {
            emit(listDomain)
        }
        every { trucoRepository.getAllGames() } returns flowAux

        //When
        val response = getAllTrucoGameUseCase()

        //Then
        response.test {
            val emittedGame = awaitItem()
            for(i in emittedGame.indices){
                assertEquals(emittedGame[i], listDomain[i].toUi())
            }
            awaitComplete()
        }
    }
    

}