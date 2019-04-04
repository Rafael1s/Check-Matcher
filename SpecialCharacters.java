package CheckMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpecialCharacters {
	
	private final String allQuantifiers = "+*?";
	
	
    // Check if char is "*", "+", "?" or other quantifier
    // The match (or partial match) can not start form quantifier
    public boolean isQuantifier(String str, int i){
    	
    	 String quant = str.substring(i,  i+1);
    	 
    	 if (i >= str.length()){
    		 System.out.printf("The index of the string " + str + " > than " +  "\n");
    		 return false;
    	 }
    	 
	     if (allQuantifiers.contains(quant)) {
	    	System.out.printf("Quantifier = " + quant + "\n");
	    
			return true;
		 }
	     else {
		    return false;
	     }
    }
    
    // Check if char is the meta-character like "\w", "\d", "\s", ...
    public boolean isMetachar2bytes(String str, int i){
    	char ch = str.charAt(i);
    	    	
    	if (ch == '\\'){
    		System.out.printf("isMetachar2bytes: i = " + i + ", char: " +  str.charAt(i+1) + "\n");
    		return true;
    	}
    	else {
    	    return false;	
    	}
   	 }
    
    // Is one of pairs '{ }', '[ ]', '( )' unmatched ? 
    private boolean unmatchedBr(String regex, String substrRegex, int i,  char leftBr, char rightBr){
     	 
    	 int posRight =  regex.indexOf(rightBr, i);
     	 
    	 // the right bracket is not found
    	 if (posRight == -1){ 
    		 return true;
    	 }
    	 
    	 // the right bracket in the start
    	 if (posRight == 0){
    	    return false;	 
    	 }
    	 
     	 if (posRight > 0) {
   	   	    if (regex.charAt(posRight - 1) == '\\'){
   			    return true;
   	        }
   		    else {
   			   int posLeft = regex.indexOf(leftBr, i);
   			   if ((posRight < posLeft) || posLeft == -1){
   				 System.out.printf("Unmatched closing " + rightBr + 
   						 ", i = " + i + " for regex substr  " + substrRegex + "\n");
   		  		    return false;
   			   }
   		    }
     	 }
     	 
   	     return true;    	
    }
    
    // Check all pairs '{ }', '[ ]', '( )'
    public boolean unmatchedBracketPair(String regex, String substrRegex, int i){
    	
    	if (!unmatchedBr(regex, substrRegex, i, '(', ')')){
    		return false;
    	}
    	
    	if (!unmatchedBr(regex, substrRegex, i, '[', ']')){
    		return false;
    	}

    	if (!unmatchedBr(regex, substrRegex, i, '{', '}')){
    		return false;
    	}
    	
    	return true;
    }
    
    
    public boolean unclosedParenthesis(String str, int i){
    	 
    	 // No ')'
    	 if ((str.charAt(i) == '(') && (str.indexOf(')', i) == -1)){
    		 System.out.printf("Unclosed paranthesis ')'");
    		 return false;
    	 }
    	 
    	 // No ']'
    	 if ((str.charAt(i) == '[') && (str.indexOf(']', i) == -1)){
    		 System.out.printf("Unclosed paranthesis ']'");
    		 return false;
    	 }

    	 // No '}'
    	 if ((str.charAt(i) == '{') && (str.indexOf('}', i) == -1)){
    		 System.out.printf("Unclosed paranthesis '}'");
    		 return false;
    	 }
    	 
    	 return true;
    }

}