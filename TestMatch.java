package CheckMatcher;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMatch
{	
    public static void main( String args[] ){
        
       Reader reader = new Reader();       

       // reader.readLinesRegexInput("D:\\RegexInputFile.txt");
       // reader.readLinesRegexInput("E:\\RegexInputFile.txt");
       reader.readLinesRegexInput("I:/Regex/RegexInputFile.txt");
              		
       ArrayList<LineRegexInput> arrayLInes = reader.getArrayListOfRegexInput();
       
       PartialMatch pMatch = new PartialMatch();

       pMatch.loopByAllRegexAndInputLines(arrayLInes);
    }    
}