package ncodedev.coffeebase.model.enums

import ncodedev.coffeebase.R

enum class SortOptions(val iconRotateValue: Float, val nameResId: Int, val sortProperty: String, val sortDirection: String, val contentDescriptionResId: Int) {
    NAME_ASC(90f, R.string.name, "name", "ASC", R.string.sort_name_asc),
    NAME_DESC(270f, R.string.name, "name", "DESC", R.string.sort_name_desc),
    RATING_ASC(90f, R.string.rating, "rating", "ASC", R.string.sort_rating_asc),
    RATING_DESC(270f, R.string.rating, "rating", "DESC", R.string.sort_rating_desc),
    SCA_SCORE_ASC(90f, R.string.sca_score, "scaRating", "ASC", R.string.sort_sca_score_asc),
    SCA_SCORE_DESC(270f, R.string.sca_score, "scaRating", "DESC", R.string.sort_sca_score_desc)
}