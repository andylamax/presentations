import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import net.kodein.cup.Slide
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

sealed interface Slot {
    val name: String
    val duration: Duration
}

data class Session(
    override val name: String,
    val details: String,
    override val duration: Duration,
) : Slot

data class Break(
    override val name: String,
    override val duration: Duration
) : Slot

private val slots = listOf(
    Break(
        name = "Arrivals",
        duration = 30.minutes,
    ),
    Session(
        name = "Opening Keynote",
        details = "Providing an introductory overview to kotlin, kotlin conf and what went down in Copenhagen",
        duration = 45.minutes
    ),
    Session(
        name = "UI Development with Compose",
        details = "Build amazing user interfaces in a declarative manner",
        duration = 1.hours
    ),
    Break(
        name = "Photos Session",
        duration = 15.minutes,
    ),
    Session(
        name = "Explore Ktor",
        details = "Learn how to write servers and clients in the ktor way",
        duration = 1.hours
    ),
    Session(
        name = "Sarufi",
        details = "Infer business intelligence from employing chatbots in your applications",
        duration = 45.minutes
    ),
    Break(
        name = "Lunch",
        duration = 1.hours
    ),
    Session(
        name = "Kotlin Dev Tools",
        details = "A discussion about what tools other developers employ while building software with kotlin",
        duration = 1.hours
    ),
    Session(
        name = "Simplify",
        details = "A short insight on how Simplitech aids our software community charge into the fintech arena",
        duration = 15.minutes
    ),
    Session(
        name = "Career fair",
        details = "Know your career and its not so obvious opportunities",
        duration = 30.minutes
    ),
    Session(
        name = "Closing Panel",
        details = "Final remarks, questions and giveaways",
        duration = 30.minutes
    )
)

val moment = LocalDateTime.Format {
    hour()
    char(':')
    minute()
}

internal val schedule by Slide {
    Column {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
            Text(
                text = "Kotlin Conf Dar es Salaam 2024 Schedule",
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        var time = LocalDateTime(2024, 6, 8, 8, 30)
        var x = 0
        Column {
            while (x < slots.size) {
                val a = slots[x]
                val b = slots.getOrNull(x + 1)
                if (a is Break) Row(
                    modifier = Modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.6f)).padding(vertical = 4.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(time.format(moment)) }
                        appendLine()
                        append(a.name)
                    }
                    Text(text, fontSize = 8.sp, lineHeight = 8.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.onPrimary)
                    time += a.duration
                    x++
                } else if (a is Session && b is Break) Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Session(a, time, modifier = Modifier.weight(1f))
                    time += a.duration
                    x++
                } else if (a is Session && b is Session) Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Session(a, time, modifier = Modifier.weight(1f))
                    time += a.duration
                    Session(b, time, modifier = Modifier.weight(1f).padding(start = 8.dp))
                    time += b.duration
                    x += 2
                } else if (a is Session) {
                    Session(a, time)
                    x++
                }
            }
        }
    }
}

@Composable
fun Session(
    s: Session,
    time: LocalDateTime,
    modifier: Modifier = Modifier
) = Row(modifier) {
    Text(time.format(moment), fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    Spacer(modifier = Modifier.width(8.dp))
    Column {
        Text(s.name, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
        Text(s.details, fontSize = 9.sp, lineHeight = 12.sp)
    }
}

operator fun LocalDateTime.plus(duration: Duration): LocalDateTime {
    if (duration.isInfinite()) throw IllegalArgumentException("Duration should be finite")
    val i = toInstant(TimeZone.UTC) + duration
    return i.toLocalDateTime(TimeZone.UTC)
}