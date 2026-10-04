package io.github.agentpolyblank.extendedfab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Extended FAB item.
 * @param expanded Whether the FAB is expanded or not.
 * @param icon The icon to display.
 * @param onClick The action to perform when the icon is clicked.
 * @param label The label to display.
 * @param shape The shape of the FAB.
 * @param staggerDelayMs The delay in ms between the FABs.
 * @param labelDelayAfterFabExpand The delay in ms after which the label will appear.
 * @param index The index of the FAB - determines the order in which the buttons expand.
 */
@Composable
fun ExtendedFabItem(
    expanded: Boolean, // todo eventually dsl will be able to propogate this
    index: Int = 0,
    staggerDelayMs: Int = 70,
    icon: @Composable () -> Unit,
    label: String = "",
    shape: Shape = RoundedCornerShape(50),
    labelDelayAfterFabExpand:  Int = 120,
    onClick: () -> Unit
) {
    val duration = 200

    val fabEnterDelay = index * staggerDelayMs
    val labelEnterDelay = fabEnterDelay + labelDelayAfterFabExpand

    val labelExitDelay = index * staggerDelayMs
    val fabExitDelay = labelExitDelay + labelDelayAfterFabExpand

    val emergenceOffset = { fullHeight: Int -> (fullHeight * 1.3f * (index + 1)).toInt() }

    Row(verticalAlignment = Alignment.CenterVertically) {

        AnimatedVisibility(
            visible = expanded,
            enter = expandHorizontally(
                expandFrom = Alignment.End,
                animationSpec = tween(durationMillis = duration, delayMillis = labelEnterDelay)
            ) + fadeIn(animationSpec = tween(durationMillis = duration, delayMillis = labelEnterDelay)),
            exit = shrinkHorizontally(
                shrinkTowards = Alignment.End,
                animationSpec = tween(durationMillis = duration, delayMillis = labelExitDelay)
            ) + fadeOut(animationSpec = tween(durationMillis = duration, delayMillis = labelExitDelay))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = label, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.size(10.dp))
            }
        }

        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(
                initialOffsetY = emergenceOffset,
                animationSpec = tween(durationMillis = duration, delayMillis = fabEnterDelay)
            ) + scaleIn(
                initialScale = 0.2f,
                animationSpec = tween(durationMillis = duration, delayMillis = fabEnterDelay)
            ) + fadeIn(animationSpec = tween(durationMillis = duration, delayMillis = fabEnterDelay)),
            exit = slideOutVertically(
                targetOffsetY = emergenceOffset,
                animationSpec = tween(durationMillis = duration, delayMillis = fabExitDelay)
            ) + scaleOut(
                targetScale = 0.2f,
                animationSpec = tween(durationMillis = duration, delayMillis = fabExitDelay)
            ) + fadeOut(animationSpec = tween(durationMillis = duration, delayMillis = fabExitDelay))
        ) {
            SmallFloatingActionButton(
                shape = shape,
                onClick = onClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Box(modifier = Modifier.padding(5.dp)) {
                    icon()
                }
            }
        }
    }
}

@Preview
@Composable
fun ExtendedFabItemPreview() {
    ExtendedFabItem(
        expanded = true,
        index = 0,
        icon = { Icon(Icons.Default.Settings, contentDescription = "") },
        label = "Settings",
        onClick = {}
    )
}