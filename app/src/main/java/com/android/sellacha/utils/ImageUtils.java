package com.android.sellacha.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;

public class ImageUtils {
    public static void setDrawableImage(Context context, ImageView imageView, int res) {
        Glide.with(context).load(res)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void setDisabled(ImageView imageView) {
        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);
        imageView.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));
    }


    public static void changeImageColor(ImageView imageView, Context context, int color) {
        imageView.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN);
    }

    public static Drawable changeShapeColor(Context context, int drawable, int color) {
        Drawable mDrawable = ContextCompat.getDrawable(context, drawable);
        mDrawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_IN));
        return mDrawable;
    }

    public static Drawable changeShapeStrokeColor(Context context, View view, int color, int stokeWidth) {
        GradientDrawable mDrawable = (GradientDrawable) view.getBackground();
        mDrawable.setStroke(stokeWidth, color); // set stroke width and stroke color
        return mDrawable;
    }


    public static Bitmap convertImagePathToBitmap(String imagePath, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, new BitmapFactory.Options());
        if (width == 0 || height == 0) {
            return Bitmap.createBitmap(bitmap);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static String convertBitmapIntoBase64(Bitmap bitmap) {
        return Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.NO_WRAP);
    }

    private static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        try (Cursor cursor = context.getContentResolver().query(contentUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null)) {
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            }
            if (cursor != null) {
                cursor.moveToFirst();
            }
            String string = null;
            if (cursor != null) {
                string = cursor.getString(column_index);
            }
            return string;
        }
    }

}
