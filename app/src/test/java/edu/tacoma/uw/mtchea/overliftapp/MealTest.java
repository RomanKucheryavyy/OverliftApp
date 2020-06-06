package edu.tacoma.uw.mtchea.overliftapp;

import org.junit.Test;

import edu.tacoma.uw.mtchea.overliftapp.model.Meal;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * JUnit testing for Meal class
 * @author Mercedes Chea
 * @version June 5, 2020
 */
public class MealTest {

    /**
     * Tests Meal constructor
     */
    @Test
    public void testMealConstructor() {
        assertNotNull(new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu"));
    }

    /**
     * Test getter for foodId
     */
    @Test
    public void testGetFoodId() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        assertNotNull(meal.getFoodId());
    }

    /**
     * Test setter for foodId
     */
    @Test
    public void testSetFoodName() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        meal.setFoodName("Burrito");
        assertEquals("food name was not set", meal.getFoodId(), "Burrito");
    }

    /**
     * Test getter for calories
     */
    @Test
    public void testGetCalories() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        assertNotNull(meal.getCalories());
    }

    /**
     * Test setter for calories
     */
    @Test
    public void testSetCalories() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        meal.setCalories(200);
        assertEquals("food name was not set", meal.getCalories(), 200);
    }

    /**
     * Test getter for fats
     */
    @Test
    public void testGetFats() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        assertNotNull(meal.getFats());
    }

    /**
     * Test setter for fats
     */
    @Test
    public void testSetFats() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        meal.setFats(200);
        assertEquals("food name was not set", meal.getFats(), 200);
    }

    /**
     * Test getter for carbs
     */
    @Test
    public void testGetCarbs() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        assertNotNull(meal.getCarbs());
    }

    /**
     * Test setter for calories
     */
    @Test
    public void testSetCarbs() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        meal.setCarbs(200);
        assertEquals("food name was not set", meal.getCarbs(), 200);
    }
    /**
     * Test getter for proteins
     */
    @Test
    public void testGetProteins() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        assertNotNull(meal.getProteins());
    }

    /**
     * Test setter for proteins
     */
    @Test
    public void testSetProteins() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        meal.setmProteins(200);
        assertEquals("food name was not set", meal.getProteins(), 200);
    }

    /**
     * Test getter for quantity
     */
    @Test
    public void testGetQuantity() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        assertNotNull(meal.getQuantity());
    }

    /**
     * Test setter for quantity
     */
    @Test
    public void testSetQuantity() {
        Meal meal = new Meal(100, 100, 100, 100, "Salad", 1, "mtchea@uw.edu");
        meal.setQuantity(200);
        assertEquals("food name was not set", meal.getQuantity(), 200);
    }

    /**
     *
     */
    @Test
    public void testParseCourseJson() {

    }
}
