package com.example.fitness
//
//import android.graphics.drawable.Icon
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
//import androidx.compose.runtime.Composable
//import androidx.compose.material3.windowsizeclass.WindowSizeClass
//import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
//import androidx.compose.ui.Modifier
//import com.example.fitness.utils.FitnessNavigationContentPosition
//import com.example.fitness.utils.FitnessNavigationType
//
//@Composable
//fun ReplyApp(
//    windowSize: WindowSizeClass,
//    displayFeatures: List<DisplayFeature>,
//    replyHomeUIState: ReplyHomeUIState,
//    closeDetailScreen: () -> Unit = {},
//    navigateToDetail: (Long, ReplyContentType) -> Unit = { _, _ -> }
//) {
//    /**
//     * This will help us select type of navigation and content type depending on window size and
//     * fold state of the device.
//     */
//    val navigationType: FitnessNavigationType
//
//    when (windowSize.widthSizeClass) {
//        WindowWidthSizeClass.Compact -> {
//            navigationType = FitnessNavigationType.BOTTOM_NAVIGATION
//        }
//
//        WindowWidthSizeClass.Medium -> {
//            navigationType = FitnessNavigationType.NAVIGATION_RAIL
//        }
//
//        WindowWidthSizeClass.Expanded -> {
//            navigationType = FitnessNavigationType.PERMANENT_NAVIGATION_DRAWER
//        }
//
//        else -> {
//            navigationType = FitnessNavigationType.BOTTOM_NAVIGATION
//        }
//    }
//    val navigationContentPosition = when (windowSize.heightSizeClass) {
//        WindowHeightSizeClass.Compact -> {
//            FitnessNavigationContentPosition.TOP
//        }
//        WindowHeightSizeClass.Medium,
//        WindowHeightSizeClass.Expanded -> {
//            FitnessNavigationContentPosition.CENTER
//        }
//        else -> {
//            FitnessNavigationContentPosition.TOP
//        }
//    }
//}
//
//@Composable
//fun HomeNavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        route = Graph.HOME,
//        startDestination = BottomBarScreen.Home.route
//    ) {
//        composable(route = BottomBarScreen.Home.route) {
//            ScreenContent(
//                name = BottomBarScreen.Home.route,
//                onClick = {
//                    navController.navigate(Graph.DETAILS)
//                }
//            )
//        }
//        composable(route = BottomBarScreen.Profile.route) {
//            ScreenContent(
//                name = BottomBarScreen.Profile.route,
//                onClick = { }
//            )
//        }
//        composable(route = BottomBarScreen.Settings.route) {
//            ScreenContent(
//                name = BottomBarScreen.Settings.route,
//                onClick = { }
//            )
//        }
//        detailsNavGraph(navController = navController)
//    }
//}