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
import com.arjun1194.purnadiexample.di.MyModule
import com.arjun1194.purnadiexample.ui.theme.PurnaDIExampleTheme

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {


    private lateinit var viewModel: MyViewModel
    private lateinit var repository: MyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module = MyModule(applicationContext)
        val di = DependencyProviderImpl(module)

        viewModel = di.get(MyViewModel::class.java)
        repository = di.get(MyRepository::class.java)

        viewModel.sayHelloToActivity()

        setContent {
            PurnaDIExampleTheme {
                // i know this should be collected on lifecycle but this is a demo so it doesn't matter
                val greeting = viewModel.state.collectAsState()
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