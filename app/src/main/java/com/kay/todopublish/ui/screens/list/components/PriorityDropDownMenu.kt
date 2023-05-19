package com.kay.todopublish.ui.screens.list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kay.todopublish.R
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.kay.todopublish.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityDropDownMenu(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    // Icon arrow animation rotation state
    val angle: Float by animateFloatAsState(
        targetValue = if (menuExpanded) 0f else 90f
    )

    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { parentSize = it.size }
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { menuExpanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small // Returns a rounded corner
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier
                .weight(8f),
            text = priority.name,
            style = MaterialTheme.typography.subtitle2
        )
        /**
         * Since your whole item is clickable,, this should just be an icon and not
         * a button. This way you have one less
         */
        Icon(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(weight = 1f),
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = stringResource(id = R.string.drop_down_arrow)
        )
        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current) { parentSize.width.toDp() }), // Fraction reduce the dropdown menusWidth so it match the padding.
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            // DropDown menu item 1
            // (slice) Only needs the 3 values from priority (er do not need the none priority)
            Priority.values().slice(0..2).forEach { priority ->
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        onPrioritySelected(priority)
                    }
                ) {
                    PriorityItem(priority = priority)
                }
            }
        }
    }
}

@Composable
@Preview
fun PriorityDropDownMenuPreview() {
    PriorityDropDownMenu(
        priority = Priority.LOW,
        onPrioritySelected = {}
    )
}
