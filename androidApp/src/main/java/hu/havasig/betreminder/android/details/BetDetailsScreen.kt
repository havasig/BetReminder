package hu.havasig.betreminder.android.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.presenter.KMPBetPresenter
import hu.havasig.betreminder.presenter.KMPUserPresenter
import org.koin.androidx.compose.get

@Composable
fun BetDetailScreen(
    navController: NavController,
    betId: String?,
    betPresenter: KMPBetPresenter = get(),
    userPresenter: KMPUserPresenter = get(),
) {

    var bet by remember { mutableStateOf<DocumentSnapshot?>(value = null) }

    LaunchedEffect(Unit) {
        bet = betPresenter.getBetById(betId ?: "")
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(bet?.android?.data?.get("title")?.toString() ?: "Bet details") }) },
        bottomBar = { BottomAppBar(backgroundColor = Color.Green) { Text("BottomAppBar") } }
    ) {

        bet?.android?.data?.let { betObject ->

            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Participants: ")
                    (betObject["participants"] as (List<Map<String, Object>>)).forEach {
                        Text(text = it["name"].toString())
                    }
                }
                Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
                RowItem(title = "Description:", text = betObject["description"].toString())
                Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
                RowItem(title = "Prize:", text = betObject["prize"].toString())
                Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
                RowItem(title = "Date:", text = betObject["date"].toString())
                Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
                RowItem(title = "Deadline:", text = betObject["deadline"].toString())
                Text(text = "TODO: Státusznak megfelelő footer: waiting for response, i won/lost, accept/decline")
            }
        } ?: Text(text = "Loading...")
    }
}

@Composable
fun RowItem(title: String, text: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Text(text = text)
    }
}