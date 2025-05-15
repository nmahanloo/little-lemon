// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
fun loadScaledBitmap(@DrawableRes resId: Int, reqWidth: Int, reqHeight: Int): ImageBitmap {
    val context = LocalContext.current
    return loadScaledBitmapInternal(context, resId, reqWidth, reqHeight)
}

fun loadScaledBitmapInternal(context: Context, @DrawableRes resId: Int, reqWidth: Int, reqHeight: Int): ImageBitmap {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    BitmapFactory.decodeResource(context.resources, resId, options)

    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
    options.inJustDecodeBounds = false

    val bitmap = BitmapFactory.decodeResource(context.resources, resId, options)
    return bitmap.asImageBitmap()
}

fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val (height: Int, width: Int) = options.outHeight to options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}
