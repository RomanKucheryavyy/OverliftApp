package edu.tacoma.uw.mtchea.overliftapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseContent {

    public static final List<Meal> ITEMS = new ArrayList<Meal>();
    public static final Map<String, Meal> ITEM_MAP = new HashMap<String, Meal>();
    private static final int COUNT = 25;

    static {
        for(int i = 1; i <= COUNT; i++){
//            addItem(createCourseItem(i));
        }
    }

    private static void addItem(Meal item){
        ITEMS.add(item);
        ITEM_MAP.put(item.getFoodId(), item);
    }

//    private static Meal createCourseItem(int position){
//        return new Meal("Id"+position, "Long desc " + position, "short desc" + position, "Pre reqs" + position);
//    }

    private static String makeDetails(int position){
        StringBuilder builder = new StringBuilder();
        builder.append("Details about course: ").append(position);
        for(int i = 0; i < position; i++){
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}
