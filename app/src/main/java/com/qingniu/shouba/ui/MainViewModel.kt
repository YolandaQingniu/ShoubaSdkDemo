package com.qingniu.qnbleotaplugin.ui

import androidx.lifecycle.ViewModel
import com.qingniu.shouba.util.ConnectState
import com.qingniu.qnscaleplugin.QNScaleData
import com.qingniu.qnscaleplugin.QNScaleDevice
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *@author: hyr
 *@date: 2023/8/22 14:09
 *@desc:
 */
object MainViewModel : ViewModel() {

    val hasScanAndConnectPermission: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isScanning: MutableStateFlow<Boolean> = MutableStateFlow(false)

    /**
     * 已发现的设备
     */
    val mFindDevices: MutableStateFlow<MutableList<QNScaleDevice>> = MutableStateFlow(mutableListOf())

    /**
     *  准备连接的设备
     */
    val targetDevice: MutableStateFlow<QNScaleDevice?> = MutableStateFlow(null)

    /**
     * 连接状态
     */
    val curConnectState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.none)

    /**
     * 当前不稳定数据
     */
    var curWeight: MutableStateFlow<String?> = MutableStateFlow(null)

    /**
     * 当前测量完成数据
     */
    val curData: MutableStateFlow<QNScaleData?> = MutableStateFlow(null)

    fun clear(){
        curConnectState.value = ConnectState.none
        curWeight.value = null
        curData.value = null
    }
}