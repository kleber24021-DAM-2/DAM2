package com.quevedo.footballvideos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.quevedo.footballvideos.ui.theme.FootballVideosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(name = "Andre")
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(name :String){
    Text(text = "Hello $name!")
}

@Composable
fun MessageCard(msg:Message){
    Text(text = msg.author)
    Text(text = msg.body)
}

@Preview
@Composable
fun PreviewMessageCard(){
    MessageCard(name = "Android")
}