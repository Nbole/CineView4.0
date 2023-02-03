package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.baseapp.R

@Composable
fun TopBarComposable(scrollProvider: () -> Int, indexProvider: () -> Int) {
    CollapsingToolBar(
        scrollProvider = { scrollProvider() },
        indexProvider = { indexProvider() },
        defaultSize = 100.dp
    ) {
        Box {
            Paragraphs.Paragraph(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                stringRes = R.string.favourite,
                color = Color.White,
                paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_24_SP
            )
        }
    }
}
