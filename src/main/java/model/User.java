package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("users")
@Indexes(@Index(value="unique_username", fields = @Field("username")))
public class User {

    @Id
    private ObjectId id;
    private  String username;
    private  String password;

    protected User(){}

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
