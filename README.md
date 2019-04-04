## Match and Partial Match for Regular Expression

### PartialMatch for the Regular Expression

The regular expression describes the format of the string. 
The object PartialMatch checks if the given string answers to the conditions
of the regular expression.  For example, the string ^\w describes the 
expression containing one or more symbols.  For this string, the object
PartialMatch  returns ‘FULL MATCH’ for the string “Hello”
as well as for the string “World”. However, the object PartialMatch  returns
‘NO PARTIAL MATCH’ for the string “Hello World”
