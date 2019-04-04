package CheckMatcher;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class PartialMatch {

    private SpecialCharacters mChar = new SpecialCharacters();
    
    private boolean checkSyntaxException(String regex){
    	
    	   try {
   	    	  Pattern.compile(regex);
		   } catch (PatternSyntaxException e) {
			  System.out.printf("PatternSyntaxException: " + e.getMessage());
			  return false;
		   }
    	   
    	   return true;
    }
    	
    public boolean isPartialMatch(String regex, String input){ 

		   if (!checkSyntaxException(regex)){
			   return false;
		   }
		
	       int len = regex.length();	       
	       
	       // The loop along the Regex
	       for (int i = 0; i < len; i++ ) // 2 - is the len of "ABX"
	       {

		     String substrRegex = regex.substring(i);  
  
	    	 // Check if char is "*", "+", "?" or other quantifier
	    	 // The Regex can't start form quantifier  
	         if (mChar.isQuantifier(regex, i)){
	        	 continue;
	         } 
	         
	         // unclosed paranthesis, then regex is wrong
	         if (!mChar.unclosedParenthesis(regex, i)){
	        	 continue;
	         }
	         
	         // Is one of pairs '{ }', '[ ]', '( )' unmatched ? 
	         if (!mChar.unmatchedBracketPair(regex, substrRegex, i)){
	        	continue; 
	         }
	             
	    	 Pattern p = null;
	    	 
	    	 try {
	   	    	 p = Pattern.compile(substrRegex);
			 } catch (PatternSyntaxException e) {
				  System.out.printf("PatternSyntaxException: " + e.getMessage() +
						            ", however, the analysis will be continued \n");
				  continue;
			 }

	    	 Matcher m = p.matcher(input);
	    	 
	         boolean bMatch = m.matches();
	         boolean bHit = m.hitEnd();

	         
	         if (bMatch){   
	           if (i == 0){	// Full Match on Regex 
	              System.out.printf(
	   	        	  "FULL MATCH: " + 
	                  "Pattern: %s, Input = %s, matches: %B \n", substrRegex, input, bMatch);
	   	          return true;
	           }
	           else { // i > 0. 'Full Match' on substring of Regex is interpreted as 'PARTIAL'
	        	   System.out.printf(  
	 	   	          "PARTIAL MATCH ('Input' is not equal to 'Regex'): " + 
	 	              "Pattern: %s, Input = %s, matches: %B \n", substrRegex, input, bMatch); 
	        	   return true;
	           }
	         }
	         
	         if (bHit){ // Partial Match 
	           System.out.printf(
	        	  "PARTIAL MATCH: "  +   
	        	  "Pattern: %s, Input = %s, matches: %B, hitEnd match: %B \n",
	        	  substrRegex, input, bMatch, bHit);
	           return true;
	         }

	         // if two byte meta-character then increase index twice
	         if (mChar.isMetachar2bytes(regex,i)){
	    	    i++;
	    	    if (i >= len){
	    	    	System.out.printf("This is the metacharacter in the position: %d\n", i - 1);
	    	    	return false;
	    	    }
	         }

	       } //end of the loop along the Regex
	       
	       return false;
	   }
 			
    public void loopByAllRegexAndInputLines(ArrayList<LineRegexInput> arrayLInes){   

    	// Loop by Regex and Input
        for (LineRegexInput line :  arrayLInes){ 
  
     	    boolean partialMatchSuccess = false;
     	  
     	    String regex = line.getRegex();
     	    String input = line.getInput(); 
        
         	System.out.printf("\n" + "Regex: " + regex + ", Input: " + input + "\n"); 
     	    partialMatchSuccess = isPartialMatch(regex, input);
     	   
            if (partialMatchSuccess){
         	   System.out.printf("There is a match for Regex: " + regex + " and Input: " + input + "\n");    	   
            }
            else{
         	   System.out.printf("No even partial match for Regex: " + regex + " and Input: " + input + "\n");
            }
        }   
    }
}