package org.itmodreamteam.myrest.android.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.datetime.LocalDateTime
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo

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
fun bindPrintTime(view: TextView, time: LocalDateTime?) {
    if (time != null) {
        view.text = time.toString()
    }
}

@BindingAdapter("printInterval")
fun bindPrintInterval(view: TextView, reservationInfo: ReservationInfo) {
    val from = reservationInfo.activeFrom
    val until = reservationInfo.activeUntil
    if (from.year == until.year) {
        if (from.month == until.month) {
            if (from.dayOfMonth == until.dayOfMonth) {
                view.text = "${from.year} ${from.month} ${from.dayOfWeek}: ${from.hour}:${from.minute} - ${until.hour}:${until.minute}"
            }
        }
    } else {
        view.text = "$from - $until"
    }
}