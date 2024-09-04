package com.example.yourpoints.domain.annotatorTruco

import android.util.Log
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.TrucoPlayerDomain
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.TrucoPlayerUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetTrucoGameUseCaseTest{

    @RelaxedMockK
    private lateinit var trucoRepository: TrucoRepository

    lateinit var getTrucoGameUseCase: GetTrucoGameUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getTrucoGameUseCase = GetTrucoGameUseCase(trucoRepository)
    }

    @Test
    fun `when database return a throw then get empty trucoUi`() = runBlocking {
        //Given
        val gameDefault = TrucoUi()
        every { trucoRepository.getTrucoGameFromDatabase(any()) } returns null

        //When
        val response = getTrucoGameUseCase(1)

        //Then
        assert(response == gameDefault)
    }

    @Test
    fun `when database return a gameDomain then get gameToUi`() = runBlocking {
        //Given
        val gameDomain = TrucoDomain(
            id = 2,
            dataCreated = "DateX",
            pointLimit = 30,
            player1 = TrucoPlayerUi().toDomain(),
            player2 = TrucoPlayerUi().toDomain(),
            winner = TypePlayer.VACIO,
        )
        every { trucoRepository.getTrucoGameFromDatabase(gameDomain.id) } returns gameDomain

        //When
        val response = getTrucoGameUseCase(gameDomain.id)

        //Then
        assert( gameDomain.toUi() == response)

    }

}