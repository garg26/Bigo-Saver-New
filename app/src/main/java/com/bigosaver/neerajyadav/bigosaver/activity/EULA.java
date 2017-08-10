package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.bigosaver.neerajyadav.bigosaver.model.EulaResponse;
import com.bigosavers.R;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;

/**
 * Created by Dhillon on 30-11-2016.
 */

public class EULA extends BaseActivity {
    private TextView tvEula;
    private boolean eula = false, rules = false, terms = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);
        Bundle bundle = getIntent().getExtras();
        eula = bundle.getBoolean(AppConstants.BUNDLE_KEYS.EULA);
        rules = bundle.getBoolean(AppConstants.BUNDLE_KEYS.RULES);
        terms = bundle.getBoolean(AppConstants.BUNDLE_KEYS.TERMS);
        if (eula)
            initToolBar(getString(R.string.eula));
        else if (rules)
            initToolBar(getString(R.string.rules_of_use));
        else if (terms)
            initToolBar(getString(R.string.terms_of_use));
        tvEula = (TextView) findViewById(R.id.tv_eula);
        getEula();
    }

    private void getEula() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.EULA);
        httpParamObject.setClassType(EulaResponse.class);
        executeTask(AppConstants.TASKCODES.EULA, httpParamObject);
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
        showProgressDialog();
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.EULA:
                EulaResponse eulaResponse = (EulaResponse) response;
                Spanned htmlText;
                if (eulaResponse != null) {
                    if (eula) {
                        htmlText = Html.fromHtml(eulaResponse.getEula());
                        tvEula.setText(htmlText);
                    } else if (rules) {
//                        tvEula.setText(eulaResponse.getRules_and_guidelines().toString());
                        htmlText = Html.fromHtml(eulaResponse.getRules_and_guidelines());
                        tvEula.setText(htmlText);
                    } else if (terms) {
                        htmlText = Html.fromHtml(eulaResponse.getTerm_of_use());
                        tvEula.setText(htmlText);
                    }
                }
        }
    }
}
