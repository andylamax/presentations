import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.kodein.cup.Presentation
import net.kodein.cup.Slide
import net.kodein.cup.Slides
import net.kodein.cup.cupApplication
import utils.Candidate
import utils.SidePanel

private val candidates = mutableMapOf<String, MutableList<Candidate>>()

private val slides = Slides((sections.reversed().map { section ->
    val slide by Slide {
        Text(section, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        SidePanel(candidates.getOrPut(section) { mutableListOf() })
    }
    slide
} + introduction).reversed())

private fun main() = cupApplication("Developer Tools") {
    Presentation(slides) { content ->
        var light by remember { mutableIntStateOf(1) }
        val colors by remember(light) { derivedStateOf { if (light == 1) lightColors(secondaryVariant = Color(0xFFFF0000)) else darkColors() } }

        MaterialTheme(colors = colors) {
            Box(modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    content()
                }
                Switch(
                    checked = light == 1,
                    onCheckedChange = { light = if (it) 1 else 0 },
                    modifier = Modifier.align(Alignment.TopEnd).padding(end = 16.dp),
                )
            }
        }
    }
}