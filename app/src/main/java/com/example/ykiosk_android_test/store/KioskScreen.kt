package com.example.ykiosk_android_test.store

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ykiosk_android_test.DTO.response.MenuDetailResponse
import com.example.ykiosk_android_test.DTO.response.MenuGroupDetailResponse
import com.example.ykiosk_android_test.DTO.response.OptionCategoryDetailResponse
import com.example.ykiosk_android_test.DTO.response.StoreMenuDetailResponse
import com.example.ykiosk_android_test.Item.CartItem
import com.example.ykiosk_android_test.custom_widget.button.CategoryButton
import com.example.ykiosk_android_test.custom_widget.button.GroupButton
import com.example.ykiosk_android_test.custom_widget.customBorder
import com.example.ykiosk_android_test.custom_widget.text.LargeText2
import com.example.ykiosk_android_test.custom_widget.text.LargeText3
import com.example.ykiosk_android_test.custom_widget.text.LargeText4
import com.example.ykiosk_android_test.testdata.dto
import com.example.ykiosk_android_test.testdata.menuDetail

import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme
import com.example.ykiosk_android_test.ui.theme.*
import com.example.ykiosk_android_test.view_model.KioskViewModel
import com.example.ykiosk_android_test.view_model.StoreViewModel


@Composable
fun KioskScreen(
    viewModel: KioskViewModel,
    onNavigateToOrderComplete: () -> Unit,
    modifier: Modifier = Modifier,
    deviceAddress: String,
    deviceName: String,
    storeId: String,
    onBack: () -> Unit
){
    val context = LocalContext.current

    val storeMenuDetail = viewModel.storeMenuDetail
    val cartList = viewModel.cartList

    KioskScreenContent(
        storeMenuDetail = storeMenuDetail,
        isLoading = viewModel.isLoading,
        viewModel.selectedGroup,
        cartList = cartList,
        onGroupSelected = {group -> viewModel.selectGroup(group)},
        onAddToCart = {cartItem -> viewModel.addToCart(cartItem)},
        onUpdateQuantity = { item, delta ->
            viewModel.updateQuantity(item, delta) // 실제 로직 실행
        }
    )

}

@Composable
fun KioskScreenContent(
    storeMenuDetail: StoreMenuDetailResponse?,
    isLoading: Boolean,
    selectedGroup: MenuGroupDetailResponse?,
    cartList: List<CartItem>,
    onGroupSelected: (MenuGroupDetailResponse) -> Unit,
    onAddToCart: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit
) {
    var selectedMenuForDialog by remember { mutableStateOf<MenuDetailResponse?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Log.d("Kiosk", "KioskScreenContent")
    BoxWithConstraints (// 화면의 사이즈를 알기 위해 필요한 가장 바깥 껍데기
        modifier = Modifier
            .background(Gray)
            .fillMaxSize()
    ) {

        val paramWidth = this.maxWidth
        val paramHeight = this.maxHeight

        val horizontalPadding = paramWidth * 0.05f
        val verticalPadding = paramHeight * 0.05f
        Surface (// 실질적인 바깥 껍데기
            color = GraySemiLight,
            modifier = Modifier
                .background(DefaultBackgroundGray)
                .padding(8.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp)
        ){


            Row (// 세로 레이아웃들 싸는거
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Box(// 메뉴 카테고리
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0x00000000),
                                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 0.dp, bottomStart = 10.dp, bottomEnd = 0.dp))

                    ) {
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .padding(5.dp, 5.dp, 0.dp, 0.dp)
                                .background(Gray,
                                    RoundedCornerShape(topStart = 10.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                                .fillMaxWidth()
                                .customBorder(8.dp, bottom = true)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            LargeText3(
                                "메뉴 카테고리", color = YKioskColors.TextPrimary
                            )
                        }
                        storeMenuDetail?.let { detail ->
                            LazyColumn(
                                modifier = Modifier
                                    .weight(17f) // 전체 화면에서 왼쪽 비율
                                    .fillMaxHeight()
                                    .padding(vertical = 8.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) {
                                items(detail.menuGroupDetailResDtoList) { group ->
                                    // 그룹 버튼 (음식, 음료 등)
                                    CategoryButton(
                                        text = group.menuGroupName,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        // 💡 여기서 클릭한 그룹을 ViewModel의 selectedGroup에 저장!
                                        onGroupSelected(group)
                                    }
                                }
                            }
                        }

                    }

                }

                Box(// 메뉴 항목
                    modifier = Modifier
                        .weight(12f)
                        .background(MenuAreaBackgroundWhite)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxSize()
                                .padding(5.dp, 5.dp, 0.dp, 0.dp)
                                .customBorder(borderWidth = 8.dp, bottom = true)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            LargeText4(text=storeMenuDetail?.storeName ?: "")
                        }

                        Box(
                            modifier = Modifier
                                .weight(17f)
                                .padding(5.dp)
                                .background(MenuAreaBackgroundWhite)
                                .fillMaxSize()
                        ) {
                            MenuDisplaySection(
                                selectedGroup = selectedGroup) { clickedMenu ->
                                selectedMenuForDialog = clickedMenu
                                showDialog = true
                            }

                        }
                    }
                }

                Box(// 담은 메뉴
                    modifier = Modifier
                        .weight(5f)
                        .background(Color.Green)
                        .padding(5.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .background(SoftSand,
                                    shape = RoundedCornerShape(16.dp))
                                .fillMaxSize()
                                .padding(bottom = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LargeText2(
                                "담은 메뉴"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(17f)
                                .background(ChoicedBackground)
                                .fillMaxSize()
                        ) {
                            CartSection(
                                cartList = cartList,
                                onQuantityChange = onUpdateQuantity
                            )
                        }
                    }
                }
            }

            if (showDialog && selectedMenuForDialog != null) {
                MenuOptionDialog(
                    menu = selectedMenuForDialog!!,
                    onDismiss = {
                        showDialog = false
                        selectedMenuForDialog = null // 상태 초기화
                    },
                    onConfirm = { count, options ->
                        // 💡 1. 장바구니 객체 생성
                        val newCartItem = CartItem(
                            menu = selectedMenuForDialog!!,
                            quantity = count,
                            selectedOptions = options
                        )

                        // 💡 2. ViewModel의 장바구니에 추가
                        onAddToCart(newCartItem)

                        // 💡 3. 팝업 닫기 및 상태 초기화
                        showDialog = false
                        selectedMenuForDialog = null
                    }
                )
            }
        }
    }
}

@Composable
fun MenuDisplaySection(
    selectedGroup: MenuGroupDetailResponse?, // 왼쪽에서 선택된 '음식' 또는 '음료' 그룹
    onMenuSelected: (MenuDetailResponse) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // --- [상단] 선택된 그룹 내의 '카테고리' 버튼들 (weight 2) ---
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .padding(10.dp, 0.dp)
                .customBorder(borderWidth = 8.dp, borderColor = Dark3, bottom = true)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 선택된 그룹 안에 있는 카테고리 리스트를 버튼으로 만듦
                selectedGroup?.menuCategoryDetailResDtoList?.let { categories ->
                    items(categories) { category ->
                        // 여기서는 CategoryButton 또는 기존에 만든 GroupButton을 재사용
                        GroupButton(
                            text = category.menuCategoryName, // '커피', '차' 등
                            isSelected = false, // 필요 시 카테고리별 필터링 상태 추가
                            modifier = Modifier.padding(horizontal = 5.dp)
                        ) {
                            // 클릭 시 해당 카테고리 위치로 스크롤하는 로직 등을 넣을 수 있음
                        }
                    }
                }
            }
        }

        // --- [하단] 그룹 내 모든 메뉴 리스트 (weight 18) ---
        Box(
            modifier = Modifier
                .weight(18f)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            if (selectedGroup != null) {
                // 핵심: 카테고리 상관없이 그룹 내 모든 메뉴를 하나로 합침 (flatMap)
                val allMenusInGroup = selectedGroup.menuCategoryDetailResDtoList.flatMap { it.menuDetailResDtoList }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(allMenusInGroup) { menu ->
                        MenuItemCard(menu = menu) {
                            println("${menu.menuName} 클릭")
                            onMenuSelected(menu)
                        }
                    }
                }
            } else {
                Text("그룹을 선택해주세요.", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


@Composable
fun MenuItemCard(
    menu: MenuDetailResponse,
    onClick: () -> Unit
) {
    // 카드 전체의 틀
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. 상단: 메뉴 사진
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // 사진을 1:1 정사각형으로 고정
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            AsyncImage(
                model = menu.imageUrl,
                contentDescription = menu.menuName,
                contentScale = ContentScale.Crop, // 이미지가 꽉 차게 자르기
                modifier = Modifier.fillMaxSize(),
                // 이미지 로딩 중이나 실패 시 보여줄 기본 배경색
                placeholder = ColorPainter(DawnMist),
                error = ColorPainter(DawnMist)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2. 하단: 메뉴 이름
        Text(
            text = menu.menuName,
            style = MaterialTheme.typography.titleMedium, // 또는 LargeText2 같은 커스텀 텍스트
            color = Dark1,
            maxLines = 2, // 이름이 길면 2줄까지
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MenuOptionDialog(
    menu: MenuDetailResponse,
    onDismiss: () -> Unit,
    onConfirm: (Int, Map<Int, List<OptionCategoryDetailResponse>>) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    // 💡 Key: optionId (5층), Value: 선택된 6층 객체들의 리스트
    val selectedOptionsMap = remember {
        mutableStateMapOf<Int, List<OptionCategoryDetailResponse>>()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.width(600.dp).fillMaxHeight(0.85f)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp) // 이미지 높이 고정
                        .clip(RoundedCornerShape(16.dp)) // 이미지 자체를 둥글게
                        .background(DawnMist) // 로딩 전 배경색
                ) {
                    AsyncImage(
                        model = menu.imageUrl, // DTO의 이미지 URL 사용
                        contentDescription = menu.menuName,
                        contentScale = ContentScale.Crop, // 영역에 꽉 차게
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))


                // 상단 메뉴 정보
                Text(text = menu.menuName, style = MaterialTheme.typography.headlineMedium, color = YKioskColors.TextSecondary)
                Text(text = menu.menuInfo ?: "", style = MaterialTheme.typography.headlineSmall, color = YKioskColors.TextSecondary)
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    // 💡 5층: MenuOptionDetailResponse 반복
                    items(menu.menuOptionDetailResDtoList) { optionGroup ->
                        Text(
                            text = "${optionGroup.optionName} (최대 ${optionGroup.selectionNum}개 선택)",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = YKioskColors.TextPrimary
                        )

                        // 💡 6층: OptionCategoryDetailResponse (실제 선택지들)
                        // 유연한 배치를 위해 FlowRow 또는 Row 사용
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            optionGroup.optionCategoryDetailResDtoList.forEach { category ->
                                val currentSelectedList = selectedOptionsMap[optionGroup.optionId] ?: emptyList()
                                val isSelected = currentSelectedList.any { it.categoryId == category.categoryId }

                                // 옵션 버튼 (칩 형태)
                                OptionChip(
                                    text = category.optionContent,
                                    isSelected = isSelected,
                                    onClick = {
                                        val newList = if (isSelected) {
                                            // 이미 선택된 경우: 제거
                                            currentSelectedList.filter { it.categoryId != category.categoryId }
                                        } else {
                                            // 새로 선택하는 경우 로직
                                            if (optionGroup.selectionNum == 1) {
                                                // 1. 최대 1개인 경우 (교체)
                                                listOf(category)
                                            } else if (currentSelectedList.size < optionGroup.selectionNum) {
                                                // 2. 최대 개수 미만인 경우 (추가)
                                                currentSelectedList + category
                                            } else {
                                                // 3. 최대 개수 초과 (무시)
                                                currentSelectedList
                                            }
                                        }
                                        selectedOptionsMap[optionGroup.optionId] = newList
                                    }
                                )
                            }
                        }
                        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }

                // 하단 버튼 영역 (수량 조절 및 담기)
                // ... (수량 조절 로직 생략) ...
                Button(
                    onClick = { onConfirm(quantity, selectedOptionsMap.toMap()) },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Dark1)
                ) {
                    Text("장바구니 담기", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun OptionChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Dark1 else DawnMist, // 선택 시 색상 반전
        border = BorderStroke(1.dp, if (isSelected) Dark1 else Color.LightGray)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (isSelected) Color.White else Dark1,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun CartSection(
    cartList: List<CartItem>,
    onQuantityChange: (CartItem, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(cartList) { item ->
            CartItemRow(item = item, onQuantityChange = onQuantityChange)
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onQuantityChange: (CartItem, Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. 메뉴명 및 옵션 정보 (왼쪽)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.menu.menuName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = YKioskColors.TextSecondary)

                // 선택된 옵션들을 텍스트로 변환 (예: HOT, 샷추가)
                val optionsText = item.selectedOptions.values.flatten().joinToString(", ") { it.optionContent }
                if (optionsText.isNotEmpty()) {
                    Text(text = optionsText, style = MaterialTheme.typography.bodySmall, color = YKioskColors.TextSecondary)
                }
            }

            // 2. 수량 조절 버튼 (오른쪽)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onQuantityChange(item, -1) }) {
                    Icon(
                        imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.RemoveCircleOutline,
                        contentDescription = null,
                        tint = if (item.quantity == 1) Color.Red else Dark1
                    )
                }

                Text(text = "${item.quantity}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 4.dp), color = YKioskColors.TextSecondary)

                IconButton(onClick = { onQuantityChange(item, 1) }) {
                    Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = null, tint = Dark1)
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
fun OrderScreenPrev() {
    var currentSelectedGroup by remember { mutableStateOf(dto.menuGroupDetailResDtoList.firstOrNull()) }
    val cartList = remember { mutableStateListOf<CartItem>() } // 실시간 반영되는 리스트

    Ykiosk_android_testTheme {
        KioskScreenContent(
            storeMenuDetail = dto,
            selectedGroup = currentSelectedGroup,
            cartList = cartList,
            onGroupSelected = { group ->
                // 버튼을 누르면 이 코드가 실행되어 Preview의 상태가 바뀝니다!
                currentSelectedGroup = group
            },
            isLoading = false,
            onAddToCart = { newItem ->
                val existing = cartList.find {
                    it.menu.menuId == newItem.menu.menuId && it.selectedOptions == newItem.selectedOptions
                }
                if (existing != null) {
                    val index = cartList.indexOf(existing)
                    cartList[index] = existing.copy(quantity = existing.quantity + newItem.quantity)
                } else {
                    cartList.add(newItem)
                }
            },onUpdateQuantity = { item, delta ->
                val index = cartList.indexOf(item)
                if (index != -1) {
                    val newQty = item.quantity + delta
                    if (newQty <= 0) cartList.removeAt(index)
                    else cartList[index] = item.copy(quantity = newQty)
                }
            }
        )
    }
}