package com.duyguyilmaz.smartphotocase.views

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.duyguyilmaz.smartphotocase.model.Photo
import com.duyguyilmaz.smartphotocase.model.PhotoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortList(photoList: PhotoViewModel) {
    val options = listOf("Name", "Date")
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    var listSorted = remember { mutableStateListOf<Photo>() }
    listSorted.clear()
    listSorted.addAll(photoList.photoItems)

   ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            singleLine = true,
            label = { Text("Sort") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = {
                    Text(
                    text = selectionOption)},
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        photoList.photoItems.clear()
                        if(selectedOptionText == options[0] )
                            photoList.photoItems.addAll(listSorted.sortedByDescending { it.name.lowercase() })
                        else
                            photoList.photoItems.addAll(listSorted.sortedBy { it.date.lowercase() })
                    })
            }
        }
    }
}