package com.github.namhyungu.jetcard.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme

@Composable
fun TextFieldWithError(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isErrorValue: Boolean = false,
    errorLabel: @Composable (() -> Unit)? = null,
    errorIcon: @Composable (() -> Unit)? = null
) {
    val trailing = if (isErrorValue && errorIcon != null) {
        errorIcon
    } else {
        trailingIcon
    }

    Column(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isErrorValue = isErrorValue,
            label = label,
            trailingIcon = trailing
        )
        if (isErrorValue && errorLabel != null) {
            Box(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 12.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
            ) {
                errorLabel()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithErrorPreview() {
    JetCardTheme {
        TextFieldWithError(
            value = "",
            onValueChange = { },
            isErrorValue = true,
            label = { Text("Name") },
            errorLabel = {
                ErrorText("Error: Name is empty")
            },
            errorIcon = { Icon(Icons.Rounded.Error) },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ErrorText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = modifier
    )
}