package com.specialschool.schoolapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.specialschool.schoolapp.R

@BindingAdapter(value = ["imageUri", "placeholder"], requireAll = false)
fun imageUri(imageView: ImageView, imageUri: Uri?, placeholder: Drawable?) {
    when (imageUri) {
        null -> {
            Glide.with(imageView)
                .load(placeholder)
                .into(imageView)
        }
        else -> {
            Glide.with(imageView)
                .load(imageUri)
                .apply(RequestOptions().placeholder(placeholder))
                .into(imageView)
        }
    }
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("websiteLink")
fun websiteLink(button: View, url: String) {
    button.isVisible = true
    button.setOnClickListener {
        openWebsiteUri(it.context, Uri.parse(url))
    }
}

fun openWebsiteUri(context: Context, uri: Uri) {
    if (context.isChromeCustomTabsSupported()) {
        CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            .build()
            .launchUrl(context, uri)
    } else {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}

@SuppressLint("QueryPermissionsNeeded")
private fun Context.isChromeCustomTabsSupported(): Boolean {
    val serviceIntent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
    serviceIntent.setPackage("com.android.chrome")
    val resolveInfos = packageManager.queryIntentServices(serviceIntent, 0)
    return !resolveInfos.isNullOrEmpty()
}
