package com.github.namhyungu.jetcard.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Composable
fun JetCardTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.h5
            )
        },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = navigationIcon,
        actions = actions,
        elevation = 0.dp
    )
}

@Preview
@Composable
fun JetCardTopAppBarPreview() {
    JetCardTopAppBar(
        title = "JetCard",
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.ArrowBack)
            }
        }
    )
}