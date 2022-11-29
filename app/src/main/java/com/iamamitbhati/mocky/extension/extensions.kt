package com.iamamitbhati.mocky.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.iamamitbhati.mocky.R
import com.iamamitbhati.mocky.model.ProductDetail


fun ProductDetail.getFavDrawable(context: Context): Drawable? {
    var drawable = R.drawable.ic_not_favorite
    this.takeIf { it.favorite }?.let {
        drawable = R.drawable.ic_favorite
    }
    return ContextCompat.getDrawable(context, drawable)
}

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible)
        View.VISIBLE
    else
        View.GONE
}

fun AppCompatImageView.setImage(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.placeholder)
        .into(this)
}

fun ActionBar.updateTitle(text: String) {
    setDisplayShowTitleEnabled(true)
    title = text
}