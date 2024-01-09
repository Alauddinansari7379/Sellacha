package com.android.sellacha.api;

public class RestUtils {

    public static String getEndPoint(String URL_Type) {

       // String STG = "http://thedemostore.in/api/";
        String STG = "https://sellacha.com/api/";

        //        switch (URL_Type) {
//            case Constants.GOOD_API_DEFAULT:
//                baseUrl = BuildConfig.DEBUG ? BuildConfig.STAGING_END_POINT : BuildConfig.PRODUCTION_END_POINT;
//                break;
//            case Constants.GOOD_API_CO:
//                baseUrl = Constants.GOOD_API_CO;
//                break;
//            case Constants.GOOD_API_PH:
//                baseUrl = Constants.GOOD_API_PH;
//                break;
//        }
      // return BuildConfig.BASE_URL;
   // return TEST;
           return STG;
    }
}
