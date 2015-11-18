package waitr.vendorapp.mc.waitruser.Events;

/**
 * Created by Manan Wason on 18/11/15.
 */
public class ItemDownloadDoneEvent {
    boolean state;

    public ItemDownloadDoneEvent(boolean state) {
        this.state = state;
    }

    public boolean isState() {

        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
