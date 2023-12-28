package com.qingniu.qnbleotaplugin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qingniu.shouba.util.ConnectState
import com.qingniu.qnplugin.QNPlugin
import com.qingniu.qnplugin.model.QNWeightUnit
import com.qingniu.qnscaleplugin.QNScaleOperate
import com.qingniu.qnscaleplugin.QNScalePlugin
import com.qingniu.shouba.ui.UnitSelectActivity
import com.qingniu.shouba.util.BleHelper

/**
 *@author: hyr
 *@date: 2023/8/22 14:18
 *@desc:
 */

@Composable
fun ShowUnitSelectView() {
    Box(
        Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(40.dp)
        //.background(Color.Red)
    ) {
        val context = LocalContext.current
        Text(
            text = "点击设置单位",
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable(onClick = {
                    context.startActivity(UnitSelectActivity.getCallIntent(context))
                }, indication = null, interactionSource = remember { MutableInteractionSource() }),
            textAlign = TextAlign.Center, fontSize = 22.sp
        )
    }
}

@Composable
fun ShowFindView() {
    Box(
        Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
            .height(40.dp)
            //.background(Color.Red)
    ) {
        val context = LocalContext.current
        val isScanning = MainViewModel.isScanning.collectAsState()
        val showText = if (isScanning.value) "点击停止扫描" else "点击开始扫描"
        Text(
            text = showText,
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable(onClick = {
                    if (isScanning.value) {
                        BleHelper.stopScan()
                    } else {
                        MainViewModel.beforeScan()
                        BleHelper.startScan()
                    }
                }, indication = null, interactionSource = remember { MutableInteractionSource() }),
            textAlign = TextAlign.Center, fontSize = 22.sp
        )
    }
    val targetDevice = MainViewModel.targetDevice.collectAsState()
    val context = LocalContext.current
    if (null != targetDevice.value){
        Box(
            Modifier
                .padding(top = 80.dp)
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Gray)
        ) {
            Text(
                text = "点击连接 ${targetDevice.value?.mac}",
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .clickable(
                        onClick = {
                            BleHelper.stopScan()
                            targetDevice.value?.let {
                                QNScalePlugin.connectDevice(it, QNScaleOperate().apply {
                                    unit = MainViewModel.curWeightUnit.value
                                })
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }),
                textAlign = TextAlign.Center, fontSize = 22.sp, color = Color.White
            )
        }
    }
    val findDevices = MainViewModel.mFindDevices.collectAsState()
    if (findDevices.value.isNotEmpty()){
        LazyColumn(
            modifier = Modifier
                .padding(top = 120.dp)
                .fillMaxSize()
        ) {
            item {
                Row(
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "蓝牙名", textAlign = TextAlign.Center)
                    }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "内部型号", textAlign = TextAlign.Center)
                    }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "mac地址", textAlign = TextAlign.Center)
                    }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "存储数据", textAlign = TextAlign.Center)
                    }
                }
            }

            items(findDevices.value.size) {
                val findDevice = findDevices.value[it]
                val isSelected = findDevice.mac == targetDevice.value?.mac
                Row(
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                MainViewModel.targetDevice.value = findDevice
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() })
                        .background(if (isSelected) Color.Green else Color.Transparent), verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "${findDevice.bleName}", textAlign = TextAlign.Center)
                    }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "${findDevice.modelId}", textAlign = TextAlign.Center)
                    }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "${findDevice.mac}", textAlign = TextAlign.Center)
                    }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "${findDevice.storageCount}", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowConnectView() {
    Box(
        Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(40.dp)
    ) {
        val showText = "返回扫描"
        val context = LocalContext.current
        Text(
            text = showText,
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable(onClick = {
                    MainViewModel.targetDevice.value?.let {
                        QNScalePlugin.cancelConnectDevice(it)
                        MainViewModel.clear()
                    }
                }, indication = null, interactionSource = remember { MutableInteractionSource() }),
            textAlign = TextAlign.Center, fontSize = 22.sp
        )
    }
    val connectState = MainViewModel.curConnectState.collectAsState()
    val curWeight = MainViewModel.curWeight.collectAsState()
    val curData = MainViewModel.curData.collectAsState()
    val curStorageList = MainViewModel.curStorageDataList.collectAsState()
    connectState.value.let {

        val tip =if (it == ConnectState.connecting) {
            "连接中"
        } else if (it == ConnectState.connected) {
            "已连接"
        }else{
           "未连接"
        }

        Box(
            Modifier
                .padding(top = 80.dp)
                .fillMaxWidth()
        ) {
            LazyColumn{
                item {
                    Column {
                        Text(text = tip,
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally), textAlign = TextAlign.Center, fontSize = 22.sp)
                        if (curData.value != null){
                            Text(text = "体重 ${BleHelper.getWeightShouStr(curData.value!!.weight)}\n50Hz阻抗 ${curData.value!!.resistance50}\n500Hz阻抗 ${curData.value!!.resistance500}",
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally), textAlign = TextAlign.Center, fontSize = 22.sp)
                        }else if (curWeight.value != null){
                            Text(text = "不稳定体重 ${BleHelper.getWeightShouStr(curWeight.value!!)}",
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally), textAlign = TextAlign.Center, fontSize = 22.sp)
                        }
                        if (curStorageList.value.isNotEmpty()){
                            Text(text = "----以下为存储数据----",
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally), textAlign = TextAlign.Center, fontSize = 22.sp)
                            curStorageList.value.forEach { storageData->
                                Text(text = "体重 ${BleHelper.getWeightShouStr(storageData.weight)}\n50Hz阻抗 ${storageData.resistance50}\n500Hz阻抗 ${storageData.resistance500}",
                                    Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally), textAlign = TextAlign.Center, fontSize = 22.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GreetingPreview() {
    ShowFindView()
}