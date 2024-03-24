package ncodedev.coffeebase.model.enums

import ncodedev.coffeebase.R

enum class Continent(val continentResId: Int, val continentValue: String) {
    CONTINENT(R.string.continent, "Continent"),
    AFRICA(R.string.africa, "Africa"),
    ASIA(R.string.asia, "Asia"),
    SOUTH_AMERICA(R.string.south_america, "South America");

    companion object {
        fun fromContinentValue(value: String): Continent? {
            return Continent.entries.find { it.continentValue == value }
        }
    }
}
