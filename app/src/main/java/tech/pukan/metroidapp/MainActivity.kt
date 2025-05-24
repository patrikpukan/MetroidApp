package tech.pukan.metroidapp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Get the dynamic color scheme
            val dynamicColorScheme = dynamicColorScheme(this)

            PragueMetroTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun PragueMetroTheme(
    content: @Composable () -> Unit
) {
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            dynamicDarkColorScheme(context)
        }

        else -> darkColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}