package dao;

import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class UserDao extends BasicDAO<User, ObjectId> {


    public UserDao(Datastore datastore){
        super(User.class, datastore);
    }

    public void create(User user) {
        getDatastore().save(user);
    }

    public User getUser(String username) {
        return getDatastore()
                .createQuery(User.class)
                .filter("username", username).get();
    }
}
