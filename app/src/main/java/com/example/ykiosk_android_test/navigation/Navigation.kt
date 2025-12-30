package com.example.ykiosk_android_test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ykiosk_android_test.basic.StartScreen
import com.example.ykiosk_android_test.bluetooth.PrinterListScreen
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
                },
                onNavigateToPrinterList = {
                    navController.navigate(NavRoute.PRINTER_LIST)
                },
                onNavigateToLogin = {
                    navController.navigate(NavRoute.LOGIN)
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

        composable(NavRoute.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(NavRoute.STOREMAIN) {
            val context= LocalContext.current

            val myStoreViewModel: StoreViewModel = viewModel {
                StoreViewModel(context)
            }
            StoreMainScreen(myStoreViewModel)
        }

        composable(NavRoute.PRINTER_LIST) {
            PrinterListScreen (
                onDeviceSelected = { device ->
                    println("선택된 기기:${device.name}")
                }
            )
        }

        // 파라미터가 있는 화면
    }
}