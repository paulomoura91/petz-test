package com.paulomoura.petztest.cards.model.repository

import com.paulomoura.petztest.cards.model.dto.CardDTO
import com.paulomoura.petztest.cards.model.dto.SetDTO
import com.paulomoura.petztest.cards.model.network.CardsService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.test.assertFailsWith

class CardsRepositoryTest {

    private val mockWebServer = MockWebServer()
    private lateinit var cardsRepository: CardsRepository

    private val fakeCardDTOs = listOf(
        CardDTO(
            cardId = "cardId",
            name = "name",
            cardSet = "cardSet",
            type = "type",
            faction = "faction",
            rarity = "rarity",
            cost = 1,
            attack = 1,
            health = 1,
            text = "text",
            flavor = "flavor",
            artist = "artist",
            collectible = true,
            elite = true,
            race = "race",
            img = "img",
            imgGold = "imgGold",
            locale = "locale"
        ),
        CardDTO(
            cardId = "cardId2",
            name = "name2",
            cardSet = "cardSet2",
            type = "type2",
            faction = "faction2",
            rarity = "rarity2",
            cost = 2,
            attack = 2,
            health = 2,
            text = "text2",
            flavor = "flavor2",
            artist = "artist2",
            collectible = true,
            elite = true,
            race = "race2",
            img = "img2",
            imgGold = "imgGold2",
            locale = "locale2"
        )
    )

    @Before
    fun `Start the mock Server and initiate the repository`() {
        mockWebServer.start(8080)

        val okHttpClient = OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cardsService = retrofit.create(CardsService::class.java)

        cardsRepository = CardsRepository(cardsService)
    }

    @Test
    fun `Check whether 'getSets' endpoint headers are correct`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{\"sets\":[\"set1\", \"set2\"]}")
        )

        runBlocking { cardsRepository.getSets() }

        val headers = mockWebServer.takeRequest().headers
        assert(headers.contains(Pair("X-RapidAPI-Key", "1d3a0da9acmshd9a786bba2346b8p1a8f8ejsn7af0c0ba7fe7")))
        assert(headers.contains(Pair("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")))
    }

    @Test
    fun `Test success response of 'getSets' endpoint`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{\"sets\":[\"set1\", \"set2\"]}")
        )

        val getSetsResponse = runBlocking { cardsRepository.getSets() }
        assert(SetDTO(listOf("set1", "set2")) == getSetsResponse)
    }

    @Test
    fun `Test error response of 'getSets' endpoint`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        assertFailsWith<Exception> { runBlocking { cardsRepository.getSets() } }
    }

    @Test
    fun `Check whether 'getCardsInASet' endpoint headers are correct`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getGetCardsInASetBodyResponse())
        )

        runBlocking { cardsRepository.getCardsInASet("set") }

        val headers = mockWebServer.takeRequest().headers
        assert(headers.contains(Pair("X-RapidAPI-Key", "1d3a0da9acmshd9a786bba2346b8p1a8f8ejsn7af0c0ba7fe7")))
        assert(headers.contains(Pair("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")))
    }

    @Test
    fun `Test success response of 'getCardsInASet' endpoint`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getGetCardsInASetBodyResponse())
        )

        val getSetsResponse = runBlocking { cardsRepository.getCardsInASet("set") }
        assert(fakeCardDTOs == getSetsResponse)
    }

    private fun getGetCardsInASetBodyResponse(): String {
        return "[\n" +
                "  {\n" +
                "   \"cardId\": \"cardId\",\n" +
                "   \"name\": \"name\",\n" +
                "   \"cardSet\": \"cardSet\",\n" +
                "   \"type\": \"type\",\n" +
                "   \"faction\": \"faction\",\n" +
                "   \"rarity\": \"rarity\",\n" +
                "   \"cost\": 1,\n" +
                "   \"attack\": 1,\n" +
                "   \"health\": 1,\n" +
                "   \"text\": \"text\",\n" +
                "   \"flavor\": \"flavor\",\n" +
                "   \"artist\": \"artist\",\n" +
                "   \"collectible\": true,\n" +
                "   \"elite\": true,\n" +
                "   \"race\": \"race\",\n" +
                "   \"img\": \"img\",\n" +
                "   \"imgGold\": \"imgGold\",\n" +
                "   \"locale\": \"locale\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"cardId\": \"cardId2\",\n" +
                "   \"name\": \"name2\",\n" +
                "   \"cardSet\": \"cardSet2\",\n" +
                "   \"type\": \"type2\",\n" +
                "   \"faction\": \"faction2\",\n" +
                "   \"rarity\": \"rarity2\",\n" +
                "   \"cost\": 2,\n" +
                "   \"attack\": 2,\n" +
                "   \"health\": 2,\n" +
                "   \"text\": \"text2\",\n" +
                "   \"flavor\": \"flavor2\",\n" +
                "   \"artist\": \"artist2\",\n" +
                "   \"collectible\": true,\n" +
                "   \"elite\": true,\n" +
                "   \"race\": \"race2\",\n" +
                "   \"img\": \"img2\",\n" +
                "   \"imgGold\": \"imgGold2\",\n" +
                "   \"locale\": \"locale2\"\n" +
                "  }\n" +
                "]"
    }

    @Test
    fun `Test error response of 'getCardsInASet' endpoint`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        assertFailsWith<Exception> { runBlocking { cardsRepository.getCardsInASet("set") } }
    }

    @After
    fun `Shut the mock server down`() = mockWebServer.shutdown()
}