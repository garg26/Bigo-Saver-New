package com.bigosaver.user;

import android.app.IntentService;
import android.content.Intent;

import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.asyncmanager.OKHttpService;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;

/**
 * Created by Dhillon on 23-11-2016.
 */

public class GetUpdatedDataService extends IntentService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public GetUpdatedDataService() {
        super("name");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        getUpdatedData();
    }

    private void getUpdatedData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.GETUSER);
        httpParamObject.setClassType(UpdateProfileResponse.class);
        OKHttpService service = new OKHttpService();
        try {
            UpdateProfileResponse response = (UpdateProfileResponse) service.getData(httpParamObject);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RestException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
