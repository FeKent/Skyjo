package com.fekent.skyjo.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fekent.skyjo.engine.Card
import com.fekent.skyjo.engine.Player
import com.fekent.skyjo.engine.PlayerId
import com.fekent.skyjo.engine.Rules

@Composable
fun FlipCard(hiddenContent: @Composable () -> Unit, revealedContent: @Composable () -> Unit) {
    var isFlipped by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .graphicsLayer(
                rotationY = if (isFlipped) 180f else 0f, // Flip the card along the Y-axis
                cameraDistance = 12f // To add depth to the flip
            )
            .clickable {
                isFlipped = !isFlipped // Toggle flip state on click
            }
            .padding(4.dp) // Optional padding for styling
    ) {
        // Front side (hidden content)
        if (!isFlipped) {
            hiddenContent() // Hidden side
        }

        // Back side (revealed content)
        if (isFlipped) {
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        rotationY = 180f // Reverse the rotation of the revealed side
                    )
            ) {
                revealedContent() // Revealed side
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlipCardPreview() {
    FlipCard(
        hiddenContent = {
            HiddenCard() // This is the hidden side (initial view)
        },
        revealedContent = {
            val card = Card(5) // Example card with value 5
            RevealedCard(card) // This is the revealed side (flipped view)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BoardFlipPreview() {
    val players = listOf(
        Player(PlayerId("1"), "August"),
        Player(PlayerId("2"), "Gabi"),
        Player(PlayerId("3"), "Charlie"),
        Player(PlayerId("4"), "Snippy"),
        Player(PlayerId("5"), "Pongo")
    )

    val playerIds = players.map { it.id }
    val initialBoards = Rules.deal(playerIds)

    val selectedPlayerId = PlayerId("1") // Change this to preview a different player's board

    val board = initialBoards[selectedPlayerId]

    // Display the board of cards
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        board?.cards?.chunked(4)?.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { card ->
                    FlipCard(
                        hiddenContent = {
                            HiddenCard() // Pass the card to the hidden side
                        },
                        revealedContent = {
                            RevealedCard(card) // Pass the card to the revealed side
                        }
                    )
                }
            }
        }
    }
}