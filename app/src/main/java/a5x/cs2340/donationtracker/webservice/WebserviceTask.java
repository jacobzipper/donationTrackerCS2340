package a5x.cs2340.donationtracker.webservice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Response;

import static a5x.cs2340.donationtracker.webservice.Webservice.ERROR_CODE_OK;

/**
 * Represents an abstract webservice task which must be implemented in order to make calls
 * to the backend webservice
 * @param <S> The activity representing the context of the call
 * @param <T> The body of the task that is passed in
 * @param <U> The response expected from the webservice after completion
 */
@SuppressLint("StaticFieldLeak")
public abstract class WebserviceTask<S extends Activity, T,
        U extends StandardResponse> extends AsyncTask<Void, Void, Boolean> {
    protected final S mContext;
    protected final T mBody;

    protected WebserviceTask(S context, T body) {
        mContext = context;
        mBody = body;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Boolean doInBackground(Void... params) {
        Response<U> requestAttempt;
        try {
            requestAttempt = doRequest(mBody);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        U response = requestAttempt.body();
        if ((requestAttempt.code() == ERROR_CODE_OK) && (response != null)
                && (response.getError() == 0)) {
            useResponse(response);
            return true;
        }
        return false;

    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            uiSuccess();
        } else {
            uiFailure();
        }
    }

    @Override
    protected void onCancelled() {

    }

    // The webservice request
    protected abstract Response<U> doRequest(T body) throws IOException;

    // What you want to do with the response (storing data in the class and such)
    protected abstract void useResponse(U response);

    // What to do on the UI when successful
    protected abstract void uiSuccess();

    // What to do on the UI when failed
    protected abstract void uiFailure();
}
