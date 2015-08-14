package dao;

import model.Color;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class ColorDao extends BasicDAO<Color, ObjectId> {

    private Datastore datastore;

    public ColorDao(Datastore datastore) {
        super(datastore);

        this.datastore = datastore;
    }

    public String add(Color color) {
        datastore.save(color);

        return color.getColor();
    }

    public Color getColor(String color) {
        Color dbColor = datastore.createQuery(Color.class).filter("color", color).get();
        return dbColor;
    }
}
