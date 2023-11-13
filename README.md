## PurnaDI - A Simple Dependency Injection Library
PurnaDI is a lightweight and straightforward dependency injection library created to help you understand the fundamentals of dependency injection in your projects. The name "Purna" signifies completeness and fullness, reflecting the library's aim to provide a comprehensive solution for managing dependencies.

### Features
Simple Configuration: PurnaDI offers a simple and intuitive way to configure and manage dependencies in your application.

Constructor Injection: Dependencies are injected through constructors, promoting clean and modular code.

Singleton Support: PurnaDI supports the creation of singleton instances, ensuring that a single instance of a class is used throughout the application.

### Getting Started

Create a Hilt style module
```
object MyModule : Module {

    fun provideViewModel(myRepository: MyRepository) = MyViewModel(myRepository)

    fun provideMyRepository(myService: MyService) = MyRepository(myService)

    fun provideMyService() = MyService()
}
```

Use it directly to inject fields
```
class MainActivity : ComponentActivity() {

    private val viewModel by inject<MyViewModel>(MyModule)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.sayHelloToActivity()

        setContent {
            // ...
        }
    }
}
```

