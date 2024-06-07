import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import net.kodein.cup.Slide


internal val sections = listOf(
    "Languages",
    "Platforms",
    "Architecture",
    "UI Libraries/Frameworks",
    "Server Databases",
    "Client Databases",
    "Http Server Solutions",
    "Http Client Solutions",
    "Image Loaders",
    "Caching Key/Value Pairs",
    "Build tools",
    "Testing patterns & frameworks",
    "Miscellaneous"
)

internal val introduction by Slide(stepCount = sections.size) { step ->
    Text("Kotlin Dev Tools", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    sections.take(step).chunked(2).forEachIndexed { index, pair ->
        Row(modifier = Modifier.fillMaxWidth()) {
            val a = pair[0]
            val b = pair.getOrNull(1)
            val count = index * 2
            Text("${count + 1}. $a", modifier = Modifier.fillMaxWidth().weight(1f))
            if (b != null) Text("${count + 2}. $b", modifier = Modifier.fillMaxWidth().weight(1f))
        }
    }
}