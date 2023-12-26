package com.qingniu.shouba.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import com.qingniu.shouba.util.BlePermissionCenter
import com.qingniu.shouba.util.ConnectState
import com.qingniu.qnbleotaplugin.ui.MainViewModel
import com.qingniu.qnbleotaplugin.ui.ShowFindView
import com.qingniu.qnbleotaplugin.ui.ShowConnectView
import com.qingniu.qnbleotaplugin.ui.ShowUnitSelectView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BlePermissionCenter.verifyPermissions(this)
        setContent {
            val hasPermission = MainViewModel.hasScanAndConnectPermission.collectAsState()
            if (hasPermission.value){
                val connectState = MainViewModel.curConnectState.collectAsState()
                if (connectState.value == ConnectState.none){
                    ShowUnitSelectView()
                    ShowFindView()
                }else{
                    ShowConnectView()
                }
            }else{
                Text(text = "缺少蓝牙或定位权限")
            }
        }
    }
}