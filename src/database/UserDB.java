package database;

import classes.User.User;
import com.mongodb.*;

public class UserDB {
    private static String LOGTAG = "User DB: ";

    public static BasicDBObject toDBObject(User user) {
        return new BasicDBObject("_id", user.getId())
                .append("name", user.getName())
                .append("surname", user.getSurname())
                .append("email", user.getEmail())
                .append("phone_number", user.getPhoneNumber())
                .append("address", user.getAddress());
    }
}
