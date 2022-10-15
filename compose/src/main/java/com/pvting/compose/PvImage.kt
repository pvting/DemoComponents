package com.pvting.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PvImage() {
    Image(painter = painterResource(id = R.mipmap.ic_launcher), contentDescription = null, contentScale = ContentScale.FillHeight)
}