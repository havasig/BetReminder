package hu.havasig.betreminder.android.create

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.android.navigation.Screens
import hu.havasig.betreminder.data.Participant
import hu.havasig.betreminder.presenter.KMPBetPresenter
import hu.havasig.betreminder.presenter.KMPUserPresenter
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.get

@Composable
fun BetCreateScreen(
    navController: NavController,
    betPresenter: KMPBetPresenter = get(),
    userPresenter: KMPUserPresenter = get(),
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var prize by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var friends by remember { mutableStateOf<List<DocumentSnapshot>>(value = emptyList()) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentUser by remember { mutableStateOf<DocumentSnapshot?>(null) }



    LaunchedEffect(Unit) {
        friends = userPresenter.getFriendsOfUser()
        currentUser = userPresenter.getCurrentUser()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("New bet") }) },
        bottomBar = { BottomAppBar(backgroundColor = Color.Green) { Text("BottomAppBar") } }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Title")
                TextField(
                    value = title,
                    onValueChange = { title = it }
                )
            }
            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Prize")
                TextField(
                    value = prize,
                    onValueChange = { prize = it }
                )
            }
            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Description")
                TextField(
                    value = description,
                    onValueChange = { description = it }
                )
            }
            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Player one")
                TextField(
                    value = "You",
                    enabled = false,
                    onValueChange = { }
                )
            }
            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)



            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Red)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Player two")
                Box(
                    modifier = Modifier
                        .width(Dp(200F))
                        .wrapContentHeight()
                ) {
                    if (friends.isEmpty() || friends[selectedIndex].android.data?.get("name") == null) {
                        Text(
                            "", modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { expanded = true })
                                .background(
                                    Color.Gray
                                )
                        )
                    } else {
                        Text(
                            friends[selectedIndex].android.data!!["name"].toString(), modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { expanded = true })
                                .background(Color.Gray)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },

                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.Red
                            )
                    ) {
                        friends.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                expanded = false
                            }) { Text(text = s.android.data?.get("name")?.toString() ?: "nincs") }
                        }
                    }
                }
            }

            Button(onClick = {
                if (title.text.isEmpty()) {
                    Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                } else if (prize.text.isEmpty()) {
                    Toast.makeText(context, "Prize cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                } else if (description.text.isEmpty()) {
                    Toast.makeText(context, "Description cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                } else if (friends.isEmpty() || friends[selectedIndex].android.data?.get("name") == null) {
                    Toast.makeText(context, "Friend must be selected", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                scope.launch {
                    try {
                        betPresenter.createBet(
                            title = title.text,
                            prize = prize.text,
                            deadline = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
                            description = description.text,
                            participants = mutableListOf(

                                Participant(
                                    id = currentUser!!.id, // TODO
                                    inviteAccepted = true,
                                    winner = null,
                                    name = currentUser!!.android.data!!["name"].toString() // TODO
                                ),
                                Participant(
                                    id = friends[selectedIndex].android.data!!["firebaseId"] as String,
                                    inviteAccepted = null,
                                    winner = null,
                                    name = friends[selectedIndex].android.data!!["name"] as String
                                ),
                            )
                        )
                        Toast.makeText(context, "Bet created success", Toast.LENGTH_LONG).show()
                        //betPresenter.loadMyBets(me.id)
                        navController.navigate(Screens.Home.route)
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        Log.e("BET_CREATION_ERROR", e.message ?: "bet creation error")
                    }
                }

            }) {
                Text(text = "Save")
            }
        }
    }
}
