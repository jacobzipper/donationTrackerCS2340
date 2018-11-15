package a5x.cs2340.donationTracker.webservice.responses;

import android.support.annotation.Nullable;

/**
 * Represents a standard response from the webservice
 */
public class StandardResponse {
    private int error;
    @Nullable
    private final String msg;

    /**
     * Constructor for standard response
     * @param error error code
     * @param msg message describing error
     */
    public StandardResponse(int error, @Nullable String msg) {
        this.error = error;
        this.msg = msg;
    }
    protected StandardResponse() {msg = null;}

    /**
     * Get the error code of the response
     * @return the error code
     */
    public int getError() {
        return error;
    }

    /**
     * Get message
     * Suppress unused because retrofit
     * @return the message
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getMsg() {
        return msg;
    }
}
