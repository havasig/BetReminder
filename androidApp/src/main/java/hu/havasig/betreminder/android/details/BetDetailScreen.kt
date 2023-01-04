package hu.havasig.betreminder.android.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.presenter.KMPBetPresenter
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun BetDetailScreen(
    navController: NavController,
    betId: String?,
    betPresenter: KMPBetPresenter = get()
) {

    var bet by remember { mutableStateOf<DocumentSnapshot?>(value = null) }

    LaunchedEffect(Unit) {
        bet = betPresenter.getBetById(betId ?: "")
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Bet details") }) },
        bottomBar = { BottomAppBar(backgroundColor = Color.Green) { Text("BottomAppBar") } }
    ) {

        bet?.run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = betId ?: "Detail Screen",
                        fontSize = MaterialTheme.typography.h3.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}