package waitr.vendorapp.mc.waitruser.Events;

/**
 * Created by siddharth on 11/18/15.
 */
public class OrderDownloadDoneEvent {
    boolean state;

    public OrderDownloadDoneEvent(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
