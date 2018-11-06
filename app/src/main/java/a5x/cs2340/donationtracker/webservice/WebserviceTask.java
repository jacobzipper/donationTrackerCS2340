package a5x.cs2340.donationtracker.webservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Response;

import static a5x.cs2340.donationtracker.webservice.Webservice.ERROR_CODE_OK;

/**
 * Represents an abstract webservice task which must be implemented in order to make calls
 * to the backend webservice
 */
@SuppressLint("StaticFieldLeak")
public abstract class WebserviceTask<T, Void, U extends StandardResponse> extends AsyncTask<T, Void, U> {

    @Override
    protected U doInBackground(T... params) {
        return doRequestBoilerplate(params[0]);
    }

    protected U doRequestBoilerplate(T mBody) {
        Response<U> requestAttempt;
        try {
            requestAttempt = doRequest(mBody);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        U response = requestAttempt.body();
        if ((requestAttempt.code() == ERROR_CODE_OK) && (response != null)
                && (response.getError() == 0)) {
            return response;
        }
        return null;
    }

    // The webservice request
    protected abstract Response<U> doRequest(T body) throws IOException;
}
