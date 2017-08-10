package simplifii.framework.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;


public class CustomFontEditText extends AppCompatEditText {
    private static final String TAG = "CustomFont";
    private String emptyErrorMsg ;
    private String errorMsg;

    public CustomFontEditText(Context context) {
        super(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }


    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);

    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        setErrorMsg(context, attrs);
//    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        setErrorMsg(ctx, attrs);
        TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTxtView);
        String customFont = a
                .getString(R.styleable.CustomFontTxtView_customFont);

        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            if (asset == null || "".equals(asset)) {
                asset = "fonts/" + AppConstants.DEF_REGULAR_FONT;
            }
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Error to get typeface: " + e.getMessage());
            return false;
        }
        setTypeface(tf);
        return true;
    }

    private void setErrorMsg(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTxtView);
        emptyErrorMsg = a.getString(R.styleable.CustomFontTxtView_e_e_m);
        errorMsg = a.getString(R.styleable.CustomFontTxtView_e_m);
    }

    public boolean isValidate(Context context) {
        String s = getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            if(TextUtils.isEmpty(emptyErrorMsg)){
                setError(context.getString(R.string.default_errore_msg));
            }else {
                setError(emptyErrorMsg);
            }
            return false;
        } else if (getInputType() == InputType.TYPE_CLASS_PHONE) {
            if(s.length()!=10){
                if(TextUtils.isEmpty(errorMsg)){
                    setError(context.getString(R.string.invalid_phone_number));
                }else {
                    setError(errorMsg);
                }
                return false;
            }
        } else if(getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS){
            if(Util.isValidEmail(s)){
                if(TextUtils.isEmpty(errorMsg)){
                    setError(context.getString(R.string.invalid_email));
                }else {
                    setError(errorMsg);
                }
                return false;
            }
        }
        return true;
    }

}
