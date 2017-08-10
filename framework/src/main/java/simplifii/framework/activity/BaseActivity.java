package simplifii.framework.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import simplifii.framework.R;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.TaskFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.CustomFontEditText;
import simplifii.framework.widgets.CustomTextInputLayout;


public class BaseActivity extends AppCompatActivity implements
        OnClickListener, TaskFragment.AsyncTaskListener {

    protected ActionBar bar;
    protected View actionBarView;
    private Dialog dialog;
    protected boolean isCartIconNeeded = false;
    private TaskFragment taskFragment;
    protected Toolbar toolbar;

    protected final String TAG = getTag();

    public static boolean isInternetDialogVisible = false;

    public String getActionTitle() {
        return getResources().getString(R.string.app_name);
    }

    protected String getTag() {
        return this.getClass().getSimpleName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskFragment = new TaskFragment();
        getSupportFragmentManager().beginTransaction().add(taskFragment, "task").commit();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loadBundle(bundle);
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            outState.putAll(bundle);
//            super.onSaveInstanceState(outState);
//        } else {;
//            super.onSaveInstanceState(outState);
//        }
//    }

    protected void loadBundle(Bundle bundle) {

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);

    }

    protected void initWindow() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.white);
    }


    public void onActionItemClicked(View v) {
        Log.i(TAG, "onActionItemClicked");
        switch (v.getId()) {

            default:
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setFullScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getWindow().setAttributes(attrs);
    }


    public void onBackIconClicked(View v) {
        super.onBackPressed();
    }

    public void showToast(String message) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public AsyncTask executeTask(int taskCode, Object... params) {
        if (Util.isConnectingToInternet(this)) {
            taskFragment.createAsyncTaskManagerObject(taskCode)
                    .executeOnExecutor(TaskFragment.AsyncManager.THREAD_POOL_EXECUTOR, params);
        } else {
            Log.i("Base Activity", "Not Connected to internet");
            showToast("Internet not connected...");
//            if (isInternetDialogVisible) {
//                Util.createAlertDialog(this, "Please Connect to Internet",
//                        "Not Connected to internet", false, "OK", "Cancel",
//                        internetDialogListener).show();
//                isInternetDialogVisible = true;
//            }
            onInternetException();
        }
        return null;
    }


    protected void onInternetException() {
//        findViewById(R.id.frame_noInternet).setVisibility(View.VISIBLE);
    }

    public boolean isNetworkAvailable() {
        if (Util.isConnectingToInternet(this)) {
            return true;
        } else {
            Log.i("Base Activity", "Not Connected to internet");
            if (isInternetDialogVisible) {
                Util.createAlertDialog(this, "Please Connect to Internet",
                        "Not Connected to internet", false, "OK", "Cancel",
                        internetDialogListener).show();
                isInternetDialogVisible = true;
            }
            return false;
        }
    }

    public static Util.DialogListener internetDialogListener = new Util.DialogListener() {

        @Override
        public void onOKPressed(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            isInternetDialogVisible = false;
        }

        @Override
        public void onCancelPressed(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            isInternetDialogVisible = false;
        }
    };


    public void hideProgressBar() {
        Log.i(TAG + "Dialog", Thread.currentThread().getName());
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
        } else {
            dialog = new Dialog(this);
//            dialog.setMessage("Loading...");
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_progress_bar);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(divierId);
            if (divider != null)
                divider.setVisibility(View.GONE);
            dialog.show();
        }
    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub

    }

    public void setText(String text, int textViewId) {
        TextView view = (TextView) findViewById(textViewId);
        view.setText(text);
    }

    public void setText(String text, int textViewId, View v) {
        TextView view = (TextView) v.findViewById(textViewId);
        view.setText(text);
    }


    private int cartCount = 0;


    public void startNextActivity(Class<? extends Activity> activityClass) {
        Intent i = new Intent(this, activityClass);
        startActivity(i);
        finish();
    }

    public void startNextActivity(Bundle bundle,
                                  Class<? extends Activity> activityClass) {

        Intent i = new Intent(this, activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }


    protected void setOnClickListener(int... viewIds) {
        for (int i : viewIds) {
            findViewById(i).setOnClickListener(this);
        }
    }

    protected String getEditText(int editTextId) {
        return ((EditText) findViewById(editTextId)).getText().toString()
                .trim();
    }

    @Override
    public void onPreExecute(int taskCode) {
        showProgressDialog();
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        hideProgressBar();
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        hideProgressBar();
        if (re != null) {
            showToast(re.getMessage());
        }
    }

    public void initToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getHomeIcon());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        toolbar.setBackgroundColor(colorCode);
//        setStatusBarColor(colorCode);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    protected int getHomeIcon() {
        return 0;
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    public void onRetryClicked(View view) {
        if (Util.isConnectingToInternet(this)) {
            findViewById(R.id.frame_noInternet).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomePressed();
                return true;
        }
        return false;
    }

    protected void onHomePressed() {
        onBackPressed();
    }

    protected void onServerError() {
//        FrameLayout errorLayout = (FrameLayout) findViewById(R.id.frame_noInternet);
//        if (errorLayout != null) {
//            errorLayout.setVisibility(View.VISIBLE);
//            ImageView errorImage = (ImageView) errorLayout.findViewById(R.id.iv_error);
//            TextView errorMsg = (TextView) errorLayout.findViewById(R.id.tv_errorMsg);
//            TextView errorInfo = (TextView) errorLayout.findViewById(R.id.tv_errorInfo);
//
//            errorImage.setImageResource(R.drawable.icon_server_error);
//            errorMsg.setText("SERVER ERROR");
//            errorInfo.setText("Oops! Something went wrong...");
//        }
    }

    protected void hideVisibility(int... viewIds) {
        for (int i : viewIds) {
            findViewById(i).setVisibility(View.GONE);
        }
    }

    protected void showVisibility(int... viewIds) {
        for (int i : viewIds) {
            findViewById(i).setVisibility(View.VISIBLE);
        }
    }

    protected int getResourceColor(int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    protected void setError(int editTextId, String error) {
        EditText editText = (EditText) findViewById(editTextId);
        editText.setError(error);
    }

    protected boolean isValidate(int... editTextIds) {
        for (int x = 0; x < editTextIds.length; x++) {
            int id = editTextIds[x];
            CustomFontEditText customFontEditText = (CustomFontEditText) findViewById(id);
            if (!customFontEditText.isValidate(this)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isValidateTil(int... editTextIds) {
        for (int x = 0; x < editTextIds.length; x++) {
            int id = editTextIds[x];
            CustomTextInputLayout customTextInputLayout = (CustomTextInputLayout) findViewById(id);
            if (!customTextInputLayout.isValidate(this)) {
                return false;
            }
        }
        return true;
    }

}
