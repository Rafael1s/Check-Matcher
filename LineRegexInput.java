package CheckMatcher;

public class LineRegexInput {
 
   private String regex;
   private String input;
 
   public LineRegexInput(String reg, String inp){
	   regex = reg;
	   input = inp;
   }
 
   public String getRegex(){
	   return regex;
   }
   
   public String getInput(){
	   return input;
   }
 
}