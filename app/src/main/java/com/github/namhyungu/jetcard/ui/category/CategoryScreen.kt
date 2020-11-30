package com.github.namhyungu.jetcard.ui.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.ui.tooling.preview.Preview
import com.github.namhyungu.jetcard.R
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.CardContent
import com.github.namhyungu.jetcard.model.CardStatus
import com.github.namhyungu.jetcard.ui.component.JetCardTopAppBar
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme

typealias OnCardClick = (CardContent) -> Unit

data class CardDialogAction(
    val id: String,
    val name: String
)

typealias OnCardDialogAction = (CardDialogAction) -> Unit

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    onNavigateUpClick: () -> Unit,
    onPlayClick: () -> Unit,
    onEditCategory: () -> Unit,
    onAddCard: () -> Unit,
    onEditCard: (CardContent) -> Unit,
    onDeleteCard: (CardContent) -> Unit,
) {
    val categoryWithContents by viewModel.categoryWithContents.observeAsState()

    Scaffold(
        topBar = {
            CategoryToolbar(
                category = categoryWithContents?.category,
                onNavigateUpClick = onNavigateUpClick,
                onEditActionClick = onEditCategory,
                onAddActionClick = onAddCard
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onPlayClick) {
                Icon(Icons.Rounded.PlayArrow)
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        if (categoryWithContents?.contents != null) {
            CategoryScreenContent(
                modifier = modifier,
                cards = categoryWithContents?.contents!!,
                onEditCard = onEditCard,
                onDeleteCard = onDeleteCard
            )
        }
    }
}

@Composable
fun CategoryToolbar(
    modifier: Modifier = Modifier,
    category: CardCategory?,
    onNavigateUpClick: () -> Unit,
    onEditActionClick: () -> Unit,
    onAddActionClick: () -> Unit
) {
    val title = category?.name ?: stringResource(id = R.string.app_name)
    val subtitle = category?.description ?: ""

    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colors

    Column(
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {
        JetCardTopAppBar(
            modifier = modifier,
            title = "",
            navigationIcon = {
                IconButton(onClick = onNavigateUpClick) {
                    Icon(Icons.Rounded.ArrowBack)
                }
            },
            actions = {
                IconButton(
                    icon = { Icon(Icons.Rounded.Edit) },
                    onClick = onEditActionClick
                )
                IconButton(
                    icon = { Icon(Icons.Rounded.Add) },
                    onClick = onAddActionClick
                )
            }
        )
        Column(
            modifier = Modifier
                .padding(
                    start = 88.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 28.dp
                ),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                title,
                fontWeight = FontWeight.Black,
                style = typography.h5
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                subtitle,
                style = typography.subtitle2,
                color = colors.onSurface.copy(
                    alpha = 0.56f
                )
            )
        }
    }
}

@Preview
@Composable
fun CategoryToolbarPreview() {
    JetCardTheme {
        CategoryToolbar(
            category = CardCategory(name = "Category", description = "Description"),
            onNavigateUpClick = {},
            onEditActionClick = {},
            onAddActionClick = {}
        )
    }
}

@Composable
private fun CategoryScreenContent(
    modifier: Modifier = Modifier,
    cards: List<CardContent>,
    onEditCard: (CardContent) -> Unit,
    onDeleteCard: (CardContent) -> Unit,
) {
    var openCardDialog by remember { mutableStateOf(false) }
    var clickedCard by remember { mutableStateOf<CardContent?>(null) }

    Box {
        CardContentList(
            modifier = modifier,
            cards = cards,
            onCardClick = { card ->
                clickedCard = card
                openCardDialog = true
            }
        )
        if (openCardDialog && clickedCard != null) {
            CardDialog(
                card = clickedCard!!,
                onEditCard = onEditCard,
                onDeleteCard = onDeleteCard,
                onDismiss = { openCardDialog = false }
            )
        }
    }
}

@Composable
fun CardDialog(
    card: CardContent,
    onEditCard: (CardContent) -> Unit,
    onDeleteCard: (CardContent) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            CardDialogContent(
                onActionClick = { action ->
                    when (action.id) {
                        "action_edit" -> onEditCard(card)
                        "action_delete" -> onDeleteCard(card)
                    }
                    onDismiss()
                },
                onCancelClick = onDismiss
            )
        },
    )
}

@Composable
fun CardDialogContent(
    onActionClick: OnCardDialogAction,
    onCancelClick: () -> Unit
) {
    val cardContentActions = listOf(
        CardDialogAction(id = "action_edit", "Edit"),
        CardDialogAction(id = "action_delete", "Delete"),
    )

    Surface(
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Text(
                "Card Action",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 24.dp,
                    end = 24.dp,
                    bottom = 0.dp
                )
            )
            CardActionList(
                actions = cardContentActions,
                onActionClick = onActionClick,
                modifier = Modifier.padding(
                    top = 24.dp
                ),
            )
            Spacer(modifier = Modifier.height(28.dp))
            TextButton(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.End),
                onClick = onCancelClick
            ) {
                Text("Cancel")
            }
        }
    }
}

@Preview
@Composable
fun CardDialogContentPreview() {
    JetCardTheme {
        CardDialogContent(
            onActionClick = {},
            onCancelClick = {}
        )
    }
}

@Composable
fun CardActionList(
    modifier: Modifier = Modifier,
    actions: List<CardDialogAction>,
    onActionClick: OnCardDialogAction
) {
    Column(modifier = modifier) {
        actions.forEach { action ->
            Surface(
                modifier = Modifier
                    .clickable(onClick = { onActionClick(action) })
            ) {
                Text(
                    action.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 16.dp
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardContentActionListPreview() {
    val cardContentActions = listOf(
        CardDialogAction(id = "action_edit", "Edit"),
        CardDialogAction(id = "action_delete", "Delete"),
    )

    JetCardTheme {
        CardActionList(actions = cardContentActions, onActionClick = {})
    }
}

@Composable
fun CardContentList(
    modifier: Modifier = Modifier,
    cards: List<CardContent>,
    onCardClick: OnCardClick
) {
    ScrollableColumn(modifier = modifier) {
        cards.forEach { card ->
            CardContentItem(card = card, onClick = { onCardClick(card) })
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardContentListPreview() {
    val fakeCards = IntRange(1, 3)
        .map { i ->
            CardContent(
                categoryId = 0,
                question = "Question $i",
                answer = "Answer $i"
            )
        }

    JetCardTheme {
        CardContentList(cards = fakeCards, onCardClick = {})
    }
}

@Composable
fun CardContentItem(
    card: CardContent,
    onClick: () -> Unit
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colors

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardQuestionText(
                "Q. ${card.question}",
                cardStatus = card.status,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                if (expanded) {
                    Icon(Icons.Rounded.ExpandLess)
                } else {
                    Icon(Icons.Rounded.ExpandMore)
                }
            }
        }
        if (expanded) {
            Text(
                "A. ${card.answer}",
                style = typography.caption,
                color = colors.onSurface.copy(
                    alpha = 0.56f
                ),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
        }
    }
}

@Composable
fun CardQuestionText(
    text: String,
    modifier: Modifier = Modifier,
    cardStatus: CardStatus
) {
    val style = MaterialTheme.typography.body1
    val errorColor = MaterialTheme.colors.error

    when (cardStatus) {
        CardStatus.Incomplete -> Text(
            text,
            modifier = modifier,
            style = style,
            color = errorColor
        )
        CardStatus.Complete -> Text(
            text,
            modifier = modifier,
            style = style,
            textDecoration = TextDecoration.LineThrough
        )
        CardStatus.None -> Text(
            text,
            modifier = modifier,
            style = style,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardContentItemPreview() {
    val fakeCard = CardContent(
        categoryId = 0,
        question = "Question",
        answer = "Answer"
    )

    JetCardTheme {
        CardContentItem(card = fakeCard, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CardQuestionTextPreview() {
    JetCardTheme {
        Column {
            CardQuestionText(
                "None",
                cardStatus = CardStatus.None
            )
            CardQuestionText(
                "Incomplete",
                cardStatus = CardStatus.Incomplete
            )
            CardQuestionText(
                "Complete",
                cardStatus = CardStatus.Complete
            )
        }
    }
}