package simplifii.framework.utility;

import java.util.HashMap;
import java.util.LinkedHashMap;

public interface AppConstants {

    public static final String DEF_REGULAR_FONT = "Lato-Regular.ttf";
    String APP_LINK = "https://drive.google.com/file/d/0B8wKJnD6sONHeXlUbm5pOTk4dGM/view?usp=sharing";
    LinkedHashMap<Integer, String> storeCategory = new LinkedHashMap<Integer, String>();
    String REGISTRATION_COMPLETE = "registrationComplete";

    interface TABLAYOUT_TITLES {
        String TITLE_OFFERS = "Offers";
        String TITLE_DETAILS = "Details";
        String TITLE_DINING = "Bigo Dining";
        String TITLE_SIGNUP = "Sign Up";
        String TITLE_SIGNIN = "Sign In";
    }

    interface TOOLBAR_TITLES {
        String TITLE_ELEVATION = "Elevation Burger";
        String TITLE_FOOD_DRINK = "Food & Drinks";
        String TITLE_FILTER = "Apply Filters";
    }

    interface REQUEST_CODES {

        int GOOGLE_SIGHN_IN = 10;
        int REGISTER = 11;
        int FILTER = 12;
        int FORGOT_PASS = 13;
        int MERCHANTEWITHID = 14;
        int PAYMENT = 15;
    }

    public static interface VALIDATIONS {
        String EMPTY = "empty";
        String EMAIL = "email";
        String MOBILE = "mobile";
    }

    public static interface PARAMS {
        String LAT = "latitude";
        String LNG = "longitude";
    }


    public static interface ERROR_CODES {

        public static final int UNKNOWN_ERROR = 0;
        public static final int NO_INTERNET_ERROR = 1;
        public static final int NETWORK_SLOW_ERROR = 2;
        public static final int URL_INVALID = 3;
        public static final int DEVELOPMENT_ERROR = 4;

    }

    public static interface PAGE_URL {
        String PHOTO_URL = "http://52.66.8.188/media/";
        String BASE_URL = "http://52.66.8.188:80/v1/";
//        String PHOTO_URL = "http://192.168.1.4:8000/media/";
//        String BASE_URL = "http://192.168.1.4:8000/v1/";
        String REGISTER = BASE_URL + "users/register_user_mobile/";
        String LOGIN = BASE_URL + "token/";
        String CATEGORY = BASE_URL + "category";
        String CITIES = BASE_URL + "city";
        String MERCHANTS_FEATURE_LIST = BASE_URL + "merchant/list_feature/";
        //        String FILTERITEMS = BASE_URL + "filter/";
        String FILTERITEMS = BASE_URL + "category/%s/filters/";
        String SOCIAL_REGISTER = BASE_URL + "users/social_register_login/";
        String OFFERS = BASE_URL + "offer/filter_offer/";
        String MERCHANTDETAIL = BASE_URL + "merchant/";
        String FORGOTPASS = BASE_URL + "users/change_password/";
        String MEMBERSHIPPLAN = BASE_URL + "membership_plan/";
        String CASHCARDUSE = BASE_URL + "cash_card/checking_code/";
        String PROMOCARDUSE = BASE_URL + "cash_card/checking_promo_code/";
        String UPDATEUSER = BASE_URL + "users/profile/";
        String GETUSER = BASE_URL + "users/profile/";
        String IMPLEMENTCASHCARD = BASE_URL + "cash_card/implement_cash_card/";
        String SEARCH = BASE_URL + "merchant/generic_search/";
        String SEARCHWITHPLACE = BASE_URL + "merchant/search/";
        String SETFAVOURITE = BASE_URL + "favorite/";
        String REMOVEFAVOURITE = BASE_URL + "favorite/remove_favorite/";
        String REDEEMOFFER = BASE_URL + "offer/";
        String REDEMPTIONHISTORY = BASE_URL + "offer/redeem_user_history";
        String CHANGEPASS = BASE_URL + "mobile_reset_password/";
        String UPDATENUMBER = BASE_URL + "mobile_phone_update/";
        String EULA = BASE_URL + "site_settings/?format=json";
        String BIGODRINKS = BASE_URL + "bigo_drink_count/";
        String IMPLEMENTPROMOCODE = BASE_URL + "cash_card/implement_promo_code/";
        String GET_MEMBERSHIP_MERCHANTS = BASE_URL + "merchant/merchant_by_plan/";
        String SUBSCRIPTION_HISTORY = BASE_URL + "subscription/user_history/";
    }

    public static interface PREF_KEYS {

        String KEY_LOGIN = "IsUserLoggedIn";
        String KEY_USERNAME = "username";
        String KEY_PASSWORD = "password";
        String ACCESS_CODE = "access";
        String APP_LINK = "appLink";
        String USER_TOKEN = "user_token";
        String IS_LOGIN = "is_login";
        String IS_FIRST = "is_first";
        String PHONE = "phone";
        String CATEGOTYID = "categoryId";
        String CITYID = "cityId";
        String OFFEROPTION = "offer_option";
        String FILTERS = "filters";
        String USER_TYPE = "userType";
        String PROFILE = "profile";
        String USER_ID = "userId";
        String SELECTED_CITY = "selectedCity";
        String CATEGORY = "category";
        String IS_SOCIAL_SIGNUP = "isSocialSignUp";
        String MERCHANT = "merchant";
        String CATEGORY_DATA = "categoryData";
        String LATITUDE = PARAMS.LAT;
        String LONGITUDE = PARAMS.LNG;
        String SELECTED_FILTERS = "selectedFilters";
        String SELECTED_CATEGORY = "selectedCategory";
        String SELECTED_FILTERS_JSON = "selectedFilterJson";
    }

    public static interface BUNDLE_KEYS {
        public static final String KEY_SERIALIZABLE_OBJECT = "KEY_SERIALIZABLE_OBJECT";
        public static final String FRAGMENT_TYPE = "FRAGMENT_TYPE";
        String EXTRA_BUNDLE = "bundle";
        String CATEGORY_DATA = "category";
        String CITY = "city";
        String KEY_POSITION = "position";
        String FILTERS = "filters";
        String MERCHANT = "merchant";
        String MESSAGE = "message";
        String MEMBERSHIPID = "membershipid";
        String SEARCHDATA = "searchData";
        String NAME = "name";
        String DISTANCE = "distance";
        String LONGITUDE = "longitude";
        String LATITUDE = "latitude";
        String CATEGORYID = "categoryId";
        String ISPLACE = "isPlace";
        String ISAREA = "isArea";
        String PLACEID = "place_id";
        String AREAID = "area";
        String EULA = "eula";
        String RULES = "rules";
        String CITYID = "cityId";
        String TERMS = "terms";
        String DRINKS = "drinks";
        String MERCHANT_TYPE = "merchant";
    }

    public static interface HASHKEY {
        String DEFAULT = "default";
        String GOLD = "gold";
        String PLATINUM = "platinum";
        String SIGNATURE = "signature";
        String DEFAULTTRANSACTION = "defaultTransaction";
        String GOLDTRANSACTION = "goldTransaction";
        String PLATINUMTRANSACTION = "platinumTransaction";
        String SIGNATURETRANSACTION = "signatureTransaction";
    }


    public static interface VIEW_TYPE {
        int CARD_MY_TEAM = 0;
    }

    public static interface MEDIA_TYPES {
        String IMAGE = "img";
        String AUDIO = "audio";
        String VIDEO = "video";
    }

    public interface TASKCODES {
        int REGISTER = 10;
        int LOGIN = 11;
        int CATEGORYCODE = 12;
        int CITIES = 13;
        int MERCHANTS_FEATURE_LIST = 14;
        int FILTERITEMS = 15;
        int SOCIAL_REGISTER = 16;
        int OFFEROPTION = 17;
        int MERCHANTDETAIL = 18;
        int MERCHANTTIMING = 19;
        int MERCHANTOFFER = 20;
        int FORGOTPASS = 21;
        int MEMBERSHIPPLAN = 22;
        int CASHCARDUSE = 23;
        int UPDATEUSER = 24;
        int GETUSER = 25;
        int IMPLEMENTCASHCARD = 26;
        int SEARCHWITHOUTPLACE = 27;
        int SEARCHWITHPLACE = 28;
        int SETFAVOURITE = 29;
        int GETFAVOURITES = 30;
        int REMOVEFAVOURITE = 31;
        int MEMBERSHIPPLANWITHID = 32;
        int REDEEMOFFER = 33;
        int REDEMPTIONHISTORY = 34;
        int CHANGEPASS = 35;
        int UPDATENUMBER = 36;
        int EULA = 37;
        int BIGODRINKS = 38;
        int UPDATEPROFILEPIC = 39;
        int PAYMENT_PAYU = 40;
        int UPLOAD_IMAGE = 41;
        int PROMOCARDUSE = 42;
        int IMPLEMENTPROMOCODE = 43;
        int GET_MEMBERSHIP_MERCHANTS = 44;
        int SUBSCRIPTION_HISTORY = 45;
    }
}
