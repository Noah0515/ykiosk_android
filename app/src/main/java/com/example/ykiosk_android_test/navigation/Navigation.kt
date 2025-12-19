package com.example.ykiosk_android_test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ykiosk_android_test.basic.StartScreen
import com.example.ykiosk_android_test.order.OrderScreen

@Composable
fun MainAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.START
    ) {
        // 파라미터가 없는 화면
        composable(NavRoute.START) {
            StartScreen(
                onNavigateToOrder = {
                    navController.navigate(NavRoute.ORDER)
                }
            )
        }

        composable(NavRoute.ORDER) {
            OrderScreen(
                onNavigateToOrderComplete = {
                    navController.navigate(NavRoute.ORDER_COMPLETE)
                }
            )
        }


        // 파라미터가 있는 화면
    }
}