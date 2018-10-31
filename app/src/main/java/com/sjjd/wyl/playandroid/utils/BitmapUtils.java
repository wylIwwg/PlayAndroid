package com.sjjd.wyl.playandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by wyl on 2018/10/31.
 */
public class BitmapUtils {
    public static Bitmap rsBlur(Context context, Bitmap source, int radius) {
        //  int width = Math.round(source.getWidth() * 0.5f);
        //  int height = Math.round(source.getHeight() * 0.5f);
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap inputBmp = Bitmap.createScaledBitmap(source, width, height, false);

        RenderScript renderScript = RenderScript.create(context);


        // Allocate memory for Renderscript to work with

        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);

        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);

        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);


        renderScript.destroy();
        return inputBmp;

    }
}
