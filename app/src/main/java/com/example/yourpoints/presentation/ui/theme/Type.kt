package com.example.yourpoints.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.yourpoints.R


// Define la familia de fuentes
val bodyFontFamily = FontFamily(
    Font(R.font.lifesavers_extrabold, FontWeight.ExtraBold),
    Font(R.font.lifesavers_bold, FontWeight.Normal),
    Font(R.font.lifesavers_regular, FontWeight.Light),
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.ExtraBold),
    displayMedium = baseline.displayMedium.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Normal),
    displaySmall = baseline.displaySmall.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Light),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.ExtraBold),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Normal),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Light),
    titleLarge = baseline.titleLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.ExtraBold),
    titleMedium = baseline.titleMedium.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Normal),
    titleSmall = baseline.titleSmall.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Light),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.ExtraBold),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Normal),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Light),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.ExtraBold),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Normal),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Light),
)