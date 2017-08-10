package com.bigosaver.neerajyadav.bigosaver.model.membership;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhillon on 19-11-2016.
 */

public class MembershipPlansResponse extends MembershipPlans implements Serializable {

    public static List<MembershipPlansResponse> parseJson(String json) {
        List<MembershipPlansResponse> membershipList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Gson gson = new Gson();
                MembershipPlansResponse mpr = gson.fromJson(jsonObject.toString(), MembershipPlansResponse.class);
                membershipList.add(mpr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return membershipList;
    }

}
