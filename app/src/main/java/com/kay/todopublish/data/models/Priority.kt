package com.kay.todopublish.data.models

import androidx.compose.ui.graphics.Color
import com.kay.todopublish.ui.theme.HighPriorityColor
import com.kay.todopublish.ui.theme.LowPriorityColor
import com.kay.todopublish.ui.theme.MediumPriorityColor
import com.kay.todopublish.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
