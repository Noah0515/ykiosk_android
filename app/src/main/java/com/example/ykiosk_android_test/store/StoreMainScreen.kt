package com.example.ykiosk_android_test.store

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Button3
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ykiosk_android_test.Item.StoreItemRow
import com.example.ykiosk_android_test.custom_widget.button.Button1
import com.example.ykiosk_android_test.custom_widget.button.Button3
import com.example.ykiosk_android_test.custom_widget.text.LargeText3
import com.example.ykiosk_android_test.custom_widget.text.LargeText4
import com.example.ykiosk_android_test.order.OrderScreen
import com.example.ykiosk_android_test.ui.theme.YKioskColors
import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme
import com.example.ykiosk_android_test.view_model.StoreViewModel


@Composable
fun StoreMainScreen(myStoreViewModel: StoreViewModel) {
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val myStoreViewModel: StoreViewModel = viewModel { StoreViewModel(context)}

        val myStores = myStoreViewModel.stores

        val paramWidth = this.maxWidth
        val paramHeight = this.maxHeight

        Surface (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                Box(

                ) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onTertiaryContainer)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(

                        ) {
                            LargeText4(text = "YKiosk")
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button3(text = "가게 만들기", onclick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp))
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1F)
                                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp)
                                    .background(MaterialTheme.colorScheme.surface),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                LargeText4("내 가게 목록", color = YKioskColors.TextPrimary)
                                LazyColumn (
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(myStores) { store ->
                                        StoreItemRow(store)
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1F)
                                    .padding(start = 8.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    device = Devices.TABLET,
    widthDp = 1794,
    heightDp = 1120
)
fun StoreMainScreenPrev() {

    val context= LocalContext.current

    val myStoreViewModel: StoreViewModel = viewModel {
        StoreViewModel(context)
    }

    Ykiosk_android_testTheme {
        StoreMainScreen(myStoreViewModel)
    }
}