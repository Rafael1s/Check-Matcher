## Match and Partial Match for Regular Expression

### PartialMatch for the Regular Expression

   The regular expression describes the format of the string. The object _PartialMatch_ checks if the given string answers to the     conditions of the regular expression.  For example, the string ^\w describes the  expression containing one or more symbols. For this    string, the object _PartialMatch_ returns **FULL MATCH** for the string “Hello” as well as for the string “World”. However, the object   _PartialMatch_  returns **NO PARTIAL MATCH** for the string “Hello World”.    

### The main idea of the algorithm 

   The main idea of the algorithm is to get all substrings of the given regular expression **Regex** and to apply the method   
_hitEnd()_ of the class _java.util.regex.Matcher_. If the examined string _Input_ matches some substring of **Regex** then this    substring _Input_ can be completed to the string which is in full match to **Regex**. The proof of the last suggestion is not so     trivial, but it is    checked on all pairs contained in the file _RegexInputFile_.     

   Consider an example:  _Regex = “[A-F][a-f]*[0-9]*_. The string _Input_ = _abcd1234_ is in the full match with the sub-expression     _[a-f]*[0-9]*”_  The string Input can be completed, e.g. to _Eabcd1234_ which is already in full match with **Regex**.These examples      are taken from the file _RegexInputFile_.        

   The main algorithm is implemented in Java, in the class _PartialMatch_. The classes _java.util.regex.Matcher_, _java.util.regex.Pattern_  and _java.util.regex.PatternSyntaxException_ of the package _java.util.regex_  are used in this implementations. The principal method of the class PartialMatch is  
                __public  boolean  isPartialMatch(String regex,  String input)__  

The input parameters are the regular expression **Regex** and the examined string _Input_. 


### The key loop of analysis, the method  isPartialMatch 

The key method used in _isPartialMatch()_ is the method _hitEnd()_ from the class _java.util.regex.Matcher._ This method returns   
TRUE if there is a partial match between the string input and substring substrRegex of the regular expression regex. The metod     _hitEnd()_ checks the partial match for all substrings substrRegex starting from the position 0 of substrRegex. The algorithm performs    the loop by the length of the string regex.  Assume the length of the string regex is n.  The algorithm analyzes all substrings substrRegex of regex from positions k (k=0,1,…n-1) to n-1 and  performs the method hitEnd(). Thus, all substrings  of regex are checked. 

### The scheme of analysis  

In addition to the method hitEnd(),  we use also the method_compile(String str)_ of classes _java.util.regex.Pattern_ and the method    matches() from the class _java.util.regex.Matcher._The schematic sequence of calls is as follows:      

      Pattern p = Pattern.compile(substrRegex);
      Matcher  =  p.matcher(input);
      boolean bMatch = m.matches();
      boolean bHit  = m.hitEnd();

  The method matches() returns TRUE if and only if there is the full match between substrRegex  and input. The method hitEnd()      returns TRUE if and only if there is the partial match between some substring  starting at the position 0 of substrRegex  and input.     

