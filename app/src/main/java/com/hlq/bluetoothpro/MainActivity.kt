package com.hlq.bluetoothpro

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hlq.bluetoothpro.receiver.BluetoothReceiver
import com.hlq.bluetoothpro.utils.*

class MainActivity : AppCompatActivity() {

    /**
     * 蓝牙监听 行车模式
     */
    private var mBluetoothFilter: IntentFilter? = null
    private var mBluetoothReceiver: BluetoothReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleBluetooth()
    }

    private fun handleBluetooth() {
        // 验证当前设备是否支持蓝牙 支持便进行初始化
        if (!checkBluetoothEnable()) {
            Log.e("HLQ", "----> 当前设备支持蓝牙")
            initBluetooth()
            if (checkBluetoothStateEnable() && hasBluetoothAudioDevice()) { // 蓝牙已打开 且 已连接
                Log.e("HLQ", "----> 蓝牙已打开且已连接")
                Log.e("HLQ", "----> 输出已配对成功蓝牙列表")
                Log.e("HLQ", "----> ${fetchAlReadyConnection()}")
                Log.e("HLQ", "----> 当前连接蓝牙名称：${getConnectedBtDevice()}")
            }
        }
    }

    /**
     * 初始化行车模式 蓝牙监听
     */
    private fun initBluetooth() {
        if (mBluetoothReceiver == null) {
            mBluetoothReceiver = BluetoothReceiver()
        }
        if (mBluetoothFilter == null) {
            mBluetoothFilter = BluetoothReceiver.registerIntentFilter()
        }
        if (mBluetoothReceiver != null && mBluetoothFilter != null) {
            registerReceiver(mBluetoothReceiver, mBluetoothFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 清理蓝牙广播
        if (mBluetoothReceiver != null) {
            unregisterReceiver(mBluetoothReceiver)
            mBluetoothReceiver = null
        }
    }

}