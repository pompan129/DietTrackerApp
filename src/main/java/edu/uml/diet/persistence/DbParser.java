package edu.uml.diet.persistence;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** Class utilized to read in data from USDA food database
 *
 * @author Ray Goolishian
 */
public class DbParser {

    /** Container class with member variables for
     * each field from USDA database
     *
     * Note: all values are based on 100g serving
     */
    public class databaseFood {
        private String name;
        private double calories;
        private double protein;
        private double carbohydrate;
        private double saturatedFat;
        private double monounsaturatedFat;
        private double polyunsaturatedFat;
        private double householdWeight1;
        private String householdWeight1Description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getCalories() {
            return calories;
        }

        public void setCalories(double calories) {
            this.calories = calories;
        }

        public double getProtein() {
            return protein;
        }

        public void setProtein(double protein) {
            this.protein = protein;
        }

        public double getCarbohydrate() {
            return carbohydrate;
        }

        public void setCarbohydrate(double carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public double getSaturatedFat() {
            return saturatedFat;
        }

        public void setSaturatedFat(double saturatedFat) {
            this.saturatedFat = saturatedFat;
        }

        public double getMonounsaturatedFat() {
            return monounsaturatedFat;
        }

        public void setMonounsaturatedFat(double monounsaturatedFat) {
            this.monounsaturatedFat = monounsaturatedFat;
        }

        public double getPolyunsaturatedFat() {
            return polyunsaturatedFat;
        }

        public void setPolyunsaturatedFat(double polyunsaturatedFat) {
            this.polyunsaturatedFat = polyunsaturatedFat;
        }

        public double getHouseholdWeight1() {
            return householdWeight1;
        }

        public void setHouseholdWeight1(double householdWeight1) {
            this.householdWeight1 = householdWeight1;
        }

        public String getHouseholdWeight1Description() {
            return householdWeight1Description;
        }

        public void setHouseholdWeight1Description(String householdWeight1Description) {
            this.householdWeight1Description = householdWeight1Description;
        }
    }

    /**
     /** parse delimited ASCII file to create list of all foods from USDA database
     *
     * @param filePath          path to local copy os ASCII encoded USDA database
     * @return databaseFoodList      ArrayList of dbFood objects that will be used to populate
     *                               food database
     * @throws IOException
     */
    public ArrayList<databaseFood> importDatabase(String filePath) throws IOException {

        //initialize list to be returned
        ArrayList<databaseFood> databaseFoodList = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String record;

            while ((record = bufferedReader.readLine()) != null) {
                String[] strAry = record.split("\\^");
                String[] delimited = new String[53];
                int i = 0;

                /** make sure all records contain 54 fields  */
                while(i < strAry.length) {
                    delimited[i] = strAry[i];
                    i++;
                }
                while(i < 53){
                    delimited[i] = "";
                    i++;
                }

                databaseFood databaseFood = new databaseFood();
                for(i = 0; i < 53; i++){

                    delimited[i] = delimited[i].replace("~", "");
                    if(delimited[i].isEmpty()){
                        delimited[i] = "0";
                    }
                }

                databaseFood.setName(delimited[1]);
                databaseFood.setCalories(Double.parseDouble(delimited[3]));
                databaseFood.setProtein(Double.parseDouble(delimited[4]));
                databaseFood.setCarbohydrate(Double.parseDouble(delimited[7]));
                databaseFood.setSaturatedFat(Double.parseDouble(delimited[44]));
                databaseFood.setMonounsaturatedFat(Double.parseDouble(delimited[45]));
                databaseFood.setPolyunsaturatedFat(Double.parseDouble(delimited[46]));
                databaseFood.setHouseholdWeight1(Double.parseDouble(delimited[48]));
                databaseFood.setHouseholdWeight1Description(delimited[49]);

                databaseFoodList.add(databaseFood);
            }
            bufferedReader.close();
        }
        return databaseFoodList;
    }
}