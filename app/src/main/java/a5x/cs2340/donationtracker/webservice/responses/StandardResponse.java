package a5x.cs2340.donationtracker.webservice.responses;

/**
 * Represents a standard response from the webservice
 */
public class StandardResponse {
    private int error;
    private final String msg;

    protected StandardResponse(int error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    /**
     * Get the message from the response
     * @return the message
     */

    public String getMsg() {
        return msg;
    }


    /**
     * Get the error code of the response
     * @return the error code
     */
    public int getError() {
        return error;
    }

    /**
     * Sets the error code
     * @param error the new error code
     */
    public void setError(int error) {
        this.error = error;
    }
}
