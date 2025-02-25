package com.example.nuerd.models

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.nuerd.R


object CustomFont {
    // [START android_compose_text_df_provider]
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    // [END android_compose_text_df_provider]

    val fontName = GoogleFont("pressstart2p_regular")

    val press2Play = FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
            // weight = FontWeight.Bold,
            // style = FontStyle.Italic
        )
    )
}
