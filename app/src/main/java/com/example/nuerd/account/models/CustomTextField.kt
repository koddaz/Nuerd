package com.example.nuerd.account.models

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nuerd.ui.theme.borderColor
import com.example.nuerd.ui.theme.cursorColor
import com.example.nuerd.ui.theme.errorColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    visual: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        var text by remember { mutableStateOf(value) }
        TextField(
            value = text,
            visualTransformation = visual,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            label = { Text(label, color = colorScheme.onSecondary) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorScheme.secondary,
                disabledTextColor = Color.Gray,
                cursorColor = cursorColor,
                errorCursorColor = errorColor,
                focusedIndicatorColor = colorScheme.secondaryContainer,
                unfocusedIndicatorColor = colorScheme.surface,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = colorScheme.error
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = colorScheme.onPrimary, shape = RoundedCornerShape(16.dp))
        )
    }
}