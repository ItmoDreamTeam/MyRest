package org.itmodreamteam.myrest.android.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("printTime")
fun bindPrintTime(view: TextInputEditText, time: LocalDateTime?) {
    if (time != null) {
        view.setText(time.toJavaLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm")))
    }
}

@BindingAdapter("printDate")
fun bindPrintDate(view: TextInputEditText, time: LocalDateTime?) {
    if (time != null) {
        view.setText(time.toJavaLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    }
}

@BindingAdapter("printTableNumber")
fun bindPrintTableNumber(view: Chip, table: Int?) {
    if (table != null) {
        view.text = "Столик #${table}"
    }
}

@BindingAdapter("printNumberOfSeats")
fun bindPrintNumberOfSeats(view: Chip, numberOfSeats: Int?) {
    if (numberOfSeats != null) {
        view.text = "Мест: ${numberOfSeats}"
    }
}