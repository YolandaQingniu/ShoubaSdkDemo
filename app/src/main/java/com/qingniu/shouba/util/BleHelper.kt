package com.qingniu.shouba.util

import android.annotation.SuppressLint
import android.util.Log
import com.qingniu.shouba.BaseApplication
import com.qingniu.qnbleotaplugin.ui.MainViewModel
import com.qingniu.qnplugin.QNPlugin
import com.qingniu.qnplugin.inter.QNScanListener
import com.qingniu.qnplugin.model.QNWeightUnit
import com.qingniu.qnscaleplugin.QNScaleData
import com.qingniu.qnscaleplugin.QNScaleDevice
import com.qingniu.qnscaleplugin.QNScalePlugin
import com.qingniu.qnscaleplugin.listener.QNScaleDataListener
import com.qingniu.qnscaleplugin.listener.QNScaleDeviceListener
import com.qingniu.qnscaleplugin.listener.QNScaleStatusListener


/**
 *@author: hyr
 *@date: 2023/8/22 15:00
 *@desc:
 */
object BleHelper {
    private val TAG = "BleHelper"

    private val context by lazy { BaseApplication.instance }

    private val mDeviceListener = object :QNScaleDeviceListener{
        override fun onDiscoverScaleDevice(device: QNScaleDevice) {
            val findList = MainViewModel.mFindDevices.value.toMutableList()
            //如果尚未添加到列表中，则加入
            if (!findList.any { it.mac == device.mac }) {
                findList.add(device)
                MainViewModel.mFindDevices.value = findList
            }
        }

        override fun onSetUnitResult(code: Int, device: QNScaleDevice) {

        }
    }

    private val mStatusListener= object : QNScaleStatusListener{
        override fun onConnectedSuccess(device: QNScaleDevice) {
            MainViewModel.curConnectState.value = ConnectState.connected
        }

        override fun onConnectFail(code: Int, device: QNScaleDevice) {
            MainViewModel.curConnectState.value = ConnectState.disconnected
        }

        override fun onReadyInteractResult(code: Int, device: QNScaleDevice) {

        }

        override fun onDisconnected(device: QNScaleDevice) {
            MainViewModel.curConnectState.value = ConnectState.disconnected
        }
    }

    private val mDataListener = object :QNScaleDataListener{
        override fun onRealTimeWeight(weight: String, device: QNScaleDevice) {
            MainViewModel.curWeight.value = weight
        }

        override fun onReceiveMeasureResult(scaleData: QNScaleData, device: QNScaleDevice) {
            MainViewModel.curData.value = scaleData
            QNScalePlugin.cancelConnectDevice(device)
        }

        override fun onReceiveStoredData(
            storedDataList: MutableList<QNScaleData>,
            device: QNScaleDevice
        ) {
            MainViewModel.curStorageDataList.value = storedDataList.toMutableList()
        }
    }

    private val mScanListener = object :QNScanListener{
        override fun onStartScan() {
            MainViewModel.isScanning.value = true
        }

        override fun onStopScan() {
            MainViewModel.isScanning.value = false
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        QNPlugin.getInstance(context).startScan()
    }

    fun stopScan(){
        QNPlugin.getInstance(context).stopScan()
    }

    fun initQNPlugin(){
        QNPlugin.getInstance(context).setScanListener(mScanListener)
        QNPlugin.getInstance(context).setLogListener {
            Log.e("SDK日志", it)
        }
    }

    fun initQNScalePlugin(){
        QNScalePlugin.setScalePlugin(QNPlugin.getInstance(context))
        QNScalePlugin.setDeviceListener(mDeviceListener)
        QNScalePlugin.setStatusListener(mStatusListener)
        QNScalePlugin.setDataListener(mDataListener)
    }

    fun getWeightShouStr(weightKg: String):String {
        return when(MainViewModel.curWeightUnit.value){
            QNWeightUnit.UNIT_KG -> "$weightKg KG"
            QNWeightUnit.UNIT_LB -> "${QNScalePlugin.getWeightLb(weightKg)} LB"
            QNWeightUnit.UNIT_JIN -> "${QNScalePlugin.getWeightJin(weightKg)} 斤"
            QNWeightUnit.UNIT_ST_LB -> "${QNScalePlugin.getWeightStLb(weightKg)[0]} ST ${QNScalePlugin.getWeightStLb(weightKg)[1]} LB"
            QNWeightUnit.UNIT_ST -> "${QNScalePlugin.getWeightSt(weightKg)} ST"
        }
    }
}