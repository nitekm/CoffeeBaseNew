package ncodedev.coffeebase.ui.screens.editcoffee.tags

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.data.repository.TagRepository
import ncodedev.coffeebase.model.Tag
import java.io.IOException
import javax.inject.Inject

sealed interface TagUiState {
    data class Success(val coffees: List<Tag>) : TagUiState
    object Error : TagUiState
    object Loading : TagUiState
}

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagRepository: TagRepository
) : ViewModel() {

    companion object {
        private const val TAG = "TagViewModel"
    }

    var tagUiState: TagUiState by mutableStateOf(TagUiState.Loading)
        private set

    var tagName = mutableStateOf("")
    var isTagNameValid = mutableStateOf(true)
    var color = mutableStateOf(Color.White)
    val tag = mutableStateOf<Tag?>(null)
    val tags = mutableStateOf(listOf<Tag>())
    val tagHints = mutableStateOf(listOf<Tag>())
    val showHintDropdown = mutableStateOf(false)
    fun validateAndSetTagName(tagName: String) {
        this.tagName.value = tagName
        isTagNameValid.value = tagName.isNotBlank()
        tagHints.value = tags.value.filter { tag -> tag.name.contains(tagName, ignoreCase = true) }.take(5)
        if (tagHints.value.isNotEmpty()) {
            showHintDropdown.value = true
        }
    }

    fun getTags() {
        viewModelScope.launch {
            tagUiState = TagUiState.Loading
            tagUiState = try {
                val response = tagRepository.getTags()
                tags.value = response
                TagUiState.Success(response)
            } catch (e: IOException) {
                Log.e(TAG, "GetTags resulted in exception $e")
                TagUiState.Error
            }
        }
    }

    fun createTag() {
        val newTag = Tag(
            id = null,
            name = tagName.value,
            color = color.value.toArgb().toString(),
        )
        tag.value = newTag
        tagName.value = ""
        color.value = Color.White
    }
}