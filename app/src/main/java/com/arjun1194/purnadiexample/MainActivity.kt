package com.arjun1194.purnadiexample

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arjun1194.purna.DependencyProviderImpl
import com.arjun1194.purna.inject
import com.arjun1194.purnadiexample.di.MyModule
import com.arjun1194.purnadiexample.ui.theme.PurnaDIExampleTheme

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    private val viewModel by inject<MyViewModel>(MyModule)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.sayHelloToActivity()

        setContent {
            PurnaDIExampleTheme {
                val greeting = viewModel.state.collectAsState() // demo hai doesn't matter
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(greeting.value ?: "")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PurnaDIExampleTheme {
        Greeting("Android")
    }
}