package com.github.namhyungu.jetcard.ui.card

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
import com.github.namhyungu.jetcard.model.CardContent
import com.github.namhyungu.jetcard.ui.component.ErrorText
import com.github.namhyungu.jetcard.ui.component.JetCardTopAppBar
import com.github.namhyungu.jetcard.ui.component.TextFieldWithError
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme

typealias OnCardAdd = (CardContent) -> Unit

typealias OnFormComplete = (CardContent) -> Unit

@Composable
fun AddCardScreen(
    onNavigateUpClick: () -> Unit,
    onCardAdd: OnCardAdd
) {
    Scaffold(
        topBar = {
            JetCardTopAppBar(
                title = "Add Card",
                navigationIcon = {
                    IconButton(onClick = onNavigateUpClick) {
                        Icon(Icons.Rounded.Close)
                    }
                },
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        AddCardScreenContent(
            modifier = modifier,
            onCardAdd = onCardAdd
        )
    }
}

@Composable
private fun AddCardScreenContent(
    modifier: Modifier = Modifier,
    onCardAdd: OnCardAdd
) {
    val (question, setQuestion) = remember { mutableStateOf("") }
    val (answer, setAnswer) = remember { mutableStateOf("") }

    AddCardForm(
        modifier = modifier,
        question = question,
        answer = answer,
        onQuestionChange = setQuestion,
        onAnswerChange = setAnswer,
        onFormComplete = onCardAdd
    )
}

@Composable
private fun AddCardForm(
    modifier: Modifier = Modifier,
    question: String,
    answer: String,
    onQuestionChange: (String) -> Unit,
    onAnswerChange: (String) -> Unit,
    onFormComplete: OnFormComplete
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        val isQuestionError = question.isBlank()
        val isAnswerError = answer.isBlank()

        TextFieldWithError(
            value = question,
            onValueChange = onQuestionChange,
            label = { Text("Question") },
            isErrorValue = isQuestionError,
            errorLabel = { ErrorText("Error: Question is empty") },
            errorIcon = { Icon(Icons.Rounded.Error) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFieldWithError(
            value = answer,
            onValueChange = onAnswerChange,
            label = { Text("Answer") },
            isErrorValue = isAnswerError,
            errorLabel = { ErrorText("Error: Answer is empty") },
            errorIcon = { Icon(Icons.Rounded.Error) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                if (!isQuestionError && !isAnswerError) {
                    onFormComplete(
                        CardContent(
                            question = question, answer = answer
                        )
                    )
                }
            }
        ) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCardFormPreview() {
    JetCardTheme {
        AddCardForm(
            question = "Question",
            answer = "Answer",
            onQuestionChange = {},
            onAnswerChange = {},
            onFormComplete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddCardFormHasErrorPreview() {
    JetCardTheme {
        AddCardForm(
            question = "",
            answer = "",
            onQuestionChange = {},
            onAnswerChange = {},
            onFormComplete = {}
        )
    }
}