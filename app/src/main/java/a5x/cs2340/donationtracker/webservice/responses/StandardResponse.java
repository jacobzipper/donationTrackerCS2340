package a5x.cs2340.donationtracker.webservice.responses;

import android.support.annotation.Nullable;

/**
 * Represents a standard response from the webservice
 */
public class StandardResponse {
    private final int error;
    @Nullable
    private final String msg;

    protected StandardResponse(int error, @Nullable String msg) {
        this.error = error;
        this.msg = msg;
    }

    /**
     * Get the error code of the response
     *
     * @return the error code
     */
    public int getError() {
        return error;
    }

    /**
     * Supressed unused because used by reflect
     * @return the message
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getMsg() {
        return msg;
    }
}
