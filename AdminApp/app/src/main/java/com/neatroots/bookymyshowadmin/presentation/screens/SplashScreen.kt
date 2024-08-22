package com.neatroots.bookymyshowadmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.compose.animation.core.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.isSpecified
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.presentation.navigation.Routes
import com.neatroots.bookymyshowadmin.ui.theme.c10
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val transition = rememberInfiniteTransition()
    val animationSpec = tween<Float>(durationMillis = 1500)

    val animationState = transition.animateFloat(
        initialValue = 100f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable( animation = animationSpec)
    )
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colorScheme.primary.isSpecified // Adjust based on theme
    val statusBarColor = c10 // Replace with your desired color

    systemUiController.setSystemBarsColor(
        color = statusBarColor,
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.c10))
    ) {
        val (image, overlay, icon, title, subtitle) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.semiTransparent))
                .constrainAs(overlay) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .size(120.dp)
                .offset(y = animationState.value.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 34.sp,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(top = 16.dp)
                .offset(y = animationState.value.dp)
                .constrainAs(title) {
                    top.linkTo(icon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(id = R.string.app_desp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.white).copy(alpha = 0.7f),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = animationState.value.dp)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }

    LaunchedEffect(key1 = true) {
        delay(800) // 2-second delay for the splash screen
        navController.navigate(Routes.Home) {
            popUpTo(Routes.SpalshScreen) { inclusive = true } // Remove splash from backstack
        }
    }
}
