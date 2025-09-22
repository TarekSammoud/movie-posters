package fr.eseo.ld.ts.movieposters

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.eseo.ld.ts.movieposters.ui.theme.MoviePostersTheme
import fr.eseo.ld.ts.movieposters.ui.theme.MovieSearchScreen

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            Log.d("lifecycle", "onCreate Started")
            enableEdgeToEdge()
            setContent {
                MoviePostersTheme {
                    MovieSearchScreen()
                }
            }
        }


}

