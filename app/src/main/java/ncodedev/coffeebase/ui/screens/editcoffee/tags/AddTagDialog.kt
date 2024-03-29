package ncodedev.coffeebase.ui.screens.editcoffee.tags

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTagDialog(showAddTagDialog: MutableState<Boolean>, editCoffeeViewModel: EditCoffeeViewModel) {

    val tagViewModel: TagViewModel = hiltViewModel()
    val colorPickerController = rememberColorPickerController()

    if (showAddTagDialog.value) {
        tagViewModel.getTags()
        AlertDialog(
            onDismissRequest = { showAddTagDialog.value = false },
            confirmButton = {
                Button(
                    onClick = { createTagAndCloseDialog(showAddTagDialog, tagViewModel, editCoffeeViewModel) },
                    enabled = tagViewModel.isTagNameValid.value
                ) {
                    Text(text = stringResource(R.string.add_tag))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.add_tag),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ExposedDropdownMenuBox(
                        expanded = tagViewModel.showHintDropdown.value,
                        onExpandedChange = {
                            tagViewModel.showHintDropdown.value = !tagViewModel.showHintDropdown.value
                        }
                    ) {
                        Column {
                            OutlinedTextField(
                                modifier = Modifier.menuAnchor(),
                                label = { Text(text = stringResource(R.string.tag_name)) },
                                value = tagViewModel.tagName.value,
                                onValueChange = tagViewModel::setTagNameAndUpdateTagHints,
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = tagViewModel.color.value,
                                    unfocusedContainerColor = tagViewModel.color.value
                                ),
                                isError = !tagViewModel.isTagNameValid.value,
                            )
                            if (!tagViewModel.isTagNameValid.value) {
                                Text(
                                    text = stringResource(R.string.constraint_tag_name_not_empty),
                                    color = Color.Red,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                        }
                        ExposedDropdownMenu(
                            expanded = tagViewModel.showHintDropdown.value,
                            onDismissRequest = { tagViewModel.showHintDropdown.value = false }) {
                            tagViewModel.tagHints.value.forEach { tagHint ->
                                DropdownMenuItem(text = {
                                    Text(
                                        text = tagHint.name,
                                        color = Color(Integer.parseInt(tagHint.color))
                                    )
                                },
                                    onClick = {
                                        tagViewModel.tagName.value = tagHint.name
                                        tagViewModel.color.value = Color(Integer.parseInt(tagHint.color))
                                        tagViewModel.showHintDropdown.value = false
                                    })
                            }
                        }
                    }
                    HsvColorPicker(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 20.dp),
                        controller = colorPickerController,
                        onColorChanged = { colorEnvelope -> tagViewModel.color.value = colorEnvelope.color }
                    )
                }
            }
        )
    }

}

fun createTagAndCloseDialog(
    showAddTagDialog: MutableState<Boolean>,
    tagViewModel: TagViewModel,
    editCoffeeViewModel: EditCoffeeViewModel
) {
    tagViewModel.createTag()
    if (tagViewModel.isTagNameValid.value) {
        tagViewModel.tag.value?.let { editCoffeeViewModel.tags.add(it) }
        showAddTagDialog.value = false
    }
}