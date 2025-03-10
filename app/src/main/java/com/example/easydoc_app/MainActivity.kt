package com.example.easydoc_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.easydoc_app.ui.theme.EasyDoc_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyDoc_AppTheme {
                MainScreen(
                    onTaskManagerClick = {
                        startActivity(Intent(this, TaskManagerActivity::class.java))
                    },
                    onKanbanBoardClick = {
                        startActivity(Intent(this, KanbanBoardActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onTaskManagerClick: () -> Unit,
    onKanbanBoardClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Willkommen bei EasyDoc!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onTaskManagerClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Zum Task Manager")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onKanbanBoardClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Zum Kanban Board")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EasyDoc_AppTheme {
        MainScreen(
            onTaskManagerClick = {},
            onKanbanBoardClick = {}
        )
    }
}
