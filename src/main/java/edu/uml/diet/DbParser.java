package edu.uml.diet;

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
     */
    public class dbFood{
        private int NDB_No;
        private String Shrt_Desc;
        private double Water;
        private double Energ_Kcal;
        private double Protein;
        private double Lipid_Tot;
        private double Ash;
        private double Carbohydrt;
        private double Fiber_TD;
        private double Sugar_Tot;
        private double Calcium;
        private double Iron;
        private double Magnesium;
        private double Phosphorus;
        private double Potassium;
        private double Sodium;
        private double Zinc;
        private double Copper;
        private double Manganese;
        private double Selenium;
        private double Vit_C;
        private double Thiamin;
        private double Riboflavin;
        private double Niacin;
        private double Panto_acid;
        private double Vit_B6;
        private double Folate_Tot;
        private double Folic_acid;
        private double Food_Folate;
        private double Folate_DFE;
        private double Choline_Tot;
        private double Vit_B12;
        private double Vit_A_IU;
        private double Vit_A_RAE;
        private double Retinol;
        private double Alpha_Carot;
        private double Beta_Carot;
        private double Beta_Crypt;
        private double Lycopene;
        private double LutZea;
        private double Vit_E;
        private double Vit_D_mcg;
        private double Vit_D_IU;
        private double Vit_K;
        private double FA_Sat;
        private double FA_Mono;
        private double FA_Poly;
        private double Cholestrl;
        private double GmWt_1;
        private String GmWt_Desc1;
        private double GmWt_2;
        private String GmWt_Desc2;
        private double Refuse_Pct;

        public int getNDB_No() {
            return NDB_No;
        }

        public void setNDB_No(int NDB_No) {
            this.NDB_No = NDB_No;
        }

        public String getShrt_Desc() {
            return Shrt_Desc;
        }

        public void setShrt_Desc(String shrt_Desc) {
            Shrt_Desc = shrt_Desc;
        }

        public double getWater() {
            return Water;
        }

        public void setWater(double water) {
            Water = water;
        }

        public double getEnerg_Kcal() {
            return Energ_Kcal;
        }

        public void setEnerg_Kcal(double energ_Kcal) {
            Energ_Kcal = energ_Kcal;
        }

        public double getProtein() {
            return Protein;
        }

        public void setProtein(double protein) {
            Protein = protein;
        }

        public double getLipid_Tot() {
            return Lipid_Tot;
        }

        public void setLipid_Tot(double lipid_Tot) {
            Lipid_Tot = lipid_Tot;
        }

        public double getAsh() {
            return Ash;
        }

        public void setAsh(double ash) {
            Ash = ash;
        }

        public double getCarbohydrt() {
            return Carbohydrt;
        }

        public void setCarbohydrt(double carbohydrt) {
            Carbohydrt = carbohydrt;
        }

        public double getFiber_TD() {
            return Fiber_TD;
        }

        public void setFiber_TD(double fiber_TD) {
            Fiber_TD = fiber_TD;
        }

        public double getSugar_Tot() {
            return Sugar_Tot;
        }

        public void setSugar_Tot(double sugar_Tot) {
            Sugar_Tot = sugar_Tot;
        }

        public double getCalcium() {
            return Calcium;
        }

        public void setCalcium(double calcium) {
            Calcium = calcium;
        }

        public double getIron() {
            return Iron;
        }

        public void setIron(double iron) {
            Iron = iron;
        }

        public double getMagnesium() {
            return Magnesium;
        }

        public void setMagnesium(double magnesium) {
            Magnesium = magnesium;
        }

        public double getPhosphorus() {
            return Phosphorus;
        }

        public void setPhosphorus(double phosphorus) {
            Phosphorus = phosphorus;
        }

        public double getPotassium() {
            return Potassium;
        }

        public void setPotassium(double potassium) {
            Potassium = potassium;
        }

        public double getSodium() {
            return Sodium;
        }

        public void setSodium(double sodium) {
            Sodium = sodium;
        }

        public double getZinc() {
            return Zinc;
        }

        public void setZinc(double zinc) {
            Zinc = zinc;
        }

        public double getCopper() {
            return Copper;
        }

        public void setCopper(double copper) {
            Copper = copper;
        }

        public double getManganese() {
            return Manganese;
        }

        public void setManganese(double manganese) {
            Manganese = manganese;
        }

        public double getSelenium() {
            return Selenium;
        }

        public void setSelenium(double selenium) {
            Selenium = selenium;
        }

        public double getVit_C() {
            return Vit_C;
        }

        public void setVit_C(double vit_C) {
            Vit_C = vit_C;
        }

        public double getThiamin() {
            return Thiamin;
        }

        public void setThiamin(double thiamin) {
            Thiamin = thiamin;
        }

        public double getRiboflavin() {
            return Riboflavin;
        }

        public void setRiboflavin(double riboflavin) {
            Riboflavin = riboflavin;
        }

        public double getNiacin() {
            return Niacin;
        }

        public void setNiacin(double niacin) {
            Niacin = niacin;
        }

        public double getPanto_acid() {
            return Panto_acid;
        }

        public void setPanto_acid(double panto_acid) {
            Panto_acid = panto_acid;
        }

        public double getVit_B6() {
            return Vit_B6;
        }

        public void setVit_B6(double vit_B6) {
            Vit_B6 = vit_B6;
        }

        public double getFolate_Tot() {
            return Folate_Tot;
        }

        public void setFolate_Tot(double folate_Tot) {
            Folate_Tot = folate_Tot;
        }

        public double getFolic_acid() {
            return Folic_acid;
        }

        public void setFolic_acid(double folic_acid) {
            Folic_acid = folic_acid;
        }

        public double getFood_Folate() {
            return Food_Folate;
        }

        public void setFood_Folate(double food_Folate) {
            Food_Folate = food_Folate;
        }

        public double getFolate_DFE() {
            return Folate_DFE;
        }

        public void setFolate_DFE(double folate_DFE) {
            Folate_DFE = folate_DFE;
        }

        public double getCholine_Tot() {
            return Choline_Tot;
        }

        public void setCholine_Tot(double choline_Tot) {
            Choline_Tot = choline_Tot;
        }

        public double getVit_B12() {
            return Vit_B12;
        }

        public void setVit_B12(double vit_B12) {
            Vit_B12 = vit_B12;
        }

        public double getVit_A_IU() {
            return Vit_A_IU;
        }

        public void setVit_A_IU(double vit_A_IU) {
            Vit_A_IU = vit_A_IU;
        }

        public double getVit_A_RAE() {
            return Vit_A_RAE;
        }

        public void setVit_A_RAE(double vit_A_RAE) {
            Vit_A_RAE = vit_A_RAE;
        }

        public double getRetinol() {
            return Retinol;
        }

        public void setRetinol(double retinol) {
            Retinol = retinol;
        }

        public double getAlpha_Carot() {
            return Alpha_Carot;
        }

        public void setAlpha_Carot(double alpha_Carot) {
            Alpha_Carot = alpha_Carot;
        }

        public double getBeta_Carot() {
            return Beta_Carot;
        }

        public void setBeta_Carot(double beta_Carot) {
            Beta_Carot = beta_Carot;
        }

        public double getBeta_Crypt() {
            return Beta_Crypt;
        }

        public void setBeta_Crypt(double beta_Crypt) {
            Beta_Crypt = beta_Crypt;
        }

        public double getLycopene() {
            return Lycopene;
        }

        public void setLycopene(double lycopene) {
            Lycopene = lycopene;
        }

        public double getLutZea() {
            return LutZea;
        }

        public void setLutZea(double lutZea) {
            LutZea = lutZea;
        }

        public double getVit_E() {
            return Vit_E;
        }

        public void setVit_E(double vit_E) {
            Vit_E = vit_E;
        }

        public double getVit_D_mcg() {
            return Vit_D_mcg;
        }

        public void setVit_D_mcg(double vit_D_mcg) {
            Vit_D_mcg = vit_D_mcg;
        }

        public double getVit_D_IU() {
            return Vit_D_IU;
        }

        public void setVit_D_IU(double vit_D_IU) {
            Vit_D_IU = vit_D_IU;
        }

        public double getVit_K() {
            return Vit_K;
        }

        public void setVit_K(double vit_K) {
            Vit_K = vit_K;
        }

        public double getFA_Sat() {
            return FA_Sat;
        }

        public void setFA_Sat(double FA_Sat) {
            this.FA_Sat = FA_Sat;
        }

        public double getFA_Mono() {
            return FA_Mono;
        }

        public void setFA_Mono(double FA_Mono) {
            this.FA_Mono = FA_Mono;
        }

        public double getFA_Poly() {
            return FA_Poly;
        }

        public void setFA_Poly(double FA_Poly) {
            this.FA_Poly = FA_Poly;
        }

        public double getCholestrl() {
            return Cholestrl;
        }

        public void setCholestrl(double cholestrl) {
            Cholestrl = cholestrl;
        }

        public double getGmWt_1() {
            return GmWt_1;
        }

        public void setGmWt_1(double gmWt_1) {
            GmWt_1 = gmWt_1;
        }

        public String getGmWt_Desc1() {
            return GmWt_Desc1;
        }

        public void setGmWt_Desc1(String gmWt_Desc1) {
            GmWt_Desc1 = gmWt_Desc1;
        }

        public double getGmWt_2() {
            return GmWt_2;
        }

        public void setGmWt_2(double gmWt_2) {
            GmWt_2 = gmWt_2;
        }

        public String getGmWt_Desc2() {
            return GmWt_Desc2;
        }

        public void setGmWt_Desc2(String gmWt_Desc2) {
            GmWt_Desc2 = gmWt_Desc2;
        }

        public double getRefuse_Pct() {
            return Refuse_Pct;
        }

        public void setRefuse_Pct(double refuse_Pct) {
            Refuse_Pct = refuse_Pct;
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

                databaseFood.setNDB_No(Integer.parseInt(delimited[0]));
                databaseFood.setShrt_Desc(delimited[1]);
                databaseFood.setWater(Double.parseDouble(delimited[2]));
                databaseFood.setEnerg_Kcal(Double.parseDouble(delimited[3]));
                databaseFood.setProtein(Double.parseDouble(delimited[4]));
                databaseFood.setLipid_Tot(Double.parseDouble(delimited[5]));
                databaseFood.setAsh(Double.parseDouble(delimited[6]));
                databaseFood.setCarbohydrt(Double.parseDouble(delimited[7]));
                databaseFood.setFiber_TD(Double.parseDouble(delimited[8]));
                databaseFood.setSugar_Tot(Double.parseDouble(delimited[9]));
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
                databaseFood.setVit_C(Double.parseDouble(delimited[20]));
                databaseFood.setThiamin(Double.parseDouble(delimited[21]));
                databaseFood.setRiboflavin(Double.parseDouble(delimited[22]));
                databaseFood.setNiacin(Double.parseDouble(delimited[23]));
                databaseFood.setPanto_acid(Double.parseDouble(delimited[24]));
                databaseFood.setVit_B6(Double.parseDouble(delimited[25]));
                databaseFood.setFolate_Tot(Double.parseDouble(delimited[26]));
                databaseFood.setFolic_acid(Double.parseDouble(delimited[27]));
                databaseFood.setFood_Folate(Double.parseDouble(delimited[28]));
                databaseFood.setFolate_DFE(Double.parseDouble(delimited[29]));
                databaseFood.setCholine_Tot(Double.parseDouble(delimited[30]));
                databaseFood.setVit_B12(Double.parseDouble(delimited[31]));
                databaseFood.setVit_A_IU(Double.parseDouble(delimited[32]));
                databaseFood.setVit_A_RAE(Double.parseDouble(delimited[33]));
                databaseFood.setRetinol(Double.parseDouble(delimited[34]));
                databaseFood.setAlpha_Carot(Double.parseDouble(delimited[35]));
                databaseFood.setBeta_Carot(Double.parseDouble(delimited[36]));
                databaseFood.setBeta_Crypt(Double.parseDouble(delimited[37]));
                databaseFood.setLycopene(Double.parseDouble(delimited[38]));
                databaseFood.setLutZea(Double.parseDouble(delimited[39]));
                databaseFood.setVit_E(Double.parseDouble(delimited[40]));
                databaseFood.setVit_D_mcg(Double.parseDouble(delimited[41]));
                databaseFood.setVit_D_IU(Double.parseDouble(delimited[42]));
                databaseFood.setVit_K(Double.parseDouble(delimited[43]));
                databaseFood.setFA_Sat(Double.parseDouble(delimited[44]));
                databaseFood.setFA_Mono(Double.parseDouble(delimited[45]));
                databaseFood.setFA_Poly(Double.parseDouble(delimited[46]));
                databaseFood.setCholestrl(Double.parseDouble(delimited[47]));
                databaseFood.setGmWt_1(Double.parseDouble(delimited[48]));
                databaseFood.setGmWt_Desc1(delimited[49]);
                databaseFood.setGmWt_2(Double.parseDouble(delimited[50]));
                databaseFood.setGmWt_Desc2(delimited[51]);
                databaseFood.setRefuse_Pct(Double.parseDouble(delimited[52]));

                databaseFoodList.add(databaseFood);
            }
            br.close();
        }
        return databaseFoodList;
    }
}