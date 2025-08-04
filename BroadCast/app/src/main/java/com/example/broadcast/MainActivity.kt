package com.example.broadcast

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.broadcast.ui.theme.BroadCastTheme

class MainActivity : ComponentActivity() {

    private val aeroplaneModeReceiver = AeroplaneModeReceiver()
    private val testReceiver = TestReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(
            aeroplaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        val filter = IntentFilter("TEST_ACTION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                testReceiver,
                filter,
                Context.RECEIVER_EXPORTED
            )
        }

        enableEdgeToEdge()
        setContent {
            BroadCastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(aeroplaneModeReceiver)
        unregisterReceiver(testReceiver)
    }
}
