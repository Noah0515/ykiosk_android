package com.example.ykiosk_android_test.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ykiosk_android_test.custom_widget.button.Button1
import com.example.ykiosk_android_test.order.OrderScreen
import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme


@Composable
fun MyApp(name : String = "Hello!") {
    //HomeScreen(name)
    StartScreen(onNavigateToOrder = {}, onNavigateToPrinterList = {})
}

@Composable
fun HomeScreen(str : String) {
    Surface(color = MaterialTheme.colorScheme.secondary) {

        Column(
            modifier = Modifier
                .padding(10.dp)
                .border(2.dp, Color.Blue)
        ) {
            Text("this area is \"Column\"", modifier = Modifier.padding(15.dp))
            Text("Home Screen $str")
            Text("this is second line!")
        }
    }
}

@Composable
fun StartScreen(
    onNavigateToOrder: () -> Unit,
    onNavigateToPrinterList: () -> Unit,
    input : String = "StartScreen"
){
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        val parentWidth = this.maxWidth
        val parentHeight = this.maxHeight

        val horizontalPadding = parentWidth * 0.05f
        val verticalPadding = parentHeight * 0.05f
        Surface(
            color = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(3.dp),
            tonalElevation = 5.dp, // margin 역할
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontalPadding, verticalPadding)
        ) {
            Column(
                modifier = Modifier.
                    padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = input,
                    modifier = Modifier
                        .padding(10.dp)
                )
                Button1(
                    "주문화면으로",
                    onclick = onNavigateToOrder
                )

                Button1(
                    "프린터 리스트",
                    onclick = onNavigateToPrinterList
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET)
@Composable
fun HomeScreenPreview() {
    Ykiosk_android_testTheme {
        StartScreen( onNavigateToOrder = {}, onNavigateToPrinterList = {} ,"확인용")
        //HomeScreen("asdf")
    }
}