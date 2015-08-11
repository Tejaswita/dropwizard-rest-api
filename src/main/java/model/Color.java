package model;

import org.mongodb.morphia.annotations.Entity;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
public class Color {
    private final String color;
    private final String code;

    public Color(String color, String code) {
        this.color = color;
        this.code = code;
    }

    public String getColor() {
        return color;
    }
}
