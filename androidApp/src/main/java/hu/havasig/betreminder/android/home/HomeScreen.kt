package hu.havasig.betreminder.android.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.android.BottomNavigation
import hu.havasig.betreminder.android.navigation.Screens
import hu.havasig.betreminder.presenter.KMPBetPresenter
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(
    navController: NavController,
    betPresenter: KMPBetPresenter = get()
) {

    val bets = produceState(
        initialValue = emptyList<DocumentSnapshot>(),
        producer = { value = betPresenter.getBets("iFEFVKFU77OhopXp1sjL") }
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Bets") }) },
        bottomBar = { BottomNavigation(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                    navController.navigate(Screens.Create.route)

                },
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Create bet",
                    )
                }
            )
        },
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(items = bets.value, itemContent = { bet ->
                    BetRow(bet, navController)
                    Divider(modifier = Modifier.padding(horizontal = 8.dp), thickness = 1.dp, color = Color.Black)
                })
            }
        }
    }
}

@Composable
fun BetRow(
    bett: DocumentSnapshot,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val bet = bett.android.data!!
        val participants = bet["participants"] as List<HashMap<String, String>>
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(width = Dp(150F))) {

                Text(text = bet["title"].toString())
                Text(text = participants[0]["name"] + " vs " + participants[1]["name"])
                Text(text = bet["date"].toString())
                Text(text = bet["description"] as String)
            }

            Button(
                onClick = { navController.navigate(Screens.Detail.route + "/" + bett.id) }) {
                Text(
                    text = "Inspect",
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}