package com.example.fitness.Page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitness.R
import com.example.fitness.TrackCard
import com.example.fitness.client.models.UserDto
import com.example.fitness.utils.ApiService
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPage(activity: ComponentActivity){
    val pagerState = rememberPagerState(0)
    ProfilePage(activity)
//    Column() {
//
//        Tabs(pagerState = pagerState)
//        TabsContent(pagerState = pagerState,
//            activity)
//    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(pagerState: PagerState){
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .clip(
                        RoundedCornerShape(
                            topStart = 3.0.dp,
                            topEnd = 3.0.dp,
                            bottomEnd = 0.0.dp,
                            bottomStart = 0.0.dp
                        )
                    ),
                height = 3.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
            Tab(
                text = {
                    Text(
                        stringResource(id = R.string.Profile),
                        color = when{
                            pagerState.currentPage == 0 -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                selected = pagerState.currentPage == 0,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
        Tab(
            text = {
                Text(
                    stringResource(id = R.string.Activity),
                    color = when{
                        pagerState.currentPage == 1 -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            },
            selected = pagerState.currentPage == 1,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(pagerState: PagerState, activity: ComponentActivity) {
    HorizontalPager(state = pagerState,
    pageCount = 2)
    {
            page ->
        when (page) {
            0 -> ProfilePage(activity)
            1 -> {
                var list = mutableListOf(Info())
                var tracks by remember { mutableStateOf(list) }
                val broadcastReceiver = object: BroadcastReceiver(){
                    override fun onReceive(p0: Context, p1: Intent) {
                        var info = Info()
                        info.name = p1.extras!!.get("username") as String
                        info.date = p1.extras!!.get("date") as String
                        info.distance = p1.extras!!.get("distance") as Float
                        info.speed = p1.extras!!.get("speed") as Float
                        info.time = p1.extras!!.get("time") as String
                        tracks.add(info)
                    }
                }
                activity.registerReceiver(broadcastReceiver, IntentFilter("usertracks"))
                tracks.forEach{
                    TrackCard(
                        it,
                        User = "Some Gay",
                        createdAt = "16:48",
                        title = "First Outgoing",
                        description = "Light riding on bike",
                        modifier = Modifier.padding(8.dp)
                    )}
            }
        }
    }
}