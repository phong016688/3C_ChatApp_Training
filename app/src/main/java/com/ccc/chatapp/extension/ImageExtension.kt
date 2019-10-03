package com.ccc.chatapp.extension

import android.widget.ImageView
import com.ccc.chatapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadImageUser(url: String) = Picasso.get().let {
    if (url != "")
        it.load(url)
    else
        it.load(R.drawable.default_avatar)
}
    .error(R.drawable.default_avatar)
    .resize(resources.getDimensionPixelSize(R.dimen.with_image_50dp),resources.getDimensionPixelSize(R.dimen.height_image_50dp))
    .into(this)
