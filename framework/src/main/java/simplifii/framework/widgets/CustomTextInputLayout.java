package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;

import simplifii.framework.R;
import simplifii.framework.utility.Util;

/**
 * Created by raghu on 27/8/16.
 */
public class CustomTextInputLayout extends TextInputLayout {
    private String errorMsg="";
    private String emptyErrorMsg="";

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context,attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context,attrs);
    }

    private void setCustomAttributes(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTxtView);
        errorMsg = a.getString(R.styleable.CustomTextInputLayout_errorMsg);
        emptyErrorMsg = a.getString(R.styleable.CustomTextInputLayout_emptyErrorMsg);
        a.recycle();
    }
    public boolean isValidate(Context context) {
//        CustomFontEditText  customFontEditText= (CustomFontEditText) getEditText();
//        return customFontEditText.isValidate(context);
        String s = getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            if(TextUtils.isEmpty(emptyErrorMsg)){
                setError(context.getString(R.string.default_errore_msg));
            }else {
                setError(emptyErrorMsg);
            }
            return false;
        } else if (getEditText().getInputType() == InputType.TYPE_CLASS_PHONE) {
            if(s.length()!=10){
                if(TextUtils.isEmpty(errorMsg)){
                    setError(context.getString(R.string.invalid_phone_number));
                }else {
                    setError(errorMsg);
                }
                return false;
            }
        } else if(getEditText().getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS){
            if(!Util.isValidEmail(s)){
                if(TextUtils.isEmpty(errorMsg)){
                    setError(context.getString(R.string.invalid_email));
                }else {
                    setError(errorMsg);
                }
                return false;
            }
        }else {
            setError(null);
        }
        return true;
    }

}
