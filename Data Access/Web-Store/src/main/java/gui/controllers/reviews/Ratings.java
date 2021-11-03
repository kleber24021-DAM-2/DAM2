package gui.controllers.reviews;

import java.util.HashMap;
import java.util.Map;

public enum Ratings {
    VERY_BAD(1),
    BAD(2),
    NORMAL(3),
    GOOD(4),
    VERY_GOOD(5);

    private int value;
    private static Map<Integer, Ratings> map = new HashMap<>();

    Ratings(int value){
        this.value = value;
    }

    static {
        for (Ratings rating : Ratings.values()){
            map.put(rating.value, rating);
        }
    }

    public static Ratings valueOf(int numericalRating){
        return map.get(numericalRating);
    }

    public int getValue(){
        return value;
    }
}
