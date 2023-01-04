package hu.havasig.betreminder.android.create

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.firestore.DocumentSnapshot
import hu.havasig.betreminder.presenter.KMPBetPresenter
import hu.havasig.betreminder.presenter.KMPUserPresenter
import org.koin.androidx.compose.get

@Preview
@Composable
fun BetCreateScreen(
    betPresenter: KMPBetPresenter = get(),
    userPresenter: KMPUserPresenter = get(),
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var prize by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var friends by remember { mutableStateOf<List<DocumentSnapshot>>(value = emptyList()) }


    LaunchedEffect(Unit) {
        friends = userPresenter.getFriendUserList()
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

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Save")
            }
        }
    }
}
