import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    public void addDetails(){
        LocalTime openingTime = LocalTime.parse("10:30:00");

        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime timeNow = LocalTime.now();
        addDetails();
        restaurant.setOpeningTime(timeNow.plusMinutes(30));
        assertTrue(restaurant.isRestaurantOpen());
        //WRITE UNIT TEST CASE HERE
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime timeNow = LocalTime.now();
        addDetails();
        restaurant.setOpeningTime(timeNow.minusMinutes(30));
        assertTrue(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addDetails();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        addDetails();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addDetails();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Failing Test case
    @Test
    public void total_price_should_be_equal_to_sum_of_items(){
        addDetails();
        assertEquals(506,restaurant.getTotalPrice(restaurant.getMenu()));
    }
    //failing Test case
    @Test
    public void total_price_should_reduce_accordingly_on_removal() throws itemNotFoundException {
        addDetails();
        List<Item> menu = restaurant.getMenu();
        int total = restaurant.getTotalPrice(menu);
        assertEquals(total,388);
        restaurant.removeFromMenu("Vegetable lasagne");
        int newTotal = restaurant.getTotalPrice(menu);
        assertEquals(newTotal,119);
    }
   //Failing test case
    @Test
    public void total_price_should_increase_accordingly_on_addition() throws itemNotFoundException {
        addDetails();
        List<Item> menu = restaurant.getMenu();
        int total = restaurant.getTotalPrice(menu);
        assertEquals(total,388);
        restaurant.addToMenu("Souffle", 200);
        int newTotal = restaurant.getTotalPrice(menu);
        assertEquals(newTotal,588);
    }
}