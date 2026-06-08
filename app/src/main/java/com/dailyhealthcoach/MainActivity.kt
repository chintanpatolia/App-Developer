package com.dailyhealthcoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dailyhealthcoach.ui.navigation.DailyHealthCoachApp
import com.dailyhealthcoach.ui.theme.DailyHealthCoachTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as DailyHealthCoachApplication).appContainer

        setContent {
            DailyHealthCoachTheme {
                DailyHealthCoachApp(appContainer = appContainer)
            }
        }
    }
}
