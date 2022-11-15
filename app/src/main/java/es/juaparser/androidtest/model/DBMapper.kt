package es.juaparser.androidtest.model

import es.juaparser.androidtest.database.DBPlayer
import es.juaparser.androidtest.database.DBTeam

class DBMapper {

    companion object {

        fun serviceToDatabase(players: List<Player>): List<DBPlayer> {
            val result = mutableListOf<DBPlayer>()
            for (p in players) {

                val dbPlayer = DBPlayer(
                    firstName = p.firstName,
                    lastName = p.lastName,
                    heightFeet = p.heightFeet,
                    heightInches = p.heightInches,
                    position = p.position,
                    weightPounds = p.weightPounds,
                    id = p.id,
                    team = p.team?.teamId
                )

                result.add(dbPlayer)
            }

            return result
        }

        fun databaseToService(players: List<DBPlayer>, teams: List<DBTeam>): List<Player> {
            val result = mutableListOf<Player>()
            val currentTeams = mutableListOf<Team>()

            for(t in teams) {
                val team = Team(
                    abbreviation = t.abbreviation,
                    city = t.city,
                    conference = t.conference,
                    division = t.division,
                    name = t.name,
                    fullName = t.fullName,
                    teamId = t.teamId
                )

                currentTeams.add(team)
            }

            for (p in players) {

                val player = Player(
                    firstName = p.firstName,
                    lastName = p.lastName,
                    heightFeet = p.heightFeet,
                    heightInches = p.heightInches,
                    position = p.position,
                    weightPounds = p.weightPounds,
                    id = p.id,
                    team = currentTeams.firstOrNull { x -> p.team == x.teamId }
                )

                result.add(player)
            }

            return result
        }

        fun teamServiceToDB(teams: List<Team>): List<DBTeam> {
            val result = mutableListOf<DBTeam>()

            for(t in teams) {
                val dbTeam = DBTeam(
                    abbreviation = t.abbreviation,
                    city = t.city,
                    conference = t.conference,
                    division = t.division,
                    name = t.name,
                    fullName = t.fullName,
                    teamId = t.teamId
                )

                result.add(dbTeam)
            }

            return result
        }
    }
}