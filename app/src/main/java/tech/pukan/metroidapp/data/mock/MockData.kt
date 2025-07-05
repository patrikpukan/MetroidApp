package tech.pukan.metroidapp.data.mock

import tech.pukan.metroidapp.data.model.Departure
import tech.pukan.metroidapp.data.model.Station
import tech.pukan.metroidapp.data.model.TransportLine
import tech.pukan.metroidapp.data.model.TransportType
import tech.pukan.metroidapp.data.model.getMetroLineColor
import java.time.LocalTime

object MockData {
    
    private fun generateDepartures(destination: String, startHour: Int = 5, endHour: Int = 24): List<Departure> {
        val departures = mutableListOf<Departure>()
        for (hour in startHour until endHour) {
            for (minute in listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55)) {
                departures.add(
                    Departure(
                        time = LocalTime.of(hour, minute),
                        destination = destination,
                        platform = if (minute % 2 == 0) "1" else "2"
                    )
                )
            }
        }
        return departures
    }
    
    val metroLines = listOf(
        TransportLine(
            id = "metro_a",
            name = "A",
            type = TransportType.METRO,
            color = getMetroLineColor("A"),
            stations = listOf(
                Station(
                    id = "depo_hostivar",
                    name = "Depo Hostivař",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "skalka",
                    name = "Skalka",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "strasnicka",
                    name = "Strašnická",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "zelivskeho",
                    name = "Želivského",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "flora",
                    name = "Flora",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "jiriho_z_podebrad",
                    name = "Jiřího z Poděbrad",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "namesti_miru",
                    name = "Náměstí Míru",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "muzeum",
                    name = "Muzeum",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "wenceslas_square",
                    name = "Václavské náměstí",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "staromestska",
                    name = "Staroměstská",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "malostranska",
                    name = "Malostranská",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "hradcanska",
                    name = "Hradčanská",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "dejvicka",
                    name = "Dejvická",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "borislavka",
                    name = "Bořislavka",
                    departures = generateDepartures("Nemocnice Motol")
                ),
                Station(
                    id = "nemocnice_motol",
                    name = "Nemocnice Motol",
                    departures = generateDepartures("Depo Hostivař")
                )
            )
        ),
        TransportLine(
            id = "metro_b",
            name = "B",
            type = TransportType.METRO,
            color = getMetroLineColor("B"),
            stations = listOf(
                Station(
                    id = "cerny_most",
                    name = "Černý Most",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "rajska_zahrada",
                    name = "Rajská zahrada",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "hloubetin",
                    name = "Hloubětín",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "kolbenova",
                    name = "Kolbenova",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "vysehrad",
                    name = "Vyšehrad",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "karlovo_namesti",
                    name = "Karlovo náměstí",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "narodni_trida",
                    name = "Národní třída",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "mustek",
                    name = "Můstek",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "namesti_republiky",
                    name = "Náměstí Republiky",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "florenc",
                    name = "Florenc",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "krizikova",
                    name = "Křižíkova",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "invalidovna",
                    name = "Invalidovna",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "palmovka",
                    name = "Palmovka",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "ceskomoaravska",
                    name = "Českomoravská",
                    departures = generateDepartures("Zličín")
                ),
                Station(
                    id = "zlicin",
                    name = "Zličín",
                    departures = generateDepartures("Černý Most")
                )
            )
        ),
        TransportLine(
            id = "metro_c",
            name = "C",
            type = TransportType.METRO,
            color = getMetroLineColor("C"),
            stations = listOf(
                Station(
                    id = "letnany",
                    name = "Letňany",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "prosek",
                    name = "Prosek",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "strizkov",
                    name = "Střížkov",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "ladvi",
                    name = "Ládví",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "kobylisy",
                    name = "Kobylisy",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "nadrazi_holesovice",
                    name = "Nádraží Holešovice",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "vltavska",
                    name = "Vltavská",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "florenc_c",
                    name = "Florenc",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "hlavni_nadrazi",
                    name = "Hlavní nádraží",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "muzeum_c",
                    name = "Muzeum",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "ip_pavlova",
                    name = "I. P. Pavlova",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "vysehrad_c",
                    name = "Vyšehrad",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "prazkeho_povstani",
                    name = "Pražského povstání",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "pankrac",
                    name = "Pankrác",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "budejovicka",
                    name = "Budějovická",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "kacerov",
                    name = "Kačerov",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "roztyly",
                    name = "Roztyly",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "chodov",
                    name = "Chodov",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "opatov",
                    name = "Opatov",
                    departures = generateDepartures("Háje")
                ),
                Station(
                    id = "haje",
                    name = "Háje",
                    departures = generateDepartures("Letňany")
                )
            )
        )
    )
    
    val tramLines = listOf(
        TransportLine(
            id = "tram_22",
            name = "22",
            type = TransportType.TRAM,
            color = getMetroLineColor("Tram"),
            stations = listOf(
                Station(
                    id = "bila_hora",
                    name = "Bílá Hora",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "petriny",
                    name = "Petřiny",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "hradcanska_tram",
                    name = "Hradčanská",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "prazsky_hrad",
                    name = "Pražský hrad",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "malostranske_namesti",
                    name = "Malostranské náměstí",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "narodni_divadlo",
                    name = "Národní divadlo",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "namesti_miru_tram",
                    name = "Náměstí Míru",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "vinohrady",
                    name = "Vinohrady",
                    departures = generateDepartures("Nádraží Hostivař", 6, 23)
                ),
                Station(
                    id = "nadrazi_hostivar",
                    name = "Nádraží Hostivař",
                    departures = generateDepartures("Bílá Hora", 6, 23)
                )
            )
        ),
        TransportLine(
            id = "tram_9",
            name = "9",
            type = TransportType.TRAM,
            color = getMetroLineColor("Tram"),
            stations = listOf(
                Station(
                    id = "spojovaci",
                    name = "Spojovací",
                    departures = generateDepartures("Vypich", 6, 23)
                ),
                Station(
                    id = "palackeho_namesti",
                    name = "Palackého náměstí",
                    departures = generateDepartures("Vypich", 6, 23)
                ),
                Station(
                    id = "narodni_trida_tram",
                    name = "Národní třída",
                    departures = generateDepartures("Vypich", 6, 23)
                ),
                Station(
                    id = "vaclavske_namesti",
                    name = "Václavské náměstí",
                    departures = generateDepartures("Vypich", 6, 23)
                ),
                Station(
                    id = "jindrisska",
                    name = "Jindřišská",
                    departures = generateDepartures("Vypich", 6, 23)
                ),
                Station(
                    id = "masarykovo_nadrazi",
                    name = "Masarykovo nádraží",
                    departures = generateDepartures("Vypich", 6, 23)
                ),
                Station(
                    id = "vypich",
                    name = "Výpich",
                    departures = generateDepartures("Spojovací", 6, 23)
                )
            )
        )
    )
    
    val allLines = metroLines + tramLines
} 