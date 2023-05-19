package com.kay.todopublish.util

/**
 *  A util folder like this should be for things that are shared in multiple places in the app. RequestState
 *  and Action might fit here, but CloseIconState and SearchAppBarState should be by the app bar. The Constants
 *  file shouldn't be done like that either, you don't just want an object with a bunch of random constants.
 *  The correct place to put constants is in a Companion object in the class that uses them. (or in a more
 *  specific object in the case of navigation routes, for example)
 */
enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

// Tasking a string and returning an enum entry (convert string to Action class)
// can also write the function like this: fun toAction(ADD: String?)
fun String?.toAction(): Action {
    return if (this.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(this)
    /*return when {
        this == "ADD" -> {
            Action.ADD
        }
        this == "UPDATE" -> {
            Action.UPDATE
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "DELETE_ALL" -> {
            Action.DELETE_ALL
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        else -> {
            Action.NO_ACTION
        }
    }*/
}
