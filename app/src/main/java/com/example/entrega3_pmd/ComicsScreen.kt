package com.example.entrega3_pmd

import com.example.entrega3_pmd.model.Comic


import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.entrega3_pmd.model.ComicsRepository
import com.example.entrega3_pmd.ui.theme.Entrega3_PMDTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ComicsList(
    comics: List<Comic>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {

        LazyVerticalGrid(columns = GridCells.Fixed(2), // 3 items per row
            contentPadding = contentPadding,
            modifier = modifier) {
            itemsIndexed(comics) { index, comic ->

                    ComicListItem(
                        comic = comic,
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            // Animate each list item to slide in vertically
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = StiffnessVeryLow,
                                        dampingRatio = DampingRatioLowBouncy
                                    ),
                                    initialOffsetY = { it * (index + 1) } // staggered entrance
                                )
                            )
                    )




            }
        }
    }
}



@Composable
fun ComicListItem(
    comic: Comic,
    modifier: Modifier = Modifier
) {

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = modifier,
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .padding(5.dp)
                    .height(150.dp)
                //.sizeIn(minHeight = 72.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .weight(3f)
                        .clip(RoundedCornerShape(5.dp))

                ) {
                    Image(
                        painter = painterResource(comic.imageRes),
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        contentScale = ContentScale.Fit
                    )
                }


                    Spacer(modifier = Modifier.width(5.dp))

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .weight(2f)
                        .clip(RoundedCornerShape(5.dp))

                ) {
                    Column {
                        Text(
                            text = stringResource(comic.nameRes),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = stringResource(comic.descriptionRes),
                            style = MaterialTheme.typography.labelSmall,

                            )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = stringResource(comic.dia),
                            style = MaterialTheme.typography.labelLarge,
                            )
                    }
                }
            }
        }
    }

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ComicPreview() {
    val comic = Comic(
        R.string.comic1,
        R.string.description1,
        R.drawable.comic1,
        R.string.day1
    )
    Entrega3_PMDTheme {
        ComicListItem(comic = comic)
    }
}

@Preview("Comics List")
@Composable
fun ComicsPreview() {
    Entrega3_PMDTheme(darkTheme = false) {
        Surface (
            color = MaterialTheme.colorScheme.background
        ) {
            /* Important: It is not a good practice to access data source directly from the UI.
            In later units you will learn how to use ViewModel in such scenarios that takes the
            data source as a dependency and exposes heroes.
            */
            ComicsList(comics = ComicsRepository.comics)
        }
    }
}