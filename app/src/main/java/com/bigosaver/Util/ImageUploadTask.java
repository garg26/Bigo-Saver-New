package com.bigosaver.Util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.asyncmanager.FileUploadService;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by Admin on 22-Jul-16.
 */
public class ImageUploadTask extends AsyncTask<Bitmap, Void, String> {
    UploadListener uploadListener;
    private List<Bitmap> bitmapList;
    private List<String> imageUrls = new ArrayList<>();

    public static ImageUploadTask uploadImage(UploadListener uploadListener, List<Bitmap> bitmapList) {
        ImageUploadTask imageUploadTask = new ImageUploadTask();
        imageUploadTask.uploadListener = uploadListener;
        imageUploadTask.bitmapList = bitmapList;
        return imageUploadTask;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        String imageUrl = null;
        try {
            for (Bitmap bMap : bitmapList) {
                imageUrl = uploadFile(bMap);
                if (!TextUtils.isEmpty(imageUrl))
                    imageUrls.add(imageUrl);
            }
        } catch (OutOfMemoryError e) {

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        if (s == null) {
            uploadListener.onUploadFailed();
        } else {
            uploadListener.onUpload(imageUrls);
        }
    }

    private String uploadFile(Bitmap bMap) throws Exception {
        File file = getFile(bMap);
        FileParamObject obj = new FileParamObject(file, file.getName(), "file" );
        obj.setUrl(AppConstants.PAGE_URL.UPDATEUSER);
        obj.addHeader("token", Preferences.getData(AppConstants.PREF_KEYS.USER_TOKEN, ""));
        FileUploadService service = new FileUploadService();
        String response = (String) service.getData(obj);
        if (!TextUtils.isEmpty(response)) {
            JSONObject root = new JSONObject(response);
            if (root.has("response")) {
                JSONArray array = root.getJSONArray("response");
                if (array != null && array.length() > 0) {
                    return array.getString(0);
                }
            }
        }
        return "";
    }


    public static File getFile(Bitmap bMap) throws Exception {
        bMap = getResizedBitmap(bMap);
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Bigo";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, System.currentTimeMillis() + ".png");
        FileOutputStream fOut = new FileOutputStream(file);

        bMap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        return file;
    }

    public static Bitmap getResizedBitmap(Bitmap image) {
        int maxSize = 1024;
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public interface UploadListener {
        void onUpload(List<String> imageUrls);

        void onUploadFailed();
    }
}
