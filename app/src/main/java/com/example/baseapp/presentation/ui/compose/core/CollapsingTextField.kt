package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baseapp.R
import com.example.baseapp.presentation.model.SearchModel
import com.example.baseapp.presentation.ui.compose.theme.Styles
import com.example.baseapp.presentation.ui.compose.theme.Yellow

@Composable
fun CollapsingTextField(
    searchModelProvider: () -> SearchModel,
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
) {
    val searchModel = searchModelProvider.invoke()
    CollapsingLayout(
        scrollProvider = scrollProvider,
        indexProvider = indexProvider,
    ) {
        TextField(
            singleLine = true,
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .clickable(enabled = searchModel.enabled, null, null) {}
                .background(
                    colorResource(
                        if (!searchModel.searchText.isNullOrEmpty()) {
                            R.color.white_ffffff
                        } else {
                            R.color.grey_f6f7fc
                        }
                    )
                )
                .height(50.dp)
                .fillMaxWidth()
                .border(width = 0.dp, color = Yellow, shape = RoundedCornerShape(6.dp)),
            value = searchModel.searchText.orEmpty(),
            onValueChange = {
                searchModel.searchActionable(it)
            },
            label = {
                Paragraphs.ParagraphLight(
                    color = colorResource(
                        id = if (searchModel.errorResource != null) {
                            R.color.red_ff2d31
                        } else {
                            R.color.grey_989fb3
                        }
                    ),
                    text = stringResource(id = searchModel.labelResource),
                    paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_14_SP
                )
            },
            leadingIcon = {
                NonClickableIcon(
                    icon = R.drawable.ic_baseline_search_24,
                    iconColor = colorResource(id = R.color.grey_a2a8bc)
                )
            },
            trailingIcon = {
                Paragraphs.ParagraphLight(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {
                            searchModel.acceptActionable.invoke(searchModel.searchText.orEmpty())
                        },
                    text = stringResource(id = R.string.accept),
                    paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_12_SP,
                    color = colorResource(id = R.color.yellow_fee440)
                )
            },
            colors = Styles.editTextStyle()
        )
    }
}
