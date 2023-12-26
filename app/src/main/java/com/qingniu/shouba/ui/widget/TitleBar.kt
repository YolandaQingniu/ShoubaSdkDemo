package com.qingniu.shouba.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *@author: hyr
 *@date: 2022/8/11 16:12
 *@desc:
 */

@Composable
fun TitleBar(title: String) {
    Box(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(text = title, Modifier.align(Alignment.Center), fontSize = 16.sp)
    }
}