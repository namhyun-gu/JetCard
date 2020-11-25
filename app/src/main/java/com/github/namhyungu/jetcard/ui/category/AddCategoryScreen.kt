package com.github.namhyungu.jetcard.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.ui.component.ErrorText
import com.github.namhyungu.jetcard.ui.component.JetCardTopAppBar
import com.github.namhyungu.jetcard.ui.component.TextFieldWithError
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme

typealias OnCategoryAdd = (CardCategory) -> Unit

typealias OnFormComplete = (CardCategory) -> Unit

@Composable
fun AddCategoryScreen(
    onNavigateUpClick: () -> Unit,
    onCategoryAdd: OnCategoryAdd
) {
    Scaffold(
        topBar = {
            JetCardTopAppBar(
                title = "Add Category",
                navigationIcon = {
                    IconButton(onClick = onNavigateUpClick) {
                        Icon(Icons.Rounded.Close)
                    }
                },
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        AddCategoryScreenContent(
            modifier = modifier,
            onCategoryAdd = onCategoryAdd
        )
    }
}

@Composable
private fun AddCategoryScreenContent(
    modifier: Modifier = Modifier,
    onCategoryAdd: OnCategoryAdd
) {
    val (name, setName) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    AddCategoryForm(
        modifier = modifier,
        name = name,
        description = description,
        onNameChange = setName,
        onDescriptionChange = setDescription,
        onFormComplete = onCategoryAdd
    )
}

@Composable
private fun AddCategoryForm(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onFormComplete: OnFormComplete
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        val isNameError = name.isBlank()

        TextFieldWithError(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            isErrorValue = isNameError,
            errorLabel = { ErrorText("Error: Name is empty") },
            errorIcon = { Icon(Icons.Rounded.Error) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                if (!isNameError) {
                    onFormComplete(CardCategory(name = name, description = description))
                }
            }
        ) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCategoryFormPreview() {
    JetCardTheme {
        AddCategoryForm(
            name = "Name",
            description = "Description",
            onNameChange = {},
            onDescriptionChange = {},
            onFormComplete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddCategoryFormHasErrorPreview() {
    JetCardTheme {
        AddCategoryForm(
            name = "",
            description = "",
            onNameChange = {},
            onDescriptionChange = {},
            onFormComplete = {}
        )
    }
}