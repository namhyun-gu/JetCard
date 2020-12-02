package com.github.namhyungu.jetcard.ui.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.github.namhyungu.jetcard.R
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.CardContent
import com.github.namhyungu.jetcard.ui.component.JetCardTopAppBar
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme
import kotlin.math.roundToInt

private data class CardAction(
    val id: String,
    val icon: VectorAsset,
    val text: String,
    val primary: Boolean = false
)

object CardActions {
    const val actionIncomplete = "action_incomplete"
    const val actionSkip = "action_skip"
    const val actionComplete = "action_complete"
}

typealias OnCardActionClick = (cardId: Int, action: String) -> Unit

@Composable
fun CardScreen(
    category: CardCategory?,
    cards: List<CardContent>?,
    onNavigateUpClick: () -> Unit,
    onCardActionClick: OnCardActionClick,
    onAllCardsDone: () -> Unit
) {
    Scaffold(
        topBar = {
            CardToolbar(
                category = category,
                onNavigateUpClick = onNavigateUpClick
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        if (cards != null) {
            CardScreenContent(
                modifier = modifier,
                cards = cards,
                onCardActionClick = onCardActionClick,
                onAllCardsDone = onAllCardsDone
            )
        }
    }
}

@Composable
fun CardToolbar(
    modifier: Modifier = Modifier,
    category: CardCategory?,
    onNavigateUpClick: () -> Unit,
) {
    val title = category?.name ?: stringResource(id = R.string.app_name)

    JetCardTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            IconButton(onClick = onNavigateUpClick) {
                Icon(Icons.Rounded.Close)
            }
        }
    )
}

@Preview
@Composable
fun CardToolbarPreview() {
    JetCardTheme {
        CardToolbar(
            category = CardCategory(name = "Category"),
            onNavigateUpClick = {}
        )
    }
}

@Composable
fun CardScreenContent(
    modifier: Modifier = Modifier,
    cards: List<CardContent>,
    onCardActionClick: OnCardActionClick,
    onAllCardsDone: () -> Unit
) {
    var cardIndex by savedInstanceState { 0 }
    var visibleAnswer by remember { mutableStateOf(false) }
    val currentCard = cards[cardIndex]

    val cardActions = listOf(
        CardAction(
            CardActions.actionIncomplete,
            Icons.Rounded.Close,
            "I don't know"
        ),
        CardAction(
            CardActions.actionSkip,
            Icons.Rounded.SkipNext,
            "Skip",
        ),
        CardAction(
            CardActions.actionComplete,
            Icons.Rounded.RadioButtonUnchecked,
            "I know",
            primary = true
        )
    )

    Column(modifier = modifier.fillMaxSize()) {
        CardProgressIndicator(
            modifier = Modifier.padding(16.dp),
            cardIndex = cardIndex,
            cardsSize = cards.size
        )
        Card(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
                .weight(1f),
            elevation = 4.dp,
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CardContentPanel(
                    cardContent = currentCard,
                    modifier = modifier
                        .fillMaxSize()
                        .weight(1f),
                    visibleAnswer = visibleAnswer,
                    onVisibleButtonClick = {
                        visibleAnswer = !visibleAnswer
                    }
                )
                Divider()
                CardActionBar(
                    actions = cardActions,
                    onActionClick = { actionId ->
                        visibleAnswer = false
                        onCardActionClick(currentCard.id, actionId)
                        if (cardIndex < cards.size - 1) {
                            cardIndex += 1
                        } else {
                            onAllCardsDone()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CardScreenContentPreview() {
    val fakeCardContents = IntRange(1, 10)
        .map {
            CardContent(
                categoryId = 0,
                question = "Question $it",
                answer = "Answer $it"
            )
        }

    JetCardTheme {
        CardScreenContent(
            cards = fakeCardContents,
            onCardActionClick = { _: Int, _: String -> },
            onAllCardsDone = {}
        )
    }
}

@Composable
fun CardProgressIndicator(
    modifier: Modifier = Modifier,
    cardIndex: Int,
    cardsSize: Int,
) {
    val progress = (cardIndex + 1) / cardsSize.toFloat()

    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
            ) {
                Text(
                    "Progress",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "${cardIndex + 1}/${cardsSize} (${(progress * 100).roundToInt()}%)",
                )
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp
                    ),
                progress = progress
            )
        }
    }
}

@Preview
@Composable
fun CardProgressIndicatorPreview() {
    JetCardTheme {
        CardProgressIndicator(
            cardIndex = 2,
            cardsSize = 10
        )
    }
}

@Composable
fun CardContentPanel(
    modifier: Modifier = Modifier,
    cardContent: CardContent,
    visibleAnswer: Boolean = false,
    onVisibleButtonClick: () -> Unit
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            CardQuestionPanel(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                question = cardContent.question
            )
            Divider()
            CardAnswerPanel(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                answer = cardContent.answer,
                visible = visibleAnswer,
                onVisibleButtonClick = onVisibleButtonClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardContentPanelPreview() {
    JetCardTheme {
        CardContentPanel(
            cardContent = CardContent(0, 0, "Question", "Answer"),
            onVisibleButtonClick = {}
        )
    }
}

@Composable
fun CardTimer(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Timer)
            Spacer(modifier = Modifier.width(8.dp))
            Text("00:00")
        }
    }
}

@Preview
@Composable
fun CardTimerPreview() {
    JetCardTheme {
        CardTimer()
    }
}

@Composable
fun CardQuestionPanel(
    modifier: Modifier = Modifier,
    question: String
) {
    Box(
        modifier = modifier,
    ) {
        Text(
            question,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            softWrap = true,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun CardAnswerPanel(
    modifier: Modifier = Modifier,
    answer: String,
    visible: Boolean = false,
    onVisibleButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            onClick = onVisibleButtonClick
        ) {
            val icon = if (visible) {
                Icons.Rounded.VisibilityOff
            } else {
                Icons.Rounded.Visibility
            }
            Icon(icon)
        }
        val style = if (visible) {
            MaterialTheme.typography.h4
        } else {
            MaterialTheme.typography.h4.copy(
                background = MaterialTheme.colors.onSurface
            )
        }

        Text(
            answer,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            softWrap = true,
            style = style
        )
    }
}

@Composable
private fun CardActionBar(
    modifier: Modifier = Modifier,
    actions: List<CardAction>,
    onActionClick: (String) -> Unit
) {
    Row(modifier = modifier) {
        actions.forEach { action ->
            CardActionButton(
                modifier = Modifier.weight(1f),
                icon = action.icon,
                text = action.text,
                primary = action.primary,
                onClick = {
                    onActionClick(action.id)
                }
            )
        }
    }
}

@Preview
@Composable
fun CardActionBarPreview() {
    JetCardTheme {
        CardActionBar(
            actions = listOf(
                CardAction("", Icons.Rounded.Close, "I don't know"),
                CardAction("", Icons.Rounded.SkipNext, "Skip"),
                CardAction("", Icons.Rounded.RadioButtonUnchecked, "I know", primary = true)
            ),
            onActionClick = {}
        )
    }
}

@Composable
private fun CardActionButton(
    modifier: Modifier = Modifier,
    icon: VectorAsset,
    text: String,
    primary: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (primary) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }

    val contentColor = if (primary) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onSurface
    }

    Button(
        modifier = modifier,
        elevation = ButtonConstants.defaultElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(icon)
                Text(text)
            }
        },
        onClick = onClick,
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    )
}
