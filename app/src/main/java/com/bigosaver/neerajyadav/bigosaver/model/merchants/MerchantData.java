package com.bigosaver.neerajyadav.bigosaver.model.merchants;

import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Image;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.MerchantDetailResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

/**
 * Created by nbansal2211 on 12/10/16.
 */
public class MerchantData extends MerchantDetailResponse implements Serializable{

    public static List<MerchantData> parseJson(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<MerchantData> list = new ArrayList<>();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                MerchantData data = (MerchantData) JsonUtil.parseJson(obj.toString(), MerchantData.class);
                list.add(data);
            }
        }
        return list;
    }

}
