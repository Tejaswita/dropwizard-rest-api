package model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Objects;

@Entity(value = "color")
public class Color {
    @Id
    private  String color;
    private  String code;

    public Color() {
    }

    public Color(String color, String code) {
        this.color = color;
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Objects.equals(color, color1.color) &&
                Objects.equals(code, color1.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, code);
    }

    public String getCode() {
        return code;
    }
}
