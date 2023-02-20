package com.gft.example.observablesession.ui.uitls

/**
 * Provides a way to perform safe view updates.
 *
 * Most common example is updating the view in such a way that would cause a
 * subsequent view update, i.e.:
 * * view-model publishes a view state update with a new value of a text input
 * * text input has a listener for text change which forwards new text value to
 *   the view-model
 * * new text value causes the view-model to publish a view state update with a
 *   new value of a text input
 */
class ViewUpdate {

    private var isUpdatingView = false

    /**
     * Performs given [updateAction] as a view-updating operation.
     */
    fun update(updateAction: () -> Unit) {
        if (isUpdatingView) {
            throw IllegalStateException("Update already in progress!")
        }
        isUpdatingView = true
        updateAction()
        isUpdatingView = false
    }

    /**
     * Performs given [updateAction] as a view-updating operation with provided
     * [receiver] as `this`.
     */
    fun <T> updateWith(receiver: T, updateAction: T.() -> Unit) {
        if (isUpdatingView) {
            throw IllegalStateException("Update already in progress!")
        }
        isUpdatingView = true
        receiver.updateAction()
        isUpdatingView = false
    }

    /**
     * Performs given [action] if view is not currently being updated.
     *
     * Should be used to safe-guard operations that would otherwise cause
     * subsequent call to [update].
     */
    fun whenNotUpdating(action: () -> Unit) {
        if (isUpdatingView.not()) {
            action()
        }
    }
}
