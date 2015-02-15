package main.java.edu.uml.diet;

import java.io.*;
import java.util.*;

/**
 * Created by Raymond on 2/15/2015.
 */
public class DbParser {

    private InputStreamReader reader;

    // container class for all food properties
    public class dbFood{
        public int NDB_No;
        public String Shrt_Desc;
        public double Water;
        public double Energ_Kcal;
        public double Protein;
        public double Lipid_Tot;
        public double Ash;
        public double Carbohydrt;
        public double Fiber_TD;
        public double Sugar_Tot;
        public double Calcium;
        public double Iron;
        public double Magnesium;
        public double Phosphorus;
        public double Potassium;
        public double Sodium;
        public double Zinc;
        public double Copper;
        public double Manganese;
        public double Selenium;
        public double Vit_C;
        public double Thiamin;
        public double Riboflavin;
        public double Niacin;
        public double Panto_acid;
        public double Vit_B6;
        public double Folate_Tot;
        public double Folic_acid;
        public double Food_Folate;
        public double Folate_DFE;
        public double Choline_Tot;
        public double Vit_B12;
        public double Vit_A_IU;
        public double Vit_A_RAE;
        public double Retinol;
        public double Alpha_Carot;
        public double Beta_Carot;
        public double Beta_Crypt;
        public double Lycopene;
        public double LutZea;
        public double Vit_E;
        public double Vit_D_mcg;
        public double Vit_D_IU;
        public double Vit_K;
        public double FA_Sat;
        public double FA_Mono;
        public double FA_Poly;
        public double Cholestrl;
        public double GmWt_1;
        public String GmWt_Desc1;
        public double GmWt_2;
        public String GmWt_Desc2;
        public double Refuse_Pct;
    }

    // parse delimited ANSI file to create list of all foods from USDA database
    public ArrayList<dbFood> importDatabase(String filePath) {

        //initialize list to be returned
        ArrayList<dbFood> dbFoodList = new ArrayList<dbFood>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String record = null;

            while ((record = br.readLine()) != null) {
                String[] strAry = record.split("\\^");
                String[] delimited = new String[53];
                int i = 0;

                // make sure all records contain 54 fields
                while(i < strAry.length)                    {
                    delimited[i] = strAry[i];
                    i++;
                }
                while(i < 53){
                    delimited[i] = "";
                    i++;
                }

                dbFood dbf = new dbFood();

                int t = delimited.length;
                for(i = 0; i < 53; i++){

                    delimited[i] = delimited[i].replace("~", "");
                    if(delimited[i].isEmpty()){
                        delimited[i] = "0";
                    }
                }

                dbf.NDB_No = Integer.parseInt(delimited[0]);
                dbf.Shrt_Desc = delimited[1];
                dbf.Water = Double.parseDouble(delimited[2]);
                dbf.Energ_Kcal = Double.parseDouble(delimited[3]);
                dbf.Protein = Double.parseDouble(delimited[4]);
                dbf.Lipid_Tot = Double.parseDouble(delimited[5]);
                dbf.Ash = Double.parseDouble(delimited[6]);
                dbf.Carbohydrt = Double.parseDouble(delimited[7]);
                dbf.Fiber_TD = Double.parseDouble(delimited[8]);
                dbf.Sugar_Tot = Double.parseDouble(delimited[9]);
                dbf.Calcium = Double.parseDouble(delimited[10]);
                dbf.Iron = Double.parseDouble(delimited[11]);
                dbf.Magnesium = Double.parseDouble(delimited[12]);
                dbf.Phosphorus = Double.parseDouble(delimited[13]);
                dbf.Potassium = Double.parseDouble(delimited[14]);
                dbf.Sodium = Double.parseDouble(delimited[15]);
                dbf.Zinc = Double.parseDouble(delimited[16]);
                dbf.Copper = Double.parseDouble(delimited[17]);
                dbf.Manganese = Double.parseDouble(delimited[18]);
                dbf.Selenium = Double.parseDouble(delimited[19]);
                dbf.Vit_C = Double.parseDouble(delimited[20]);
                dbf.Thiamin = Double.parseDouble(delimited[21]);
                dbf.Riboflavin = Double.parseDouble(delimited[22]);
                dbf.Niacin = Double.parseDouble(delimited[23]);
                dbf.Panto_acid = Double.parseDouble(delimited[24]);
                dbf.Vit_B6 = Double.parseDouble(delimited[25]);
                dbf.Folate_Tot = Double.parseDouble(delimited[26]);
                dbf.Folic_acid = Double.parseDouble(delimited[27]);
                dbf.Food_Folate = Double.parseDouble(delimited[28]);
                dbf.Folate_DFE = Double.parseDouble(delimited[29]);
                dbf.Choline_Tot = Double.parseDouble(delimited[30]);
                dbf.Vit_B12 = Double.parseDouble(delimited[31]);
                dbf.Vit_A_IU = Double.parseDouble(delimited[32]);
                dbf.Vit_A_RAE = Double.parseDouble(delimited[33]);
                dbf.Retinol = Double.parseDouble(delimited[34]);
                dbf.Alpha_Carot = Double.parseDouble(delimited[35]);
                dbf.Beta_Carot = Double.parseDouble(delimited[36]);
                dbf.Beta_Crypt = Double.parseDouble(delimited[37]);
                dbf.Lycopene = Double.parseDouble(delimited[38]);
                dbf.LutZea = Double.parseDouble(delimited[39]);
                dbf.Vit_E = Double.parseDouble(delimited[40]);
                dbf.Vit_D_mcg = Double.parseDouble(delimited[41]);
                dbf.Vit_D_IU = Double.parseDouble(delimited[42]);
                dbf.Vit_K = Double.parseDouble(delimited[43]);
                dbf.FA_Sat = Double.parseDouble(delimited[44]);
                dbf.FA_Mono = Double.parseDouble(delimited[45]);
                dbf.FA_Poly = Double.parseDouble(delimited[46]);
                dbf.Cholestrl = Double.parseDouble(delimited[47]);
                dbf.GmWt_1 = Double.parseDouble(delimited[48]);
                dbf.GmWt_Desc1 = delimited[49];
                dbf.GmWt_2 = Double.parseDouble(delimited[50]);
                dbf.GmWt_Desc2 = delimited[51];
                dbf.Refuse_Pct = Double.parseDouble(delimited[52]);

                dbFoodList.add(dbf);
            }

            br.close();

        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return dbFoodList;
    }
}