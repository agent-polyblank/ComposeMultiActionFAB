package io.github.agentpolyblank.extendedfab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Extended FAB.
 * @param expanded The state of the FAB.
 * @param onExpandedChange Callback when the FAB expansion state changes.
 * @param modifier The modifier to be applied to the FAB container.
 * @param shape The shape of the FAB.
 * @param containerColor The color used for the background of this FAB.
 * @param contentColor The preferred color for content inside this FAB.
 * @param elevation [FloatingActionButtonElevation] used to resolve the elevation for this FAB.
 * @param icon The icon to display.
 * @param elements The elements to display when the FAB is expanded.
 */
@Composable
fun ExtendedFab(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    icon: @Composable () -> Unit,
    elements: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            elements()
        }

        FloatingActionButton(
            onClick = { onExpandedChange(!expanded) },
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            elevation = elevation
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                icon()
            }
        }
    }
}