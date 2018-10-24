package a5x.cs2340.donationtracker.webservice.responses;

public class StandardResponse {
    private int error;
    private String msg;

    protected StandardResponse(int error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
