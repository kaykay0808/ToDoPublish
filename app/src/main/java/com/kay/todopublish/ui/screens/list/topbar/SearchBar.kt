package com.kay.todopublish.ui.screens.list.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.ui.theme.TOP_APP_BAR_HEIGHT
import com.kay.todopublish.ui.theme.topAppBarBackgroundColor
import com.kay.todopublish.ui.theme.topAppBarContentColor

@Composable
fun SearchAppBar(
    textSearchInput: String,
    onSearchTextChange: (String) -> Unit,
    onCloseIconClicked: () -> Unit,
    onSearchClicked: (String) -> Unit, // ?
    // viewState: ToDoViewState,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            value = textSearchInput,
            onValueChange = { onSearchTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            // everything we type in textField will be in a single line.
            singleLine = true,
            leadingIcon = {
                IconButton(
                    // opacity for the icon
                    modifier = Modifier.alpha(ContentAlpha.disabled),
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = onCloseIconClicked
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Exit search bar",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(textSearchInput) }),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        textSearchInput = "",
        onSearchTextChange = {},
        onCloseIconClicked = {},
        onSearchClicked = {}
    )
}
