import androidx.compose.material.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import net.kodein.cup.Slide
import utils.SidePanel
import utils.candidates

private val mentioned = candidates("Kotlin")

internal val languages by Slide {
    Text("Languages", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    SidePanel(mentioned)
}