## Match and Partial Match for Regular Expression

## 1. PartialMatch for the Regular Expression

   The regular expression describes the format of the string. The object _PartialMatch_ checks if the given string answers to the     conditions of the regular expression.  For example, the string ^\w describes the  expression containing one or more symbols. For this    string, the object _PartialMatch_ returns **FULL MATCH** for the string “Hello” as well as for the string “World”. However, the object   _PartialMatch_  returns **NO PARTIAL MATCH** for the string “Hello World”.    

## 2.	The algorithm description, the class PartialMatch

### The main idea of the algorithm 

   The main idea of the algorithm is to get all substrings of the given regular expression _Regex_ and to apply the method   
_hitEnd()_ of the class _java.util.regex.Matcher_. If the examined string _Input_ matches some substring of _Regex_ then this    substring _Input_ can be completed to the string which is in full match to _Regex_. The proof of the last suggestion is not so     trivial, but it is    checked on all pairs contained in the file _RegexInputFile_.     

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

### The validity of the regular expression

The method compile(String str) checks the validity of the regular expression of the substrings taken from regex. This method throws     exception defined by the class java.util.regex.PatternSyntaxException.  The first time this exception is caught for the full regular     expression regex. In the case of not valid string regex (i.e. the exception is caught), the analysis is stopped, see method      

                   private boolean checkSyntaxException(String regex).

of the class PartialMatch. The second time this exception is caught for every substring of regex analyzed in the loop. In this case     
of “not valid string”, the analysis is continued.  This exception is caught as follows:      
  
     try{
         p = Pattern.compile(substrRegex);
      }catch(java.util.regex.PatternSyntaxException  e){
             System.out.println(“PatternSyntaxException: " + 
             e.getMessage() + ",however, the analysis will be continued \n");
       continue;
      }

###   More checks in isPartialMatch(), class SpecialCharacters

For each substring of regex, some additional checks are performed. These checks are carried out by the following methods of     
the class _SpecialCharacters_.   Apparently, some of these methods can be dropped,  then the exception    java.util.regex.PatternSyntaxException will be generated.  However, possibly, the further developing of checks of the class      _SpecialCharacters_ can be used for better understanding of the lexical problems of regular expressions.  

   isQuantifier(String str,  int i)

The substring sustrRegex (substring of Regex) can’t start from a quantifier  (i.e., from symbols _*, +, ?_). The method        
_isQuantifier()_ checks this situation. 

   isMetachar2bytes(String str, int i)
    
   This method checks that the current two bytes are started from the backslash “\”. For example, consider the regular expression      “\w…”.  The substring starting from the second byte is “w…” can be valid and don’t generate “exception”, but can generate wrong    
 value of methods matches() and hitEnd().  If isMetachar2bytes() returns TRUE,  the method isPartialMatch() increases index i by 2.      
   unmatchedBrackedPair(String regex, String substrRegex, int i)

For any type of parenthesis, it is checked that closing parenthesis is not preceded by an opening parenthesis, only if it is not the      case of escaped parenthesis like “\(“ or “\[“  or “\{“.   The method  _unmatchedBrackedPair()_ responsible for this case.

   unclosedParenthesis(String str, int i)

For any opening parenthesis, the substring substrRegex is checked for existence of a closing parenthesis,  only if it is not the case of escaped parenthesis like “\(“ or “\[“  or “\{“.  


## 3. Description of classes in the package CheckMatcher

The package CheckMatcher contains 5 classes and the following methods:   

## TestMatch    

public class TestMatch   //  (TestMatch.java)
      public static void main(String args[])  
     
Testing of all pairs (Regex, Input) given in the file RegexInputFile.
     
## Reader

public class  Reader      // (Reader.java)
     public boolean readLinesRegexInput(String fileName)
     public ArrayList<LineRegexInput>  getArrayListOfRegexInput()
     private String[] tokenize(String line)
     private String getSecondToken(String line,  StringTokenizer strTr)

Read from the file all lines containing pairs (Regex, Input) and tokenize them. 
     
## LineRegexInput   

public class LineRegexInput   //  (LineRegexInput.java)
     public LineRegexInput (String reg, String inp)  //  ctor
     public String getRegex()
     public String getInput()

The class contains only one pair (Regex, Input) and access methods for these fields. 

## PartialMatch

public class PartialMatch    //  (PartialMatch.java)
     public Boolean isPartialMatch(String regex, String input) // The principal method
     public void loopByAllRegexAndInputLines(ArrayList<LineRegexInput>  arrayLines)
     private Boolean checkSyntaxException(String regex)

The class contains the principal method _isPartialMatch(String regex, String input)_.
This method uses classes _java.util.regex.Matcher_,  _java.util.regex.Pattern_  and 
_java.util.regex.PatternSyntaxException_ of the package _java.util.regex_.  
   
## SpecialCharacters    

 public class SpecialCharacters    //  (SpecialCharacters.java)
    public boolean isQuantifier(String str,  int i)
    public boolean isMetachar2bytes(String str, int i)
    public boolean unmatchedBrackedPair(String regex, String substrRegex, int i)
    public boolean unclosedParenthesis(String str, int i)
    private Boolean unmatchedBr(String regex, String substrRegex, int i,  char leftBr,  char rightBr)

This class contains the methods performing addition checks for the _substrRegex_.


## 4. Testing,  the classes Reader,  LineRegexInput 

### Loop by all pairs (Regex, Input), class TestMatch

All  lines (_Regex_, _Input_) are contained in the file _RegexInputFile.txt_
The class _TestMatch_ (method _main_) reads all lines from this file and saves them in the array  
ArrayList<LineRegexInput> arrayLines. Then TestMatch launches the method   

   public void loopByRegexAndInputLine(ArrayList<LineRegexInput>  arrayLines)  

from the class _PartialMatch.

### Read from the file RegexInputFile.txt, class Reader

The method 

   public  boolean readLinesRegexInput(String file)
   
of the class _Reader_ reads all lines from the file = “filename”,  the current file name is _RegexInputFile.txt_.   
Every line of the file is the line (Regex, Input).   Any line containing the symbol “;” at the position 0 is   
the comment line which is dropped.  Any empty line also will be dropped. If string _Input_ contains spaces then    
use quotes like "Hello World".  If string Input contains quotes  then use single quotes like    
'<a href="http://net.tutsplus.com/">Nettuts+</a>'. This allows check any string containing quotes " or single quotes '.  

### Tokenizer,  the class LineRegexInput

The method _readLinesRegexInput()_ calls the method _tokenize(String line)_ for any pair (_Regex_, _Input_).    
The metod  tokenize() uses the class java.util.StringTokenizer. The method _getSecondToken(String str, StringTokenizer strTr)_    
is used for getting parameter Input taking into account all conditions related  to quotes mentioned above.    

For every pair (_Regex_, _Input_) the new instance of the class LineRegexInput is created and added to the _arrayLines_ 
of the type _ArrayList<LineRegexInput>._ 

## 5. Test cases 

The file RegexInputFile.txt contains 86 pairs (_Regex_, _Input_) intended for testing.  
There are 4 groups of scenarios:  **‘Partial Match’**,  **‘Match’**,  **‘No match’**, **‘Syntax exception’**.  
For the typical examples of these groups, see below.    

### ‘Partial Match’ examples of (_Regex_, _Input_) 

r((ege)|(geg))x                                rege    
r((ege)|(geg))x                                egex    
^\w+\s+\w+$                                  "     "
^2(2[4-9]|3\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$   224   
a{3,5}bcde                                     bcd    
a\{3bcd\}                                      bcd   
\[bcde\]                                       cde]  
\d{5}                                          1234
/\d{2}-\d{5}                                   12-34 
/\d{2}-\d{5}                                   12-12345
^(\d+)\.(\d+)\.(\d+)\.(\d+)$                   177.255.255     

### ‘Match’ examples of (_Regex_, _Input_) 

r((ege)|(geg))x                                regex  
^2(2[4-9]|3\\d)\.                              224.
[a-z]*                                         hello
bbba+                                          bbbaaa
a{3,5}b                                        aaab
\[.\]                                          [f]
\d{5}                                          12345
\w+                                            ______
^[hc]at                                        hat
s.*                                            sequence
^(\d+)\.(\d+)\.(\d+)\.(\d+)$                   177.255.255.255

### ‘No Match’ examples of (_Regex_, _Input_) 
^\w+$                                          "Hello World"
^\w+$                                          "     "
^\w+\s+\w+$                                    "Hello World!"
[0-9]abc                                       lab
r((ege)|(geg))x                                regexx
^2(2[4-9]|3\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$      224.
[a-z]                                          hello
\[bcde\]                                       cde\]
^[a-z0-9-]+$                                    my_title_here


### ‘Syntax Exception’ examples of (_Regex_, _Input_)

cde)fgh                              fgh
******                              123
?\d*                                   12

### Additional test cases that need to be done

1.	PERL oriented regular extensions   
2.	Lazy quantification scenarios    
3.	Unicode cases   
4.	Scenarios containing hex values   
5.	Scenarios containing  URL slugs   
6.	More anchoring scenarios    
7.	More comprehensive scenarios with grouping and parentheses

## 6. References

[Wiki](http://en.wikipedia.org/wiki/Regular_expression)     
[MSDN](http://msdn.microsoft.com/en-us/library/az24scfc%28v=vs.110%29.aspx)         
[Quick Reference](http://www.autohotkey.com/docs/misc/RegEx-QuickRef.htm)    

 ### Java and Regular Expressions

[java.util.regex.Pattern](http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html)   
[java.util.regex.Matcher](http://docs.oracle.com/javase/7/docs/api/java/util/regex/Matcher.html)   
[java.util.regex.PatternSyntaxException](http://docs.oracle.com/javase/7/docs/api/java/util/regex/PatternSyntaxException.html)     
[Java Regex Tutorial](http://www.vogella.com/tutorials/JavaRegularExpressions/article.html)


### Java methods _matches()_  and _hitEnd()_

[Java Regular Expressions](http://www.tutorialspoint.com/java/java_regular_expressions.htm)    
[Partial Matches](http://stackoverflow.com/questions/2526756/can-java-util-regex-pattern-do-partial-matches)     
[Trouble Implementing Partial Matches](http://stackoverflow.com/questions/7085389/trouble-implementing-partial-matches-with-regular-expression-on-android)         
[Pattern Matching strange GBehaviour](http://stackoverflow.com/questions/14441070/java-pattern-matching-strange-behaviour)   









_


