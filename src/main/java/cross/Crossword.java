package cross;

import java.util.*;

public  class Crossword {

    static Character[][] clone;
    static Point startPoint;
    static Character[][] crossword;

    static LinkedList<Point> wordRoute;
    static Map<Point, List<Point>> map;

    public static void main(String[] args) {
        List<Word> list = null;
        try {
            list = detectAllWords("mlop");
            for(Word word : list)
                System.out.println(word);
        } catch (Exception e) {
            System.out.println("Нет такого слова!");
        }
    }

    //инициализация статических переменных, построение матрицы символов
    //можно написать свой массив изи вытащить из класса Randomizer массив случайных символов
    //crossword = Randomizer.createRandomArray();
    public static void init() {
        crossword = new Character[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        clone = new Character[crossword.length + 2][crossword[0].length + 2];
        for (int i = 1; i < clone.length - 1; i++) {
            for (int j = 1; j < clone[i].length - 1; j++) {
                clone[i][j] = crossword[i - 1][j - 1];
            }
        }
        for (int i = 0; i < clone.length; i++) {
            for (int j = 0; j < clone[i].length; j++) {
                if (clone[i][j] == null) clone[i][j] = '+';
            }
        }

//        for (int i = 0; i < clone.length; i++) {
//            System.out.println(Arrays.toString(clone[i]));
//        }

        wordRoute = new LinkedList<>();
        map = new HashMap<>();

        for (int i = 1; i < clone.length - 1; i++) {
            for (int j = 1; j < clone[i].length - 1; j++) {
                Point point = new Point(crossword[i - 1][j - 1], i, j);
                map.put(point, getNeibList(point));
            }
        }
    }

    public static List<Word> detectAllWords(String... words) throws Exception {
        List<Word> wordsList = new ArrayList<>();
        for (String s : words) {
            init();
            wordsList.add(detectWord(s));
        }
        return wordsList;
    }

    //основной метод по нахождению слова
    public static Word detectWord(String s) throws Exception {

        Word word = new Word(s);
        startPoint = new Point(clone[1][1],1,1);
        Point prev = null;

        for (int i = wordRoute.size(); i < word.getText().length(); ) {
            if (i == 0) {
                Point zero = detectLetter(null, s.charAt(0));
                startPoint = zero;
                wordRoute.add(zero);
                i = wordRoute.size();
            } else {
                startPoint = new Point(wordRoute.getFirst().letter,wordRoute.getFirst().x,wordRoute.getFirst().y+1);
                prev = wordRoute.get(i - 1);
                Point point = detectLetter(prev, s.charAt(i));
                if (point == null) {
                    if(wordRoute.size() >=2){
                        map.get(wordRoute.get(i - 2)).remove(prev);
                    }
                    wordRoute.remove(prev);
                    i = wordRoute.size();
                }
                else {
                    wordRoute.add(point);
                    map.get(point).remove(prev);
                    i = wordRoute.size();
                }
            }
        }

        word.setWay(wordRoute);
        word.setStartPoint(wordRoute.getFirst());
        word.setEndPoint(wordRoute.getLast());
        System.out.println(word.getWay());
        return word;
    }

    //метод проверяет наличие переданного в качестве аргумента символа в завимимости от того стартовая ли это буква или нет
    static Point detectLetter(Point prev, char letter) throws Exception {
        Point result = null;
        if (prev == null) {
            for (int i = startPoint.x; i < clone.length; i++) {

                int k = 0;
                if(i == startPoint.x)
                    k = startPoint.y;

                for (int j = k; j < clone[i].length; j++) {
                    if (letter == clone[i][j]) {
                        result = new Point(clone[i][j], i, j);
                        return result;
                    }
                }
            }
        }

        List<Point> list = map.get(prev);

        if(list == null)
            throw new Exception();

        for (int i = 0; i < list.size(); i++) {
            if (letter == list.get(i).letter) {
                result = new Point(clone[list.get(i).x][list.get(i).y], list.get(i).x, list.get(i).y);
                return result;
            }
        }
        return result;
    }

    //метод определяет всех соседей переданной в качестве аргумента точки
    public static List<Point> getNeibList(Point a) {

        List<Point> resultList = new ArrayList<>();

        resultList.add(new Point(clone[a.x + 1][a.y], a.x + 1, a.y));
        resultList.add(new Point(clone[a.x + 1][a.y + 1], a.x + 1, a.y + 1));
        resultList.add(new Point(clone[a.x + 1][a.y - 1], a.x + 1, a.y - 1));

        resultList.add(new Point(clone[a.x - 1][a.y], a.x - 1, a.y));
        resultList.add(new Point(clone[a.x - 1][a.y + 1], a.x - 1, a.y + 1));
        resultList.add(new Point(clone[a.x - 1][a.y - 1], a.x - 1, a.y - 1));

        resultList.add(new Point(clone[a.x][a.y + 1], a.x, a.y + 1));
        resultList.add(new Point(clone[a.x][a.y - 1], a.x, a.y - 1));

        return resultList;
    }
}