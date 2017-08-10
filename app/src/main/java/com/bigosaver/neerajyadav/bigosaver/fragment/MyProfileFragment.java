package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bigosaver.Util.BlurUtility;
import com.bigosaver.Util.ImageUploadTask;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.fragments.MediaFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

/**
 * Created by Neeraj Yadav on 9/28/2016.
 */

public class MyProfileFragment extends BaseFragment {
    private ImageView iv_background, ivProfilePic;
    private LinearLayout llAddr;
    private MediaFragment imagePicker;
    private Bitmap profileBitmap;
    private UpdateProfileResponse profile;
    private static final float BLUR_RADIUS = 25f;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initViews() {
        iv_background = (ImageView) findView(R.id.iv_background);
        llAddr = (LinearLayout) findView(R.id.ll_address);
        ivProfilePic = (ImageView) findView(R.id.iv_profile);
        imagePicker = new MediaFragment();
        getChildFragmentManager().beginTransaction().add(imagePicker, "Image Picker").commit();
        getProfileData();
        setOnClickListener(R.id.iv_profile);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_profile:
                new TedPermission(getActivity())
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                imagePicker.getImage(new MediaFragment.MediaListener() {
                                    @Override
                                    public void setUri(Uri uri, String MediaType) {

                                    }

                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                                    @Override
                                    public void setBitmap(Bitmap bitmap) {
                                        if (bitmap != null) {
                                            profileBitmap = bitmap;
                                            ivProfilePic.setImageBitmap(profileBitmap);
                                            iv_background.setImageBitmap(blur(bitmap));
                                            updateProfilePic(bitmap);
                                        }
                                    }
                                }, getContext());
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            }
                        }).check();

                break;
        }
    }

    private void updateProfilePic(Bitmap bitmap) {
        FileParamObject fileParamObject = null;
        if (bitmap != null) {
            File file = null;
            try {
                file = ImageUploadTask.getFile(bitmap);
                fileParamObject = new FileParamObject(file, file.getName(), "image");
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileParamObject.setUrl(AppConstants.PAGE_URL.UPDATEUSER);
            fileParamObject.setPutMethod();
            String token = Preferences.getData(AppConstants.PREF_KEYS.USER_TOKEN, "");
            if (!TextUtils.isEmpty(token)) {
                fileParamObject.addHeader("Authorization", "Token " + token);
            }
            fileParamObject.setClassType(UpdateProfileResponse.class);
            fileParamObject.addParameter("username", profile.getUsername());
            executeTask(AppConstants.TASKCODES.UPDATEPROFILEPIC, fileParamObject);
        } else {
            showToast(getString(R.string.upload_file_error));
        }
    }

    private void getProfileData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.GETUSER);
        httpParamObject.setClassType(UpdateProfileResponse.class);
        executeTask(AppConstants.TASKCODES.GETUSER, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GETUSER:
                profile = (UpdateProfileResponse) response;
                if (null != profile) {
                    setData(profile);
                }
                break;
            case AppConstants.TASKCODES.UPDATEPROFILEPIC:
                UpdateProfileResponse updatedProfilePic = (UpdateProfileResponse) response;
                if (updatedProfilePic != null) {
                    showToast(getString(R.string.pic_uploaded));
                }
        }
    }

    private void setData(final UpdateProfileResponse profile) {
        if (!TextUtils.isEmpty(profile.getFirst_name())) {
            if (!TextUtils.isEmpty(profile.getLast_name())) {
                setText(R.id.tv_username, getString(R.string.my_name_is) + profile.getFirst_name() + " " + profile.getLast_name());
            }
            setText(R.id.tv_username, getString(R.string.my_name_is) + profile.getFirst_name());
        }
        StringBuilder addr = new StringBuilder();
        if (!TextUtils.isEmpty(profile.getCity_name())) {
            addr.append(profile.getCity_name()).append(", ");
        }
        if (!TextUtils.isEmpty(profile.getCountry())) {
            addr.append(profile.getCountry());
        }
        if (!TextUtils.isEmpty(addr)) {
            llAddr.setVisibility(View.VISIBLE);
            setText(R.id.tv_address, addr.toString());
        }
        if (!TextUtils.isEmpty(profile.getMembership_type()))
            setText(R.id.tv_membership, profile.getMembership_type().toUpperCase());
        if (profile.getSavings() != null)
            setText(R.id.tv_saving, profile.getSavings().toString());
        if (!TextUtils.isEmpty(profile.getAbout_me()))
            setText(R.id.tv_about, profile.getAbout_me());
        if (!TextUtils.isEmpty(profile.getMembership_expired_at()))
            setText(R.id.tv_membership_valdity,
                    Util.convertDateFormat(profile.getMembership_expired_at(),"yyyy-MM-dd","dd-MMM-yyyy").replace("-"," "));
        if (!TextUtils.isEmpty(profile.getImage()))
            Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + profile.getImage()).
                    placeholder(R.mipmap.dummy_profile).into(ivProfilePic);
        BlurUtility blurUtility = new BlurUtility(iv_background, profile.getImage(), getActivity());
        blurUtility.execute();
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_my_profile;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(getActivity());
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
