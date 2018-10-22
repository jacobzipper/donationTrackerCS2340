package a5x.cs2340.donationtracker.webservice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public abstract class WebserviceTask<S extends Activity, T,
        U extends StandardResponse> extends AsyncTask<Void, Void, Boolean> {
    protected final S mContext;
    protected final T mBody;

    public WebserviceTask(S context, T body) {
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
            // TODO: Graceful error handling
            e.printStackTrace();
            return false;
        }

        // TODO: Error messages for each error code from backend
        U response = requestAttempt.body();
        if (requestAttempt.code() == 200 && response != null && response.getError() == 0) {
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
    public abstract Response<U> doRequest(T body) throws IOException;

    // What you want to do with the response (storing data in the class and such)
    public abstract void useResponse(U response);

    // What to do on the UI when successful
    public abstract void uiSuccess();

    // What to do on the UI when failed
    public abstract void uiFailure();
}
