/** Create dietTracker database */
CREATE DATABASE IF NOT EXISTS DietTracker;

/** Create users table */
CREATE TABLE DietTracker.USERS(
  id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, 
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  username VARCHAR(255),
  password VARCHAR(255),
  weight_lbs DOUBLE,
  goal_weight_lbs DOUBLE,
  height_ft DOUBLE
);

/** Create food table */
CREATE TABLE DietTracker.FOOD(
  id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  calories VARCHAR(255),
  fat VARCHAR(255),
  carbohydrates VARCHAR(255),
  protein VARCHAR(255)
);

/** Create days table */
CREATE TABLE DietTracker.DAYS(
  id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id INTEGER,
  meal_id INTEGER,
  date DATE
);

/** Create meals table */
CREATE TABLE DietTracker.MEALS(
  id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id INTEGER,
  portion_id INTEGER,
  name VARCHAR(255)
);

/** Create portions table */
CREATE TABLE DietTracker.PORTIONS(
  id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  food_id INTEGER,
  portion_size DOUBLE
);


INSERT INTO DietTracker.USERS (username,password)
  SELECT * FROM (SELECT 'testuser','password') AS tmp
  WHERE NOT EXISTS(SELECT username from DietTracker.USERS where username='testuser');



INSERT INTO DietTracker.FOOD (name, calories, fat, carbohydrates, protein)
  SELECT * FROM (SELECT 'testfood', '1', '2', '3', '4', '5') AS tmp
  WHERE NOT EXISTS(SELECT name FROM DietTracker.FOOD WHERE name = 'testfood');