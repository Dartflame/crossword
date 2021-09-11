package cross;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class Point {

    protected char letter;
    protected int x;
    protected int y;

    public Point(char letter, int x, int y) {
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Координаты точки :" + letter + " - " + + x + "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return letter == point.letter &&
                x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, x, y);
    }
}
