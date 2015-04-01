package edu.uml.diet;

import edu.uml.diet.persistence.DbParser;
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
    private String filename;
    private String foodName;
    private DbParser dbParser;
    private File file;
    private int lengthOfFile;

    @Before
    public void setup() throws IOException{
        filename = "src/main/resources/asciiFoodDatabase.txt";
        file = new File(filename);
        lengthOfFile = 0;

        /**get length of file
         *
         */
        FileReader fileReader = new FileReader(file);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        while (lineNumberReader.readLine() != null) {
            lengthOfFile++;
        }


        dbParser = new DbParser();
        foodName = "TURTLE,GREEN,RAW";
    }

    /**Test that all records were imported
     *  and that all records were imported properly by
     *  checking last record name
     */
    @Test
    public void testImportDatabase() throws IOException{
        ArrayList<DbParser.databaseFood> databaseFoodList = dbParser.importDatabase(filename);

        //test that all lines imported
        assertEquals(lengthOfFile, databaseFoodList.size());

        // test that name of food in last record matches ASCII file
        assertEquals(databaseFoodList.get(databaseFoodList.size() - 1).getName(),foodName);
    }
}
