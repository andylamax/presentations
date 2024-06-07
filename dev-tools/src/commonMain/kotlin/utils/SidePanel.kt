@file:OptIn(ExperimentalFoundationApi::class)

package utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

data class Candidate(
    var name: String,
    var claps: Int,
    var slaps: Int
)

fun candidates(vararg names: String) = names.map { Candidate(it, 0, 0) }.toMutableList()

@Composable
fun SidePanel(
    source: MutableList<Candidate>,
    modifier: Modifier = Modifier
) {
    val them = remember { mutableStateListOf(*source.toTypedArray()) }

    var votes by remember { mutableStateOf(false) }

    if (votes) Row {
        OutlinedButton(onClick = {
            them.sortByDescending { it.claps }
        }) {
            Text("Sort By Vote")
        }

        OutlinedButton(onClick = {
            them.sortByDescending { it.claps - it.slaps }
        }) {
            Text("Sort By Diff")
        }
    }

    if (!votes) CandidateForm(
        onSave = { name ->
            val exists = them.find { it.name.contentEquals(name) } != null
            if (!exists) {
                val c = Candidate(name = name, 0, 0)
                them.add(0, c)
                source.add(0, c)
            }
        }
    )

    LazyColumn(modifier.heightIn(min = 200.dp).widthIn(min = 200.dp).onClick(onClick = {}, onDoubleClick = { votes = !votes })) {
        items(them, key = { it.name }) {
            Candidate(
                c = it,
                votes = votes,
                onDelete = {
                    them.remove(it)
                    source.remove(it)
                },
                modifier = Modifier.fillParentMaxWidth(0.5f)
            )
        }
    }
}


@Composable
private fun Candidate(
    c: Candidate,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    votes: Boolean = false
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(4.dp)
        .border(
            width = 2.dp,
            color = LocalContentColor.current.copy(0.2f),
            shape = RoundedCornerShape(8.dp)
        ).padding(8.dp)
) {
    var claps by remember { mutableIntStateOf(c.claps) }
    var slaps by remember { mutableIntStateOf(c.slaps) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(c.name)
        Icon(Icons.Outlined.Delete, "Delete ${c.name}", modifier = Modifier.clickable { onDelete() })
    }
    if (votes) Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.onClick(
            onClick = {
                claps++
                c.claps++
            },
            onLongClick = {
                claps--
                c.claps--
            }
        )) {
            Text("$claps")
            Icon(Icons.Outlined.KeyboardArrowUp, "Up vote ${c.name}")
        }
        Row(
            modifier = Modifier.onClick(
                onLongClick = {
                    slaps--
                    c.slaps--
                },
                onClick = {
                    slaps++
                    c.slaps++
                }
            )
        ) {
            Icon(Icons.Outlined.KeyboardArrowDown, "Down vote ${c.name}")
            Text("$slaps")
        }
    }
}

@Composable
private fun CandidateForm(
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier
) = Row(modifier) {
    var name by remember { mutableStateOf("") }
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        singleLine = true,
        modifier = Modifier.onKeyEvent { event ->
            if (event.key != Key.Enter || name.isBlank()) return@onKeyEvent true
            onSave(name.replaceFirstChar { it.uppercaseChar() })
            name = ""
            true
        }
    )
}