package com.qingniu.shouba.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qingniu.qnbleotaplugin.ui.MainViewModel
import com.qingniu.qnplugin.model.QNWeightUnit
import com.qingniu.shouba.ui.widget.SelectConfigItem
import com.qingniu.shouba.ui.widget.TitleBar

class UnitSelectActivity : ComponentActivity() {

    companion object {
        fun getCallIntent(ctx: Context): Intent {
            return Intent(ctx, UnitSelectActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val ctx = LocalContext.current
                    val curUnitWeight = MainViewModel.curWeightUnit.collectAsState()
                    Column(
                        Modifier
                            .background(Color(0xFFF4F3F7))
                            .fillMaxSize()
                    ) {
                        TitleBar(title = "Select Unit")
                        Column(
                            Modifier.padding(top = 20.dp)
                        ) {
                            Text(
                                text = "Weight Unit", fontSize = 16.sp,
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                            )
                            SelectConfigItem(
                                unit = QNWeightUnit.UNIT_KG.toString(),
                                checkState = { QNWeightUnit.UNIT_KG.toString() == curUnitWeight.value.toString() }) {
                                MainViewModel.curWeightUnit.value = QNWeightUnit.UNIT_KG
                            }
                            SelectConfigItem(
                                unit = QNWeightUnit.UNIT_LB.toString(),
                                checkState = { QNWeightUnit.UNIT_LB.toString() == curUnitWeight.value.toString() }) {
                                MainViewModel.curWeightUnit.value = QNWeightUnit.UNIT_LB
                            }
                            SelectConfigItem(
                                unit = QNWeightUnit.UNIT_JIN.toString(),
                                checkState = { QNWeightUnit.UNIT_JIN.toString() == curUnitWeight.value.toString() }) {
                                MainViewModel.curWeightUnit.value = QNWeightUnit.UNIT_JIN
                            }
                            SelectConfigItem(
                                unit = QNWeightUnit.UNIT_ST.toString(),
                                checkState = { QNWeightUnit.UNIT_ST.toString() == curUnitWeight.value.toString() }) {
                                MainViewModel.curWeightUnit.value = QNWeightUnit.UNIT_ST
                            }
                            SelectConfigItem(
                                unit = QNWeightUnit.UNIT_ST_LB.toString(),
                                checkState = { QNWeightUnit.UNIT_ST_LB.toString() == curUnitWeight.value.toString() }) {
                                MainViewModel.curWeightUnit.value = QNWeightUnit.UNIT_ST_LB
                            }
                        }
                    }
                }
        }
    }
}
