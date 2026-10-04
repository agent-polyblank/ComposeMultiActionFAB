package io.github.agentpolyblank.extendedfab.sample

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.agentpolyblank.extendedfab.ui.ExtendedFab
import io.github.agentpolyblank.extendedfab.ui.ExtendedFabItem
import kotlinx.coroutines.isActive
import io.github.agentpolyblank.extendedfab.sample.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    var isExpanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(if (isExpanded) 360f else 0f)

    val isRotating by remember { mutableStateOf(false) }

    val rotate = remember { Animatable(0f) }
    val target = 360f
    if (isRotating) {
        LaunchedEffect(Unit) {
            while (isActive) {
                val remaining = (target - rotate.value) / target
                rotate.animateTo(
                    target,
                    animationSpec = tween((1_000 * remaining).toInt(), easing = LinearEasing)
                )
                rotate.snapTo(0f)
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Extended FAB", modifier = Modifier.align(Alignment.Center), fontSize = 32.sp)
        ExtendedFab(
            modifier = Modifier.align(Alignment.BottomEnd),
            expanded = isExpanded,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp).rotate(rotation)
                )
            },
            shape = RoundedCornerShape(50),
            onExpandedChange = { expanded ->
                isExpanded = !isExpanded
            }
            ) {
            ExtendedFabItem(
                label = "Something",
                expanded = isExpanded,
                index = 0,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                onClick = {
                    // Handle click
                }
            )
            ExtendedFabItem(
                label = "Something else",
                index = 1,
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null
                    )
                },
                expanded = isExpanded,
                onClick = {
                    // Handle click
                }
            )
        }
    }
}
