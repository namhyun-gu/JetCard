package com.github.namhyungu.jetcard.ui.home

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.github.namhyungu.jetcard.R
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.Resource
import com.github.namhyungu.jetcard.ui.component.JetCardTopAppBar
import com.github.namhyungu.jetcard.ui.component.ResourceContent
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme

typealias OnCategoryClick = (CardCategory) -> Unit

typealias OnCategoryPinnedChange = (CardCategory) -> Unit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNewCategoryClick: () -> Unit,
    onCategoryClick: OnCategoryClick,
    onCategoryPinnedChange: OnCategoryPinnedChange
) {
    val categories by viewModel.cardCategories.observeAsState()

    Scaffold(
        topBar = { JetCardTopAppBar(title = stringResource(id = R.string.app_name)) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNewCategoryClick) {
                Icon(Icons.Rounded.Add)
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        LoadingContent(
            empty = false,
            emptyContent = {
                FullScreenLoading()
            },
            loading = false,
            onRefresh = {}
        ) {
            HomeScreenContent(
                modifier = modifier,
                categories = categories!!,
                onCategoryClick = onCategoryClick,
                onCategoryPinnedChange = onCategoryPinnedChange
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        content()
    }
}

@Composable
private fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    categories: Resource<List<CardCategory>>,
    onCategoryClick: OnCategoryClick,
    onCategoryPinnedChange: OnCategoryPinnedChange
) {
    ResourceContent(
        resource = categories,
        onEmpty = { Box(modifier.fillMaxSize()) { /* Empty Content */ } },
        onError = { Text(text = "Failed load content") }
    ) { data ->
        val (pinned, unpinned) = data.partition { it.isPinned }

        Column(modifier = modifier) {
            if (pinned.isNotEmpty()) {
                CategoryListHeader(title = "Pinned")
                CategoryList(
                    modifier = modifier.padding(horizontal = 12.dp),
                    categories = pinned,
                    onCategoryClick = onCategoryClick,
                    onCategoryPinnedChange = onCategoryPinnedChange
                )
            }
            if (unpinned.isNotEmpty()) {
                CategoryListHeader(title = "Unpinned")
                CategoryList(
                    modifier = modifier.padding(horizontal = 12.dp),
                    categories = unpinned,
                    onCategoryClick = onCategoryClick,
                    onCategoryPinnedChange = onCategoryPinnedChange
                )
            }
        }
    }
}

@Composable
private fun CategoryListHeader(
    modifier: Modifier = Modifier,
    title: String,
) {
    val typography = MaterialTheme.typography

    Text(
        title,
        style = typography.subtitle1,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding()
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryListHeaderPreview() {
    JetCardTheme {
        CategoryListHeader(title = "Header")
    }
}

@Composable
private fun CategoryList(
    modifier: Modifier = Modifier,
    categories: List<CardCategory>,
    onCategoryClick: OnCategoryClick,
    onCategoryPinnedChange: OnCategoryPinnedChange
) {
    ScrollableColumn(modifier = modifier) {
        categories.forEach { category ->
            CategoryCard(
                category,
                onClick = { onCategoryClick(category) },
                onPinClick = { onCategoryPinnedChange(category) }
            )
        }
    }
}

@Preview
@Composable
fun CategoryListPreview() {
    val fakeCategories = IntRange(1, 3)
        .map { i ->
            CardCategory(
                name = "CategoryName $i",
                description = "CategoryDescription $i",
                isPinned = i % 2 == 0
            )
        }

    JetCardTheme {
        CategoryList(
            categories = fakeCategories,
            onCategoryClick = {},
            onCategoryPinnedChange = {}
        )
    }
}

@Composable
private fun CategoryCard(
    category: CardCategory,
    onClick: () -> Unit,
    onPinClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Assignment,
                modifier = Modifier.padding(start = 16.dp)
            )
            CategoryCardContent(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                name = category.name,
                description = category.description
            )
            PinButton(isPined = category.isPinned, onClick = onPinClick)
        }
    }
}

@Composable
private fun CategoryCardContent(
    modifier: Modifier,
    name: String,
    description: String
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colors

    Column(
        modifier = modifier
    ) {
        Text(
            name,
            style = typography.h6
        )
        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                style = typography.subtitle2,
                color = colors.onSurface.copy(
                    alpha = 0.56f
                )
            )
        }
    }
}

@Composable
private fun PinButton(
    isPined: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        modifier = modifier,
        checked = isPined,
        onCheckedChange = { onClick() }
    ) {
        if (isPined) {
            Icon(Icons.Rounded.PushPin)
        } else {
            Icon(Icons.Outlined.PushPin)
        }
    }
}

@Preview(
    group = "CategoryCardPreview",
    name = "Default"
)
@Composable
fun CategoryCardDefaultPreview() {
    JetCardTheme {
        CategoryCard(
            category = CardCategory(
                name = "CategoryName",
                description = "CategoryDescription",
                isPinned = false
            ),
            onClick = {},
            onPinClick = {}
        )
    }
}

@Preview(
    group = "CategoryCardPreview",
    name = "NoDescription"
)
@Composable
fun CategoryCardNoDescriptionPreview() {
    JetCardTheme {
        CategoryCard(
            category = CardCategory(
                name = "CategoryName",
                isPinned = false
            ),
            onClick = {},
            onPinClick = {}
        )
    }
}