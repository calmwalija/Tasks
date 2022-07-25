package net.techandgraphics.tasks.vo

import net.techandgraphics.tasks.R
import net.techandgraphics.tasks.model.Category

object Utils {
    val color = listOf(
        R.color.honey_yellow,
        R.color.brown_beige,
        R.color.pastel_violet,
        R.color.grey_beige,
        R.color.pearl_orange,
        R.color.turquoise_green,
        R.color.gentian_blue,
        R.color.pastel_yellow,
        R.color.olive_yellow,
        R.color.orange_brown,
        R.color.pearl_dark_grey,
        R.color.pearl_gold,
        R.color.traffic_purple,
        R.color.patina_green,
        R.color.reed_green,
        R.color.graphite_black,
    )


    private val icon = listOf(
        R.drawable.ic_call,
        R.drawable.ic_email,
        R.drawable.ic_meeting,
         R.drawable.ic_cook,
        R.drawable.ic_eat,
        R.drawable.ic_music,
        R.drawable.ic_video,
        R.drawable.ic_download,
        R.drawable.ic_upload,
        R.drawable.ic_swim,
        R.drawable.ic_sport,
        R.drawable.ic_volleyball,
        R.drawable.ic_basketball,
        R.drawable.ic_soccer,
        R.drawable.ic_book,
        R.drawable.ic_other,
    )


    val category = listOf(
        Category(icon[0], "Call", color[0]),
        Category(icon[1], "Email", color[1]),
        Category(icon[2], "Meeting", color[2]),
         Category(icon[3], "Cook", color[3]),
        Category(icon[4], "Eat", color[4]),
        Category(icon[5], "Music", color[5]),
        Category(icon[6], "Video", color[6]),
        Category(icon[7], "Download", color[7]),
        Category(icon[8], "Upload", color[8]),
        Category(icon[9], "Swim", color[9]),
        Category(icon[10], "Sports", color[10]),
        Category(icon[11], "Volleyball", color[11]),
        Category(icon[12], "Basketball", color[12]),
        Category(icon[13], "Soccer", color[13]),
        Category(icon[14], "Read", color[14]),
        Category(icon[15], "Other", color[15]),
    )

}