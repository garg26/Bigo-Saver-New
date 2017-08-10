package simplifii.framework.asyncmanager;

import android.content.Context;

import simplifii.framework.utility.AppConstants;


public class ServiceFactory {

    public static Service getInstance(Context context, int taskCode) {
        Service service = null;
        switch (taskCode) {
            case AppConstants.TASKCODES.UPDATEPROFILEPIC:
                service = new FileUploadService();
                break;
            default:
                service = new OKHttpService();
                break;


        }
        return service;
    }

}
