package edu.tacoma.uw.mtchea.overliftapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exercise content, with all its needed attributes such as name , target area , complexity and description
 * code snippets provided by Menaka Abraham, webcourses lab
 */
public class ExerciseContent {

    public static final List<Exercise> ITEMS = new ArrayList<Exercise>();
    public static final Map<String, Exercise> ITEM_MAP = new HashMap<String, Exercise>();
    private static final int COUNT = 25;

    static {
        for(int i = 1; i <= COUNT; i++){
            addItem(createExerciseItem(i));
        }
    }

    /**
     * Adding a exercise
     * @param item
     */
    private static void addItem(Exercise item){
        ITEMS.add(item);
        ITEM_MAP.put(item.getExercise(), item);
    }

    /**
     * creating an exercise with details
     * @param position
     * @return
     */
    private static Exercise createExerciseItem(int position){
        return new Exercise("Name"+position, "Target Area " + position, "Complexity" + position, "Description" + position);
    }

    /**
     * Making details for exercise
     * @param position
     * @return
     */
    private static String makeDetails(int position){
        StringBuilder builder = new StringBuilder();
        builder.append("Details about exercise: ").append(position);
        for(int i = 0; i < position; i++){
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}
