package com.example.ykiosk_android_test.Item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ykiosk_android_test.DTO.response.StoreResponse
import com.example.ykiosk_android_test.ui.theme.YKioskColors

@Composable
fun StoreItemRow(store: StoreResponse) {
    // 가로로 긴 레이아웃 구성
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
            .padding(20.dp), // 안쪽 여백
        horizontalArrangement = Arrangement.SpaceBetween, // 이름은 왼쪽, 상태는 오른쪽에 배치
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 가게 이름 표시
        Text(
            text = store.storeName,
            style = MaterialTheme.typography.titleMedium,
            color = YKioskColors.TextPrimary
        )

        // 가게 상태 표시 (영업중/영업종료 등)
        StoreStateBadge(store.state)
    }
}

@Composable
fun StoreStateBadge(state: String) {
    val badgeColor = when (state) {
        "OPEN" -> Color(0xFF4CAF50)      // 초록색 (영업중)
        "CLOSED" -> Color(0xFFF44336)    // 빨간색 (영업종료)
        else -> Color.Gray               // 회색 (기타)
    }

    Surface(
        color = badgeColor.copy(alpha = 0.1f), // 배경은 연하게
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, badgeColor) // 테두리는 진하게
    ) {
        Text(
            text = if (state == "OPEN") "영업 중" else "영업 종료",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = badgeColor,
            fontWeight = FontWeight.Bold
        )
    }
}