package com.twc.rca.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Sushil on 10-04-2018.
 */

public class PassportUtils {

    public static Bitmap cutTop(Bitmap bitmap) {
        Bitmap cutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cutBitmap);
        Rect srcRect = new Rect(0, bitmap.getHeight() / 2, bitmap.getWidth(), bitmap.getHeight());
        Rect desRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
        return cutBitmap;
    }

    public static Bitmap cutBottom(Bitmap bitmap) {
        Bitmap cutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cutBitmap);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        Rect desRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
        return cutBitmap;
    }

    public static Bitmap getCustomerNameBitmap(Bitmap bitmap) {
        Bitmap cutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        Canvas canvas = new Canvas(cutBitmap);
        Rect desRect = new Rect(0, 0, bitmap.getWidth(), (int) (bitmap.getHeight() / 1.6));
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
        cutBitmap = cutTop(cutBitmap);
        return cutBitmap;
    }

    public static Bitmap getFamilyNameBitmap(Bitmap bitmap) {
        Bitmap cutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cutBitmap);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        Rect desRect = new Rect(0, 0, bitmap.getWidth(), (int) (bitmap.getHeight() / 1.8));
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
        return cutBitmap;
    }

    public static Bitmap getAddressBitmap(Bitmap bitmap) {
        Bitmap cutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cutBitmap);
        Rect srcRect = new Rect(0, (int) (bitmap.getHeight() / 2.3), bitmap.getWidth(), bitmap.getHeight());
        Rect desRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
        return cutBitmap;
    }
}
