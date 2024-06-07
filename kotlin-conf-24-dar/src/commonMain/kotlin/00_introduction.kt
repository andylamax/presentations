@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import net.kodein.cup.Slide
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentations.kotlin_conf_24_dar.generated.resources.Res
import presentations.kotlin_conf_24_dar.generated.resources.banner

internal val introduction by Slide {
    Image(painterResource(Res.drawable.banner),"Kotlin Conf banner", modifier = Modifier.fillMaxSize())
}