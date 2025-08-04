package com.example.intent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.intent.ui.theme.IntentTheme
import androidx.core.net.toUri

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            IntentTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Second Activity")
                    CustomButton(
                        "YouTube"
                    ) {
                        Intent(Intent.ACTION_MAIN).also {
                            it.`package` = "com.google.android.youtube"
                            try {
                                startActivity(it)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }

                        }
                    }

                    CustomButton("Send Email") {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto: test@test.com")

                            putExtra(Intent.EXTRA_SUBJECT, "Subject of email")
                            putExtra(Intent.EXTRA_TEXT, "content of email")
                        }
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }

                    /*
                    If you're configuring something → apply.
                    If you're doing side things like logging → also.
                     */

                    CustomButton("Send on whatsapp") {
                        val number = "918011743346"
                        val message =
                            "This is a test to send message on whatsapp from my app"
                        val url = "https://wa.me/$number?text=${Uri.encode(message)}"
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = url.toUri()
                            setPackage("com.whatsapp")
                        }
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(text)
    }
}