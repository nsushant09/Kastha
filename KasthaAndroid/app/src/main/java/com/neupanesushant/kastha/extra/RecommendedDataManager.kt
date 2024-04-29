package com.neupanesushant.kastha.extra

object RecommendedDataManager {

    var recommendedCategories: MutableList<Int> = getSavedRecommendedCategories()
    private fun getSavedRecommendedCategories() =
        Preferences.getRecommendedCategories().toMutableList()

    fun saveCategory(id: Int) {
        if (recommendedCategories.contains(id)) return
        recommendedCategories.removeAt(0)
        recommendedCategories.add(id)
    }
}