package CheckMatcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Reader {
 
  private final String skipLine = "skip";
  private final char chQutes = '"';  
  private final String quotes = Character.toString(chQutes);
  private final String singleQuote = "'"; 
  
  private ArrayList<LineRegexInput> arrayLines = new ArrayList();
  
  public ArrayList<LineRegexInput> getArrayListOfRegexInput(){
	  return arrayLines;
  }
	
  public boolean readLinesRegexInput(String file) {
    BufferedReader br;

    // System.out.printf("SingleQuote: ", singleQuote + "\n");
    
	try {
		br = new BufferedReader(new FileReader(file));
	} catch (FileNotFoundException e) {
		System.out.println("ReadRegexsInputs: FileNotFoundException " + e.getMessage());
		return false;
	}

	// The structure of any line should be as follows:
	// Regex  Input
	// If 'Input' contains spaces, we use format with quotes "Any Input" or 'Any Input'

    String line;
    try {
		while ((line = br.readLine()) != null) {
			String[] tokens = tokenize(line);
			if (tokens[0].equals(skipLine)){
				continue;
			}
			
			String regex = tokens[0];
			String input = tokens[1];
			
			LineRegexInput lineRegexInput = new LineRegexInput(regex, input);
			arrayLines.add(lineRegexInput);			
		}
	} catch (IOException e) {
		System.out.println("ReadRegexsInputs: IOException: readLine " + e.getMessage());
		return false;
	}
    
    try {
		br.close();
	} catch (IOException e) {
		System.out.println("ReadRegexsInputs: IOException: close " + e.getMessage());
	}
    
    System.out.printf("Size of the array of pairs (Regex, Input): " + arrayLines.size() + "\n");
    
    return true;
  }
  
  
  // The structure of any line should be as follows:
  // regex input
  // If Input conains spaces, the structure is as followsL
  // regex "any   input" 
  // If input contains only spaces the structure is:
  // regex "    "
  private String[] tokenize(String line) {
	   
	   // System.out.printf("tokenize is entered for line: " + line + "\n");
	   
	   // Skip empty line
	   String checkEmpty = line.trim();
	   if (checkEmpty.length() < 2){ 
		   String[] tokens = new String[1];
		   tokens[0] = skipLine;
		   // System.out.println("Skip empty line: " + checkEmpty);
		   return tokens;
	   }
	  
	   // Count tokens
	   StringTokenizer st = new StringTokenizer(line);
	   int count = 0;
	   String elem;
	   while (st.hasMoreTokens()) {
		   elem = st.nextToken();
		   // Skip line if the first letter is ';' (comment)
		   if (count == 0 && elem.charAt(0) == ';'){ 
			   String[] tokens = new String[1];
			   tokens[0] = skipLine;
			   return tokens;
		   }
		   count++;
	   }
	   
	   String[] tokens = new String[count];
	   // Skip line if the structure contains < 2 tokens,
	   // i.e. the line is not {regex input}, 	   
	   if (count < 2){
		   tokens[0] = skipLine;
		   return tokens;		   
	   }
	   	   
	   // Save the line (regex, input) into tokens
	   StringTokenizer strTr = new StringTokenizer(line);
	   
	   // Get The first token (Regex)
	   if (strTr.hasMoreTokens()){
	       tokens[0] =  new String(strTr.nextToken());
	   }
	   else {
		   tokens[0] = skipLine;
		   return tokens;			   
	   }
	  	 
	   tokens[1] = getSecondToken(line, strTr);

	   // System.out.printf("tokens[0] = " + tokens[0] + ", tokens[1] = " + tokens[1] + "\n");
	   return tokens;

  }  

  private String getSecondToken(String line, StringTokenizer strTr){
	  
	   int posQuotes = line.indexOf(quotes); 
	   int posSingleQuote = line.indexOf(singleQuote);
	   
	   if (posQuotes > 0 || posSingleQuote > 0){
		   
		   String lineFromPos = null;
		   int pos2 = 0;
		   
		   if (posSingleQuote == -1){
			   lineFromPos = line.substring(posQuotes + 1);
			   pos2 = lineFromPos.indexOf(quotes);
		   }
		   else {
		     if (posQuotes == -1) {
			    lineFromPos = line.substring(posSingleQuote + 1);
			    pos2 = lineFromPos.indexOf(singleQuote);
		     }
		     else {	 //  Here posQuotes > 0 and  posSingleQuote > 0
		       if (posQuotes < posSingleQuote){
			      lineFromPos = line.substring(posQuotes + 1);
			      pos2 = lineFromPos.indexOf(quotes);
		       }
		       else{ // posSingleQuote < posQuotes
			      lineFromPos = line.substring(posSingleQuote + 1);
			      pos2 = lineFromPos.indexOf(singleQuote);
		       }
		     }
		   } 
		   
		   //System.out.printf("lineFromPos = " + lineFromPos + ", pos2 " + pos2 + 
			//	   ", posQuotes: " + posQuotes + ", posSingleQuote: " + posSingleQuote); 
		   return lineFromPos.substring(0, pos2);
	   }

	   // No delimter 'Quotes' or 'Single_Quote'
	   if(strTr.hasMoreTokens()) {
	         return strTr.nextToken();
	   }
	   else {
		   return skipLine;
	   }
   }     
}