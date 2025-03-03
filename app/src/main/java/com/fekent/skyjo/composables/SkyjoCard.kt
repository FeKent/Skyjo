package com.fekent.skyjo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.skyjo.engine.Card


fun Modifier.pizzaRadialGradient(mirror: Boolean = false): Modifier = this.drawBehind {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 2

    // Define gradient colors
    var colors = listOf(
        Color.Blue, Color.Cyan, Color.Green, Color.Yellow, Color.Red, Color.Magenta, Color.Blue
    )

    // If mirror is true, swap order of colors
    if (mirror) {
        colors = listOf(
            Color.Yellow,
            Color.Red,
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow
        )
    }

    // Create a Sweep Gradient Shader (circular color transition)
    val shader = Brush.sweepGradient(colors)

    // Apply a Radial Blur effect (soft transition from center outwards)
    drawCircle(
        brush = Brush.radialGradient(
            colors = colors,
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center
    )

    // Overlay with the sweep gradient (pizza slices)
    drawRect(shader)
}


@Composable
fun HiddenCard() {
    Column(
        modifier = Modifier
            .width(60.dp)
            .height(100.dp)
            .fillMaxSize()
            .padding(1.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                .pizzaRadialGradient()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "SKYJO",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.graphicsLayer(rotationZ = -30f)
                )
            }
        }

        Box(
            Modifier
                .weight(1f)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .pizzaRadialGradient(mirror = true)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "SKYJO",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.graphicsLayer(rotationZ = 150f, )
                )
            }
        }
    }

}


@Composable
fun RevealedCard(card: Card) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .height(100.dp)
            .fillMaxSize()
            .background(
                when {
                    card.value in -2..-1 -> Color.Blue
                    card.value == 0 -> Color.Cyan
                    card.value in 1..4 -> Color.Green
                    card.value in 5..8 -> Color.Yellow
                    else -> Color.Red
                }, RoundedCornerShape(4.dp)
            )
            .padding(1.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(percent = 50)) // Oval shape
                    .padding(horizontal = 6.dp) // Adjust oval stretch
            ) {
                Text(card.value.toString(), color = Color.Black)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp) // Ensures consistent circle size
                    .background(Color.White, shape = CircleShape) // Circular shape
                    .padding(4.dp), // Adjust padding to center text properly
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = card.value.toString(),
                    fontSize = 28.sp,
                    color = Color.Black,
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(percent = 50)) // Oval shape
                    .padding(horizontal = 6.dp) // Adjust oval stretch
            ) {
                Text(
                    card.value.toString(),
                    modifier = Modifier.graphicsLayer(rotationZ = 180f),
                )
            }
        }
    }
}

@Composable
fun CardWithLabel(index: Int, card: Card) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp)) {
        Text(
            "Card ${index + 1}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        ) // Label the card
        RevealedCard(card) // Display the actual card
    }
}

@Preview(showBackground = true)
@Composable
private fun HiddenCardPreview() {
    HiddenCard()
}


@Preview
@Composable
private fun CardPreview() {
    RevealedCard(Card(12))
}


//@Preview(showBackground = true)
//@Composable
//private fun RevealedBoardPreview() {
//    val players = listOf(
//        Player(PlayerId("1"), "August"),
//        Player(PlayerId("2"), "Gabi"),
//        Player(PlayerId("3"), "Charlie"),
//        Player(PlayerId("4"), "Snippy"),
//        Player(PlayerId("5"), "Pongo")
//    )
//
//    val playerIds = players.map { it.id }
//    val initialBoards = Rules.deal(playerIds)
//
//    val selectedPlayerId = PlayerId("1") // Change this to preview a different player's board
//
//    val selectedPlayer = players.find { it.id == selectedPlayerId }
//    val board = initialBoards[selectedPlayerId]
//
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("${selectedPlayer?.name}'s Board", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//
//        board?.cards?.chunked(board.cards.size / 4)
//            ?.let { (firstColumn, secondColumn, thirdColum, fourthColumn) ->
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//
//                    Column {
//                        firstColumn.forEachIndexed { index, card ->
//                            CardWithLabel(index, card)
//                        }
//                    }
//
//                    Column {
//                        secondColumn.forEachIndexed { index, card ->
//                            CardWithLabel(
//                                index + firstColumn.size,
//                                card
//                            )
//                        }
//                    }
//                    Column {
//                        thirdColum.forEachIndexed { index, card ->
//                            CardWithLabel(
//                                index + firstColumn.size + secondColumn.size,
//                                card
//                            )
//                        }
//                    }
//                    Column {
//                        fourthColumn.forEachIndexed { index, card ->
//                            CardWithLabel(
//                                index + thirdColum.size + firstColumn.size + secondColumn.size,
//                                card
//                            )
//                        }
//                    }
//                }
//            }
//    }
//}

