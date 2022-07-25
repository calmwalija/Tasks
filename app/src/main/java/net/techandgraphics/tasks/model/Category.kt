package net.techandgraphics.tasks.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import net.techandgraphics.tasks.vo.Utils

data class Category(
    @DrawableRes val icon: Int,
    val name: String,
    @ColorRes val backgroundColor: Int = Utils.color[0]
)
