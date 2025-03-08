package com.example.easydoc_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.easydoc_app.ui.theme.EasyDoc_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Statt setContentView(...) verwenden wir Compose:
        setContent {
            EasyDoc_AppTheme {
                // Wir zeigen unsere MainScreen-Composable.
                // Per Lambda regeln wir, was beim Button-Klick passieren soll.
                MainScreen(
                    onTaskManagerClick = {
                        startActivity(Intent(this, TaskManagerActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(onTaskManagerClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Willkommen bei EasyDoc!")
                Spacer(modifier = Modifier.height(20.dp))

                // Compose-Button, der zum Task Manager führt
                Button(onClick = onTaskManagerClick) {
                    Text(text = "Zum Task Manager")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EasyDoc_AppTheme {
        MainScreen {}
    }
}
