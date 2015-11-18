package waitr.vendorapp.mc.waitruser.dataObjects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Manan Wason on 15/11/15.
 */
public class User {
    private int id;
    private String name;
    private String email;

    @SerializedName("created_at")
    private String creationTime;

    @SerializedName("updated_at")
    private String updationTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(String updationTime) {
        this.updationTime = updationTime;
    }

    public User(int id, String name, String email, String creationTime, String updationTime) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
    }
}
