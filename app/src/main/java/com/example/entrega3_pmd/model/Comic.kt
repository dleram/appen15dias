package com.example.entrega3_pmd.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Comic (
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val dia: Int
)
