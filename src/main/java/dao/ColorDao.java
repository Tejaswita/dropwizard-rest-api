package dao;

import model.Color;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.Optional;

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

    public Optional<Color> getColor(String color) {
        Color dbColor = datastore.createQuery(Color.class).filter("color", color).get();
        if(dbColor == null) {
            return Optional.empty();
        }
        return Optional.of(dbColor);
    }
}
