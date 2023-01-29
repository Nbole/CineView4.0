package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest.invoke() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(Color.Transparent)
                .testTag("loadingDialog")
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    LoadingDialog {}
}
