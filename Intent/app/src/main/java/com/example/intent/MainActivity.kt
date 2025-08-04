package com.example.intent

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.intent.ui.theme.IntentTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ImageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentTheme {
                viewModel.uri?.let { uri ->
                    AsyncImage(
                        model = viewModel.uri,
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            Intent(applicationContext, SecondActivity::class.java).also {
                                startActivity(it)
                            }
                        }
                    ) {
                        Text("Touch me")
                    }

                    Button(
                        onClick = {
                            sendBroadcast(
                                Intent("TEST_ACTION")
                            )
                        }
                    ) {
                        Text("Send broadcast")
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }

        viewModel.updateUri(uri)
    }
}
