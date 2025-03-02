package com.fekent.skyjo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.skyjo.engine.Card

@Composable
fun RevealedCard(card: Card) {

    Column(
        modifier = Modifier
            .width(50.dp)
            .height(100.dp)
            .fillMaxSize()
            .background(
                when {
                    card.value in -2..-1 -> Color.Blue
                    card.value == 0 -> Color.Cyan
                    card.value in 1..4 -> Color.Green
                    card.value in 5..8 -> Color.Yellow
                    else -> Color.Red
                }
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 4.dp),horizontalArrangement = Arrangement.Start) {
            Text(card.value.toString())
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(card.value.toString(), fontSize = 40.sp)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(end = 4.dp),horizontalArrangement = Arrangement.End) {
            Text(
                card.value.toString(),
                modifier = Modifier.graphicsLayer(rotationZ = 180f),

            )
        }
    }

}

@Preview
@Composable
private fun CardPreview() {
    RevealedCard(Card(2))
}