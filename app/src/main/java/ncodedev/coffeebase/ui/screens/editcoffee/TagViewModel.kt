package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import ncodedev.coffeebase.model.Tag

class TagViewModel: ViewModel() {
    var tagName = mutableStateOf("")
    var isTagNameValid = mutableStateOf(true)
    var color = mutableStateOf(Color.White)
    val tag =  mutableStateOf<Tag?>(null)

    fun validateAndSetTagName(tagName: String) {
        this.tagName.value = tagName
        isTagNameValid.value = tagName.isNotBlank()
    }

    fun createTag() {
        val newTag = Tag(
            id = null,
            name = tagName.value,
            color = color.value.toArgb().toString(),
            userId = ""
        )
        tag.value = newTag
        tagName.value = ""
        color.value = Color.White
    }
}