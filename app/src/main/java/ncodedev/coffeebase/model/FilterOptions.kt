package ncodedev.coffeebase.model

import ncodedev.coffeebase.R

enum class FilterOptions(val sectionTitleResId: Int, val displayNameResId: Int, val filterKey: String, val filterValue: String) {
    FAVOURITE_YES(R.string.favorite, R.string.yes, "favourite", "yes"),
    FAVOURITE_NO(R.string.favorite, R.string.no, "favourite", "no"),
    CONTINENT_AFRICA(R.string.continent, R.string.africa, "continent", "africa"),
    CONTINENT_ASIA(R.string.continent, R.string.asia, "continent", "asia"),
    CONTINENT_SOUTH_AMERICA(R.string.continent, R.string.south_america, "continent", "southAmerica"),
    ROAST_PROFILE_LIGHT(R.string.roast_profile, R.string.light, "roastProfile", "light"),
    ROAST_PROFILE_DARK(R.string.roast_profile, R.string.dark, "roastProfile", "dark"),
    ROAST_PROFILE_OMNIROAST(R.string.roast_profile, R.string.omniroast, "roastProfile", "omniroast")
}