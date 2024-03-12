package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayTag(tagName: String, color: Int) {
    Surface(
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(50.dp),
        color = Color(color)
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(
                text = "#",
                color = Color.White,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = tagName,
                color = Color.White,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}