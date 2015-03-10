package edu.uml.diet.persistence;

import java.io.*;
import java.util.*;

/** Class utilized to read in data from USDA food database
 *
 * @author Ray Goolishian
 */
public class DbParser {

    /**  InputStreamReader object utilized by parser to read in
     * data from USDA ANSII database
     */
    private InputStreamReader reader;

    /** Container class with member variables for
     * each field from USDA database
     *
     * Note: all values are based on 100g serving
     */
    public class dbFood{
        private int id;
        private String name;
        private double water;
        private double calories;
        private double protein;
        private double lipidTotal;
        private double ash;
        private double carbohydrate;
        private double dietaryFiber;
        private double sugarTotal;
        private double calcium;
        private double iron;
        private double magnesium;
        private double phosphorus;
        private double potassium;
        private double sodium;
        private double zinc;
        private double copper;
        private double manganese;
        private double selenium;
        private double vitaminC;
        private double thiamin;
        private double riboflavin;
        private double niacin;
        private double pantothenicAcid;
        private double vitaminB6;
        private double folateTotal;
        private double folicAcid;
        private double foodFolate;
        private double folateDfe;
        private double cholineTotal;
        private double vitaminB12;
        private double vitaminAIu;
        private double vitaminARae;
        private double retinol;
        private double alphaCarotene;
        private double betaCarotene;
        private double betaCryptoxanthin;
        private double lycopene;
        private double luteinZeazanthin;
        private double vitaminE;
        private double vitaminDMcg;
        private double vitaminDIu;
        private double vitaminK;
        private double saturatedFat;
        private double monounsaturatedFat;
        private double polyunsaturatedFat;
        private double cholesterol;
        private double householdWeight1;
        private String householdWeight1Description;
        private double householdWeight2;
        private String householdWeight2Description;
        private double percentRefuse;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getWater() {
            return water;
        }

        public void setWater(double water) {
            this.water = water;
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

        public double getLipidTotal() {
            return lipidTotal;
        }

        public void setLipidTotal(double lipidTotal) {
            this.lipidTotal = lipidTotal;
        }

        public double getAsh() {
            return ash;
        }

        public void setAsh(double ash) {
            this.ash = ash;
        }

        public double getCarbohydrate() {
            return carbohydrate;
        }

        public void setCarbohydrate(double carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public double getDietaryFiber() {
            return dietaryFiber;
        }

        public void setDietaryFiber(double dietaryFiber) {
            this.dietaryFiber = dietaryFiber;
        }

        public double getSugarTotal() {
            return sugarTotal;
        }

        public void setSugarTotal(double sugarTotal) {
            this.sugarTotal = sugarTotal;
        }

        public double getCalcium() {
            return calcium;
        }

        public void setCalcium(double calcium) {
            this.calcium = calcium;
        }

        public double getIron() {
            return iron;
        }

        public void setIron(double iron) {
            this.iron = iron;
        }

        public double getMagnesium() {
            return magnesium;
        }

        public void setMagnesium(double magnesium) {
            this.magnesium = magnesium;
        }

        public double getPhosphorus() {
            return phosphorus;
        }

        public void setPhosphorus(double phosphorus) {
            this.phosphorus = phosphorus;
        }

        public double getPotassium() {
            return potassium;
        }

        public void setPotassium(double potassium) {
            this.potassium = potassium;
        }

        public double getSodium() {
            return sodium;
        }

        public void setSodium(double sodium) {
            this.sodium = sodium;
        }

        public double getZinc() {
            return zinc;
        }

        public void setZinc(double zinc) {
            this.zinc = zinc;
        }

        public double getCopper() {
            return copper;
        }

        public void setCopper(double copper) {
            this.copper = copper;
        }

        public double getManganese() {
            return manganese;
        }

        public void setManganese(double manganese) {
            this.manganese = manganese;
        }

        public double getSelenium() {
            return selenium;
        }

        public void setSelenium(double selenium) {
            this.selenium = selenium;
        }

        public double getVitaminC() {
            return vitaminC;
        }

        public void setVitaminC(double vitaminC) {
            this.vitaminC = vitaminC;
        }

        public double getThiamin() {
            return thiamin;
        }

        public void setThiamin(double thiamin) {
            this.thiamin = thiamin;
        }

        public double getRiboflavin() {
            return riboflavin;
        }

        public void setRiboflavin(double riboflavin) {
            this.riboflavin = riboflavin;
        }

        public double getNiacin() {
            return niacin;
        }

        public void setNiacin(double niacin) {
            this.niacin = niacin;
        }

        public double getPantothenicAcid() {
            return pantothenicAcid;
        }

        public void setPantothenicAcid(double pantothenicAcid) {
            this.pantothenicAcid = pantothenicAcid;
        }

        public double getVitaminB6() {
            return vitaminB6;
        }

        public void setVitaminB6(double vitaminB6) {
            this.vitaminB6 = vitaminB6;
        }

        public double getFolateTotal() {
            return folateTotal;
        }

        public void setFolateTotal(double folateTotal) {
            this.folateTotal = folateTotal;
        }

        public double getFolicAcid() {
            return folicAcid;
        }

        public void setFolicAcid(double folicAcid) {
            this.folicAcid = folicAcid;
        }

        public double getFoodFolate() {
            return foodFolate;
        }

        public void setFoodFolate(double foodFolate) {
            this.foodFolate = foodFolate;
        }

        public double getFolateDfe() {
            return folateDfe;
        }

        public void setFolateDfe(double folateDfe) {
            this.folateDfe = folateDfe;
        }

        public double getCholineTotal() {
            return cholineTotal;
        }

        public void setCholineTotal(double cholineTotal) {
            this.cholineTotal = cholineTotal;
        }

        public double getVitaminB12() {
            return vitaminB12;
        }

        public void setVitaminB12(double vitaminB12) {
            this.vitaminB12 = vitaminB12;
        }

        public double getVitaminAIu() {
            return vitaminAIu;
        }

        public void setVitaminAIu(double vitaminAIu) {
            this.vitaminAIu = vitaminAIu;
        }

        public double getVitaminARae() {
            return vitaminARae;
        }

        public void setVitaminARae(double vitaminARae) {
            this.vitaminARae = vitaminARae;
        }

        public double getRetinol() {
            return retinol;
        }

        public void setRetinol(double retinol) {
            this.retinol = retinol;
        }

        public double getAlphaCarotene() {
            return alphaCarotene;
        }

        public void setAlphaCarotene(double alphaCarotene) {
            this.alphaCarotene = alphaCarotene;
        }

        public double getBetaCarotene() {
            return betaCarotene;
        }

        public void setBetaCarotene(double betaCarotene) {
            this.betaCarotene = betaCarotene;
        }

        public double getBetaCryptoxanthin() {
            return betaCryptoxanthin;
        }

        public void setBetaCryptoxanthin(double betaCryptoxanthin) {
            this.betaCryptoxanthin = betaCryptoxanthin;
        }

        public double getLycopene() {
            return lycopene;
        }

        public void setLycopene(double lycopene) {
            this.lycopene = lycopene;
        }

        public double getLuteinZeazanthin() {
            return luteinZeazanthin;
        }

        public void setLuteinZeazanthin(double luteinZeazanthin) {
            this.luteinZeazanthin = luteinZeazanthin;
        }

        public double getVitaminE() {
            return vitaminE;
        }

        public void setVitaminE(double vitaminE) {
            this.vitaminE = vitaminE;
        }

        public double getVitaminDMcg() {
            return vitaminDMcg;
        }

        public void setVitaminDMcg(double vitaminDMcg) {
            this.vitaminDMcg = vitaminDMcg;
        }

        public double getVitaminDIu() {
            return vitaminDIu;
        }

        public void setVitaminDIu(double vitaminDIu) {
            this.vitaminDIu = vitaminDIu;
        }

        public double getVitaminK() {
            return vitaminK;
        }

        public void setVitaminK(double vitaminK) {
            this.vitaminK = vitaminK;
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

        public double getCholesterol() {
            return cholesterol;
        }

        public void setCholesterol(double cholesterol) {
            this.cholesterol = cholesterol;
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

        public double getHouseholdWeight2() {
            return householdWeight2;
        }

        public void setHouseholdWeight2(double householdWeight2) {
            this.householdWeight2 = householdWeight2;
        }

        public String getHouseholdWeight2Description() {
            return householdWeight2Description;
        }

        public void setHouseholdWeight2Description(String householdWeight2Description) {
            this.householdWeight2Description = householdWeight2Description;
        }

        public double getPercentRefuse() {
            return percentRefuse;
        }

        public void setPercentRefuse(double percentRefuse) {
            this.percentRefuse = percentRefuse;
        }
    }

    /** parse delimited ANSII file to create list of all foods from USDA database
     *
     * @param filePath          path to local copy os ANSII encoded USDA database
     * @return databaseFoodList      ArrayList of dbFood objects that will be used to populate
     *                               food database
     */
    public ArrayList<dbFood> importDatabase(String filePath) throws IOException{

        //initialize list to be returned
        ArrayList<dbFood> databaseFoodList = new ArrayList<dbFood>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String record = null;

            while ((record = br.readLine()) != null) {
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

                dbFood databaseFood = new dbFood();

                int t = delimited.length;
                for(i = 0; i < 53; i++){

                    delimited[i] = delimited[i].replace("~", "");
                    if(delimited[i].isEmpty()){
                        delimited[i] = "0";
                    }
                }

                databaseFood.setId(Integer.parseInt(delimited[0]));
                databaseFood.setName(delimited[1]);
                databaseFood.setWater(Double.parseDouble(delimited[2]));
                databaseFood.setCalories(Double.parseDouble(delimited[3]));
                databaseFood.setProtein(Double.parseDouble(delimited[4]));
                databaseFood.setLipidTotal(Double.parseDouble(delimited[5]));
                databaseFood.setAsh(Double.parseDouble(delimited[6]));
                databaseFood.setCarbohydrate(Double.parseDouble(delimited[7]));
                databaseFood.setDietaryFiber(Double.parseDouble(delimited[8]));
                databaseFood.setSugarTotal(Double.parseDouble(delimited[9]));
                databaseFood.setCalcium(Double.parseDouble(delimited[10]));
                databaseFood.setIron(Double.parseDouble(delimited[11]));
                databaseFood.setMagnesium(Double.parseDouble(delimited[12]));
                databaseFood.setPhosphorus(Double.parseDouble(delimited[13]));
                databaseFood.setPotassium(Double.parseDouble(delimited[14]));
                databaseFood.setSodium(Double.parseDouble(delimited[15]));
                databaseFood.setZinc(Double.parseDouble(delimited[16]));
                databaseFood.setCopper(Double.parseDouble(delimited[17]));
                databaseFood.setManganese(Double.parseDouble(delimited[18]));
                databaseFood.setSelenium(Double.parseDouble(delimited[19]));
                databaseFood.setVitaminC(Double.parseDouble(delimited[20]));
                databaseFood.setThiamin(Double.parseDouble(delimited[21]));
                databaseFood.setRiboflavin(Double.parseDouble(delimited[22]));
                databaseFood.setNiacin(Double.parseDouble(delimited[23]));
                databaseFood.setPantothenicAcid(Double.parseDouble(delimited[24]));
                databaseFood.setVitaminB6(Double.parseDouble(delimited[25]));
                databaseFood.setFolateTotal(Double.parseDouble(delimited[26]));
                databaseFood.setFolicAcid(Double.parseDouble(delimited[27]));
                databaseFood.setFoodFolate(Double.parseDouble(delimited[28]));
                databaseFood.setFolateDfe(Double.parseDouble(delimited[29]));
                databaseFood.setCholineTotal(Double.parseDouble(delimited[30]));
                databaseFood.setVitaminB12(Double.parseDouble(delimited[31]));
                databaseFood.setVitaminAIu(Double.parseDouble(delimited[32]));
                databaseFood.setVitaminARae(Double.parseDouble(delimited[33]));
                databaseFood.setRetinol(Double.parseDouble(delimited[34]));
                databaseFood.setAlphaCarotene(Double.parseDouble(delimited[35]));
                databaseFood.setBetaCarotene(Double.parseDouble(delimited[36]));
                databaseFood.setBetaCryptoxanthin(Double.parseDouble(delimited[37]));
                databaseFood.setLycopene(Double.parseDouble(delimited[38]));
                databaseFood.setLuteinZeazanthin(Double.parseDouble(delimited[39]));
                databaseFood.setVitaminE(Double.parseDouble(delimited[40]));
                databaseFood.setVitaminDMcg(Double.parseDouble(delimited[41]));
                databaseFood.setVitaminDIu(Double.parseDouble(delimited[42]));
                databaseFood.setVitaminK(Double.parseDouble(delimited[43]));
                databaseFood.setSaturatedFat(Double.parseDouble(delimited[44]));
                databaseFood.setMonounsaturatedFat(Double.parseDouble(delimited[45]));
                databaseFood.setPolyunsaturatedFat(Double.parseDouble(delimited[46]));
                databaseFood.setCholesterol(Double.parseDouble(delimited[47]));
                databaseFood.setHouseholdWeight1(Double.parseDouble(delimited[48]));
                databaseFood.setHouseholdWeight1Description(delimited[49]);
                databaseFood.setHouseholdWeight2(Double.parseDouble(delimited[50]));
                databaseFood.setHouseholdWeight2Description(delimited[51]);
                databaseFood.setPercentRefuse(Double.parseDouble(delimited[52]));

                databaseFoodList.add(databaseFood);
            }
            br.close();
        }
        return databaseFoodList;
    }
}