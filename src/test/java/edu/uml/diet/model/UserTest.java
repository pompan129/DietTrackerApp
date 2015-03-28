package edu.uml.diet.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Double height;
    private Double weight;
    private Double goalWeight;
    private User user;
    private Collection<Day> days;
    private Day day1;
    private Day day2;


    @Before
    public void setUp() {
        id = 10;
        userName = "USERNAME";
        password = "PASSWORD";
        firstName = "FIRST";
        lastName = "LAST";
        height = 6.0;
        weight = 200.0;
        goalWeight = 175.00;
        day1 = new Day();
        day1.setId(1);
        day2 = new Day();
        day2.setId(2);
        days = new ArrayList<>();
        days.add(day1);
        days.add(day2);
        user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setHeight(height);
        user.setWeight(weight);
        user.setGoalWeight(goalWeight);
        user.setDays(days);
    }

    /**
     * test of the  getId() method
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals("ID is equal", user.getId(), id);

    }

    /**
     * test of the  setId() method
     *
     * @throws Exception
     */
    @Test
    public void testSetId() throws Exception {
        User testUser = new User();
        testUser.setId(id + 1);
        assertEquals("setId is equal", testUser.getId(), id + 1);
    }

    /**
     * test of the  getUserName() method
     *
     * @throws Exception
     */
    @Test
    public void testGetUserName() throws Exception {
        assertEquals("username is equal", user.getUserName(), userName);
    }

    /**
     * test of the  setUserName() method
     *
     * @throws Exception
     */
    @Test
    public void testSetUserName() throws Exception {
        User testUser = new User();
        testUser.setUserName(userName + "test");
        assertEquals("setUsername is equal", testUser.getUserName(), userName + "test");
    }

    /**
     * test of the  getPassword() method
     *
     * @throws Exception
     */
    @Test
    public void testGetPassword() throws Exception {
        assertEquals("password is equal", user.getPassword(), password);

    }

    /**
     * test of the  setPassword() method
     *
     * @throws Exception
     */
    @Test
    public void testSetPassword() throws Exception {
        User testUser = new User();
        testUser.setPassword(password + "test");
        assertEquals("password is equal", testUser.getPassword(), password + "test");

    }

    /**
     * test of the  getFirstName() method
     *
     * @throws Exception
     */
    @Test
    public void testGetFirstName() throws Exception {
        assertEquals("first name is equal", user.getFirstName(), firstName);
    }

    /**
     * test of the  setFirstName() method
     *
     * @throws Exception
     */
    @Test
    public void testSetFirstName() throws Exception {
        User testUser = new User();
        testUser.setFirstName(firstName + "test");
        assertEquals("password is equal", testUser.getFirstName(), firstName + "test");

    }

    /**
     * test of the  getLastName() method
     *
     * @throws Exception
     */
    @Test
    public void testGetLastName() throws Exception {
        assertEquals("last name is equal", user.getLastName(), lastName);
    }

    /**
     * test of the  setLastName() method
     *
     * @throws Exception
     */
    @Test
    public void testSetLastName() throws Exception {
        User testUser = new User();
        testUser.setLastName(lastName + "test");
        assertEquals("password is equal", testUser.getLastName(), lastName + "test");
    }

    /**
     * test of the getDays() method
     *
     * @throws Exception
     */
    @Test
    public void testGetDays() throws Exception {
        assertEquals("Days are equal", user.getDays(), days);

    }

    /**
     * test of the setDays()  method
     *
     * @throws Exception
     */
    @Test
    public void testSetDays() throws Exception {
        User testUser = new User();
        ArrayList<Day> dayArrayList = new ArrayList<>();
        Day day = new Day();
        day.setId(0);
        dayArrayList.add(day);
        dayArrayList.add(day1);
        dayArrayList.add(day2);
        testUser.setDays(dayArrayList);
        assertEquals("days are equal", testUser.getDays(), dayArrayList);

    }


    /**
     * test of the getWeight()  method
     *
     * @throws Exception
     */
    @Test
    public void testGetWeight() throws Exception {
        assertEquals("weight is equal", user.getWeight(), weight);

    }

    /**
     * test of the  setWeight() method
     *
     * @throws Exception
     */
    @Test
    public void testSetWeight() throws Exception {
        User testUser = new User();
        testUser.setWeight(weight + 1.0);
        assertTrue("weight is equal", testUser.getWeight() == weight + 1.0);

    }

    /**
     * test of the getGoalWeight()  method
     *
     * @throws Exception
     */
    @Test
    public void testGetGoalWeight() throws Exception {
        assertEquals("goal weight is equal", user.getGoalWeight(), goalWeight);
    }

    /**
     * test of the  setGoalWeight() method
     *
     * @throws Exception
     */
    @Test
    public void testSetGoalWeight() throws Exception {
        User testUser = new User();
        testUser.setGoalWeight(goalWeight + 1.0);
        assertTrue("weight is equal", testUser.getGoalWeight() == goalWeight + 1.0);
    }

    /**
     * test of the getHeight() method
     *
     * @throws Exception
     */
    @Test
    public void testGetHeight() throws Exception {
        assertEquals("height is equal", user.getHeight(), height);

    }

    /**
     * test of the  setHeight() method
     *
     * @throws Exception
     */
    @Test
    public void testSetHeight() throws Exception {
        User testUser = new User();
        testUser.setHeight(height + 1.0);
        assertTrue("height is equal", testUser.getHeight() == height + 1.0);

    }

    /**
     * test of the getBMI() method
     *
     * @throws Exception
     */
    @Test
    public void testGetBMI() throws Exception {
        Double bmi = weight / ((height * 12.0) * (height * 12.0)) * 703.0;
        assertEquals("BMI is equal", bmi, user.getBMI());
    }


    /**
     * test of the equals() method
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        User userCopy = new User();
        userCopy.setId(id);
        userCopy.setUserName(userName);
        userCopy.setPassword(password);
        userCopy.setFirstName(firstName);
        userCopy.setLastName(lastName);
        userCopy.setHeight(height);
        userCopy.setWeight(weight);
        userCopy.setGoalWeight(goalWeight);
        userCopy.setDays(days);
        assertTrue("users are equal", user.equals(userCopy));
    }

    /**
     * test of the  hashCode() method
     *
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        User userCopy = new User();
        userCopy.setId(id);
        userCopy.setUserName(userName);
        userCopy.setPassword(password);
        userCopy.setFirstName(firstName);
        userCopy.setLastName(lastName);
        userCopy.setHeight(height);
        userCopy.setWeight(weight);
        userCopy.setGoalWeight(goalWeight);
        userCopy.setDays(days);
        assertEquals("hashcodes are equal", user.hashCode(), userCopy.hashCode());

    }


}