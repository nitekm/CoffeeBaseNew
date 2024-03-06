package ncodedev.coffeebase.model.enums

import ncodedev.coffeebase.R

enum class RoastProfile(val roastProfileResId: Int, val roastProfileValue: String) {
    ROAST_PROFILE(R.string.roast_profile, "Roast Profile"),
    LIGHT(R.string.light, "Light"),
    DARK(R.string.dark, "Dark"),
    OMNIROAST(R.string.omniroast, "Omniroast")
}