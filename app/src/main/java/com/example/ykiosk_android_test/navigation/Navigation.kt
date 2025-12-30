package com.example.ykiosk_android_test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ykiosk_android_test.basic.StartScreen
import com.example.ykiosk_android_test.bluetooth.PrintControlScreen
import com.example.ykiosk_android_test.bluetooth.PrinterListScreen
import com.example.ykiosk_android_test.login.LoginScreen
import com.example.ykiosk_android_test.order.OrderScreen
import com.example.ykiosk_android_test.store.KioskScreen
import com.example.ykiosk_android_test.store.StoreMainScreen
import com.example.ykiosk_android_test.view_model.KioskViewModel
import com.example.ykiosk_android_test.view_model.StoreViewModel

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
            StoreMainScreen(
                myStoreViewModel,
                onNavigateToPrinterListStore = { storeId ->
                    navController.navigate(NavRoute.printerListStorePath(storeId))
                },
                navController = navController)
        }

        composable(
            route = NavRoute.PRINTER_LIST_STORE,
            arguments = listOf(navArgument("storeId") {type = NavType.StringType})
        ) { backStackEntry ->
            val storeId = backStackEntry.arguments?.getString("storeId") ?: ""
            PrinterListScreen (
                storeId = storeId,
                onDeviceSelected = { device ->
                    //val destination = NavRoute.printControlPath(device.address, device.name)
                    val destination = NavRoute.kioskModePath(device.address, device.name, storeId = storeId)
                    navController.navigate(destination)
                }
            )
        }

        composable(
            route = NavRoute.KIOSK_MODE, // "order_screen/{address}/{name}/{storeId}"
            arguments = listOf(
                navArgument("address") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("storeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 1. 가방(arguments)에서 3개의 데이터를 각각 꺼냅니다.
            val address = backStackEntry.arguments?.getString("address") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: "알 수 없음"
            val storeId = backStackEntry.arguments?.getString("storeId") ?: ""

            val context= LocalContext.current

            val kioskViewModel: KioskViewModel = viewModel {
                KioskViewModel(storeId, context)
            }

            // 2. 주문 화면(KioskScreen 또는 OrderScreen)을 호출하며 데이터를 전달합니다.
            KioskScreen(
                viewModel = kioskViewModel,
                deviceAddress = address,
                deviceName = name,
                storeId = storeId,
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToOrderComplete = {
                    navController.navigate(NavRoute.ORDER_COMPLETE)
                }
            )
        }

        composable(NavRoute.PRINTER_LIST) {
            PrinterListScreen (
                storeId = "TEST_MODE",
                onDeviceSelected = { device ->
                    val destination = NavRoute.printControlPath(
                        address = device.address,
                        name = device.name
                    )
                    navController.navigate(destination)
                }
            )
        }

        composable(
            route = NavRoute.PRINT_CONTROL // "print_control_screen/{address}/{name}"
        ) { backStackEntry ->
            // 💡 'backStackEntry'는 현재 화면에 대한 정보를 담고 있는 가방입니다.
            // 이 가방에서 아까 주소에 담아 보낸 데이터를 꺼냅니다.

            // 주소에서 "address"라는 키값으로 데이터 꺼내기 (없으면 빈 문자열)
            val deviceAddress = backStackEntry.arguments?.getString("address") ?: ""
            // 주소에서 "name"이라는 키값으로 데이터 꺼내기 (없으면 "알 수 없음")
            val deviceName = backStackEntry.arguments?.getString("name") ?: "알 수 없음"

            // 꺼낸 데이터를 PrintControlScreen에 전달하며 화면을 그립니다.
            PrintControlScreen(
                deviceName = deviceName,
                deviceAddress = deviceAddress,
                onBack = {
                    // 뒤로가기 버튼 누르면 이전 화면(목록)으로 돌아가기
                    navController.popBackStack()
                }
            )
        }

        // 파라미터가 있는 화면
    }
}