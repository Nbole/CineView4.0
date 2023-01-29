package com.example.baseapp.presentation.ui.compose.core

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.baseapp.presentation.ui.compose.theme.DarkDusk

object Paragraphs {

    @Composable
    fun Paragraph(
        @StringRes stringRes: Int,
        paragraphSize: ParagraphSize,
        modifier: Modifier = Modifier,
        textAlign: TextAlign = TextAlign.Start,
        color: Color = DarkDusk,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        Text(
            text = stringResource(id = stringRes),
            style = MaterialTheme.typography.h5,
            textAlign = textAlign,
            fontSize = paragraphSize.size,
            color = color,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun ParagraphLight(
        @StringRes stringRes: Int,
        paragraphSize: ParagraphSize,
        modifier: Modifier = Modifier,
        textAlign: TextAlign = TextAlign.Start,
        color: Color = DarkDusk,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        Text(
            text = stringResource(id = stringRes),
            style = MaterialTheme.typography.h6,
            textAlign = textAlign,
            fontSize = paragraphSize.size,
            color = color,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun ParagraphLight(
        text: String,
        paragraphSize: ParagraphSize,
        modifier: Modifier = Modifier,
        textAlign: TextAlign = TextAlign.Start,
        color: Color = DarkDusk,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            textAlign = textAlign,
            fontSize = paragraphSize.size,
            color = color,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    enum class ParagraphSize(val size: TextUnit) {
        PARAGRAPH_14_SP(14.sp),
        PARAGRAPH_12_SP(12.sp),
        PARAGRAPH_24_SP(24.sp)
    }
}