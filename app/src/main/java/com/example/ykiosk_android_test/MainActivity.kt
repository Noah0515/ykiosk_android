package com.example.ykiosk_android_test

import android.app.Activity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ykiosk_android_test.basic.MyApp
import com.example.ykiosk_android_test.basic.StartScreen
import com.example.ykiosk_android_test.bluetooth.BluetoothPermissionHandler
import com.example.ykiosk_android_test.navigation.MainAppNavHost
import com.example.ykiosk_android_test.order.OrderScreen
import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme
import kotlinx.serialization.Serializable


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContent {
//            Ykiosk_android_testTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
        setContent {
            val context = LocalContext.current
            val activity = context as? Activity
            var hasPermission by remember { mutableStateOf(false) }

            if (!hasPermission) {
                // 권한이 없으면 요청 화면 실행
                BluetoothPermissionHandler(
                    onPermissionsGranted = { hasPermission = true },
                    onPermissionsDenied = {
                        Toast.makeText(context, "블루투스 권한이 거부되어 앱을 종료합니다.", Toast.LENGTH_LONG).show()
                        //권한이 없으니 앱 종료
                        activity?.finish()
                    }
                )
            } else {
                // 권한 허용 후 실제 앱 UI (2단계에서 만들 기기 리스트 화면)

                val windowSizeClass = calculateWindowSizeClass(this)
                val widthSizeClass = windowSizeClass.widthSizeClass

                Ykiosk_android_testTheme {
                    MainAppNavHost()
                    //MyApp()
                    //StartScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ykiosk_android_testTheme {
        Greeting("Android")
    }
}

