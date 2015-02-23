package edu.uml.diet;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/** Class utilized to read in data from USDA food database
 *
 * @author Ray Goolishian
 */
public class DbParserTest {
    String filename;
    String foodName;
    DbParser dbp;
    File file;
    int lengthOfFile;

    @Before
    public void setup() throws IOException{
        filename = "./USDAdatabase.txt";
        file = new File(filename);
        lengthOfFile = 0;

        /**get length of file
         *
         */
        FileReader fr = new FileReader(file);
        LineNumberReader lnr = new LineNumberReader(fr);
        while (lnr.readLine() != null) {
            lengthOfFile++;
        }


        dbp = new DbParser();
        foodName = "TURTLE,GREEN,RAW";
    }

    /**Test that all records were imported
     *  and that all records were imported properly by
     *  checking last record name
     */
    @Test
    public void testImportDatabase(){
        ArrayList<DbParser.dbFood> dbFoodList = dbp.importDatabase(filename);

        //test that all lines imported
        assertEquals(lengthOfFile,dbFoodList.size());

        // test that name of food in last record matches ASCII file
        assertEquals(dbFoodList.get(dbFoodList.size() - 1).getShrt_Desc(),foodName);
    }
}
