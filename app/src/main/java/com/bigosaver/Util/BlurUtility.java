package com.bigosaver.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bigosavers.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import simplifii.framework.utility.AppConstants;

/**
 * Created by Dhillon on 08-12-2016.
 */

public class BlurUtility extends AsyncTask {
    private ImageView ivBackground;
    private String image;
    private static final float BLUR_RADIUS = 25f;
    private Context context;

    public BlurUtility(ImageView ivBackground, String image, Activity activity) {
        this.ivBackground = ivBackground;
        this.image = image;
        context = activity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Bitmap bitmap = null;
        Bitmap blurredBitmap = null;
        if (!TextUtils.isEmpty(image)) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(AppConstants.PAGE_URL.PHOTO_URL + image).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    blurredBitmap = blur(bitmap);
                } catch (Exception c) {
                    blurredBitmap = null;
                }
            }
        }
//        else {
//            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.dummy_profile);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                blurredBitmap = blur(bitmap);
//            }
//        }
        return blurredBitmap;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (null != o)
            ivBackground.setImageBitmap((Bitmap) o);
        else
            ivBackground.setImageResource(R.mipmap.dummy_background);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(context);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

}
