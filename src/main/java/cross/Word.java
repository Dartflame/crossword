package cross;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Word {

    private String text;
    private Point startPoint;
    private Point endPoint;
    private List<Point> way = new ArrayList<>();

    public Word(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("%s - (" + startPoint + ")  - (" + endPoint + ")", text);
    }
}
