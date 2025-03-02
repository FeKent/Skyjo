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
                }
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

@Preview
@Composable
private fun CardPreview() {
    RevealedCard(Card(12))
}