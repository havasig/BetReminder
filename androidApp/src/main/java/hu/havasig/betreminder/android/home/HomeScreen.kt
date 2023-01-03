package hu.havasig.betreminder.android.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.android.navigation.Screens
import hu.havasig.betreminder.presenter.KMPBetPresenter
import org.koin.androidx.compose.get
import java.text.SimpleDateFormat

@Composable
fun HomeScreen(
    navController: NavController,
    betPresenter: KMPBetPresenter = get()
) {

    val bets = produceState(
        initialValue = emptyList<DocumentSnapshot>(),
        producer = {
            value = betPresenter.getBets("iFEFVKFU77OhopXp1sjL")
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            items(items = bets.value, itemContent = { bet ->
                bet.android.data?.let { it1 -> BetRow(bet = it1) }
            })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Home Screen",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                modifier = Modifier.padding(5.dp),
                onClick = {
                    // Navigate to Details
                    navController.navigate(Screens.Detail.route)
                }
            ) {
                Text(
                    text = "Go to Details",
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

@Composable
fun BetRow(
    bet: MutableMap<String, Any>
) {
    Column(modifier = Modifier.wrapContentHeight()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {

                Text(text = bet["title"].toString())

                // Row { Text(text = bet["participants"].toString()) }
                val date = (bet["date"] as Timestamp).toDate()
                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val formattedDate = sdf.format(date).toString()


                Text(text = formattedDate)
                Text(text = bet["description"] as String)
            }

            Button(onClick = { /*TODO*/ }) {

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