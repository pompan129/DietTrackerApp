package test.java.edu.uml.diet;

import main.java.edu.uml.diet.DbParser;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raymond on 2/15/2015.
 */
public class DbParserTest {
    String filename;
    String foodName;
    DbParser dbp;
    File file;
    int lengthOfFile;

    @Before
    public void setup(){
        filename = "./USDAdatabase.txt";
        file = new File(filename);
        lengthOfFile = 0;

        //get length of file
        try {
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);
            while (lnr.readLine() != null) {
                lengthOfFile++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        dbp = new DbParser();
        foodName = "TURTLE,GREEN,RAW";
    }

    @Test
    public void testImportDatabase(){
        ArrayList<DbParser.dbFood> dbFoodList = dbp.importDatabase(filename);

        //test that all lines imported
        assertEquals(lengthOfFile,dbFoodList.size());

        // test that name of food in last record matches ASCII file
        assertEquals(dbFoodList.get(dbFoodList.size() - 1).Shrt_Desc,foodName);
    }
}
