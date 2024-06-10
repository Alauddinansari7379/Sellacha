package com.android.sellacha.api.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.sellacha.R;
import com.android.sellacha.activity.BaseActivity;
import com.android.sellacha.api.APIInterface;
import com.android.sellacha.api.ApiResponse;
import com.android.sellacha.api.RequestModel;
import com.android.sellacha.api.request.LoginRequest;
import com.android.sellacha.app.SellAchaApplication;
import com.android.sellacha.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainService {


    private static final APIInterface apiService = BaseService.getAPIClient(Constants.GOOD_API_DEFAULT).create(APIInterface.class);

    public MainService() {

    }

    public static ApiResponse customResponse(Response<ApiResponse> response) {
        ApiResponse apiResponse = new ApiResponse();
        //   apiResponse.setStatusCode(response.body().getSuccess().equals("200"));
        apiResponse.setData(response.body().getData());
        apiResponse.setMessage(response.body().getMessage());
        //     apiResponse.setSuccess(String.valueOf(response.body().getSuccess()));
        return apiResponse;
    }

    public static ApiResponse tokenExpiredResponse(String msg, String code) {
        ApiResponse apiResponse = new ApiResponse();
        //   apiResponse.setSuccess(code);
        apiResponse.setData(null);
        apiResponse.setMessage(msg);
        apiResponse.setStatusCode(false);
        return apiResponse;
    }

    public static LiveData<ApiResponse> userLogin(final Context context, LoginRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.userLogin(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }

    public static LiveData<ApiResponse> getAllOrder(final Context context) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getAllOrder(SellAchaApplication.getDefaultHeaders());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }

    public static LiveData<ApiResponse> getFilterOrder(final Context context, int id, String status, String start, String end) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getFilterOrder(SellAchaApplication.getDefaultHeaders(), id, status, start, end);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }

    public static LiveData<ApiResponse> getOrderByID(final Context context, int orderID) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        RequestModel requestModel = new RequestModel();
        requestModel.setId(orderID);
        Call<ApiResponse> call = apiService.getOrderByID(SellAchaApplication.getDefaultHeaders(), requestModel);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }


    public static LiveData<ApiResponse> logout(final Context context, String device_token, String device_type) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.logOut(SellAchaApplication.getDefaultHeaders(),device_token,device_type);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }

    public static LiveData<ApiResponse> getAllProduct(final Context context) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getAllProduct(SellAchaApplication.getDefaultHeaders());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }




    public static LiveData<ApiResponse> getIncompleteProduct(final Context context) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getIncompleteProduct(SellAchaApplication.getDefaultHeaders());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }


    public static LiveData<ApiResponse> getTrash(final Context context) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getTrashProduct(SellAchaApplication.getDefaultHeaders());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }



    public static LiveData<ApiResponse> getDraftProduct(final Context context) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getDraftProduct(SellAchaApplication.getDefaultHeaders());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }

    public static LiveData<ApiResponse> getPublishProduct(final Context context) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        if (!SellAchaApplication.getInstance().isInternetConnected(context)) {
            return data;
        }
        Call<ApiResponse> call = apiService.getPublishProduct(SellAchaApplication.getDefaultHeaders());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showToast(context.getResources().getString(R.string.something_wrong));
            }
        });
        return data;
    }

}
