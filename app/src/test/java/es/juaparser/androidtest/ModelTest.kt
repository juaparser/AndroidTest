package es.juaparser.androidtest

import es.juaparser.androidtest.database.DBPlayer
import es.juaparser.androidtest.database.DBTeam
import es.juaparser.androidtest.model.DBMapper
import es.juaparser.androidtest.model.Player
import org.junit.Assert.*
import org.junit.Test

/**
 * Model tests
 *
 */
class ModelTest {

    @Test
    fun testPlayers() {
        val clusters = mutableListOf<Player>()

        assertTrue(clusters.isEmpty())
    }

    @Test
    fun testProductId() {
        val dataModel = Player(
            id = 220925,
            firstName = "First name",
            lastName = "last name",
            heightInches = 2,
            weightPounds = 65,
            position = "G",
            team = null,
            heightFeet = 3,
        )

        assertNotNull(dataModel.id)
    }

    @Test
    fun testMapServiceToDb() {

        val dataModel = Player(
            id = 220925,
            firstName = "First name",
            lastName = "last name",
            heightInches = 2,
            weightPounds = 65,
            position = "G",
            team = null,
            heightFeet = 3,
        )

        val list = listOf(dataModel)

        val dbList = DBMapper.serviceToDatabase(list)

        assertEquals(dataModel.id, dbList.first().id)
    }

    @Test
    fun testMapDbToService() {

        val dbPlayer = DBPlayer(
            id = 220925,
            firstName = "First name",
            lastName = "last name",
            heightInches = 2,
            weightPounds = 65,
            position = "G",
            team = null,
            heightFeet = 3,
        )

        val dbTeam = DBTeam(
            abbreviation = "abb",
            city = "Florida",
            conference = "west",
            division = "West",
            fullName = "Team",
            teamId = 3,
            name = "Team Florida"
        )

        val listDbPlayer = listOf(dbPlayer)
        val listDbTeam = listOf(dbTeam)

        val dbList = DBMapper.databaseToService(listDbPlayer, listDbTeam)

        assertEquals(dbList.first().id, dbPlayer.id)
    }
}