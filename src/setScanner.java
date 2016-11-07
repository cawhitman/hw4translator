import java.util.*;
/*

A class to perform the lexical analysis for the translation project.

It contains instance specific data members

1. src: the Scanner origin of the source code text
2. currToken: the current token
3. currLine: a char array of a line from src from which currToken was drawn
4. currPos: the position within currLine from which the next token will be searched for
5. currLineNumber: the index of currLine within the src, with the first line regarded as at index 1

Class invariants

1. src is not null
2. currToken is not null
3. currLine is not null; if there is no next line, it will be set to the empty array;
   we can imagine that even an empty file has one line on it that contains the end of file
   marker
4. 0 <= currPos <= currLine.length
5. currLineNumber >= 1

It provides methods

1. getting methods for all data members except src and currLine(the getter for
   currToken is the lookahead method)
2. void consume(): fetches the next token from src into currToken and updates the
   other data members
3. Token lookahead(): returns currToken

You can use alternative data members if you think you have a better approach, but the
behavior your implementation leads to should match the spec given here and provide the
same public methods and constructor.

****************************************************************************/

public class setScanner{

    private Scanner src;
    private Token currToken;
    // NOTE: NO SINGLE TOKEN CAN STRADDLE A LINE BOUNDARY
    private char[] currLine = {};
    private int currPos; // to record where we are on the current line
    private int currLineNumber;

    public setScanner(Scanner s) throws Exception{
        if (s == null)
            throw new Exception("Invalid Scanner input to setScanner constructor.");

        src = s;
        // load currToken with the first token
        currLineNumber = 1;
        currPos = 0;
        currLine = src.nextLine().toCharArray();

        this.consume();
    }

    public int getCurrLineNumber(){
        return currLineNumber;
    }

    public int getCurrPos(){
        return currPos;
    }

    // returns the current token w/o advancing
    public Token lookahead(){
        return currToken;
    }

    public void consume(){
   /*

     Note, the Character wrapper class has a static method, isWhitespace, that
     can be used to test if a char value is WS.

     If not already at end of file, this method advances to the next
     token in src.

     If the current token is already EOF this operation has no effect.

     Otherwise, skip over WS in src until either end of file is reached
     or a non WS char is reached.  You need to do this line by line to keep
     track of the current line number.  That is, scan the current line, but
     if you reach the end of the line w/o finding a non WS character, then
     fetch the next line and continue.

     If you reach the end of file, then that is the next(and last) token.
     Construct it and assign it to currToken, using a line number that is
     one greater than the index of the last line read.

     If you reach some non WS character, then starting at that character
     determine the longest prefix of non WS characters on that line that
     matches one of the tokens.  None of the non-letter operators or
     punctuation are prefixes of each other, so the only issue is with
     reserved words versus identifiers.  For example, the occurrences of
     "program" in the line


     program p program; program+ program, program\ program?

     are the reserved word, but the occurrences in the following line,

     program1 programABC programprogram programA1Z2

     are parts of instances of identifiers.  Similarly, the line

     000000

     is six occurrences of the nat constant token, since 0 is the longest
     prefix that matches any token type.  Also, the line

     CMPCMP then1 endif2 else3 begin4 end5 if6 var7 nat8 set9 not10 inABC

     has no reserved words, but twelve identifiers.

     If no prefix matches(for example, no token begins with '?', so if '?'
     were encountered there would be no match), then scan from the current
     position in currLine until a WS character or until the end of the line,
     and construct an instance of the "unrecognized" token from those non WS
     characters.

     Construct the Token object for currToken, including its type and the
     currLineNumber.  If the token type is nat constant, identifier, or unrecognized,
     you should also include the characters from currLine that the token comprises,
     as a String.

     Leave currPos at the index of the character AFTER the last symbol of the
     token.  If the token is not EOF, currLineNumber will be the index of the line
     from which the token was drawn.

     This is not too hard.  Assuming the first scan does not reach end of file,
     if you consider all the characters that a token can begin with-- call that
     set S-- then a rough breakdown after skipping to the first non WS character
     ch is

     if (ch is not in S){
         scan to the next ws character and construct the unrecognized token
         and put it in currToken, updating the other data members
     }
     else{
        if (ch is a decimal digit)
          construct the longest prefix from ch that is in the nat constant pattern,
          create the token and assign it to currToken, updating the data members
        else if (ch is a letter)
           construct the longest prefix from ch is an identifier or a reserved word
           create the appropriate token and assign it to currToken, updating the data members
        else// ch is not a letter or decimal digit
           check if the unique token that begins with ch can be completed(there are
           only two such tokens that need another character)

           if yes
             complete it, assign to currToken, etc.
           else
              scan to next WS character, create appropriate instance of UNRECOGNIZED,
              assign it to currToken, etc.
        }


   */

        StringBuilder sb = new StringBuilder();
        ArrayList<Character> specialTokens = new ArrayList<>();
        specialTokens.add(',');
        specialTokens.add(';');
        specialTokens.add('{');
        specialTokens.add('}');
        specialTokens.add('.');
        specialTokens.add('(');
        specialTokens.add(')');

        if(currPos == currLine.length - 1) {
            if (src.hasNextLine()){
                currLine = src.nextLine().toCharArray();
                currLineNumber += 1;
                currPos = 0;
            }else{
                currToken = new Token(29,currLineNumber);
                return;
            }
        }

        String tokenString = null;

        for(int tokenPosition= currPos; tokenPosition < currLine.length; tokenPosition++){
            if(!Character.isWhitespace(currLine[tokenPosition])){
                if(currPos == currLine.length-1){
                    tokenString = sb.toString();
                    break;
                }
                // Test for special characters , : { } . ( )
                if(specialTokens.contains(currLine[tokenPosition])){
                    sb.append(currLine[tokenPosition]);
                    currPos +=1;
                    tokenString = sb.toString();
                    break;
                }
                // Test for special characters <= and :=
                if(currLine[tokenPosition]=='<' || currLine[tokenPosition]==':' ){
                    sb.append(currLine[tokenPosition]);
                    currPos +=1;

                    if(tokenPosition+1 < currLine.length && currLine[tokenPosition+1]=='=' ){
                        sb.append(currLine[tokenPosition]);
                        currPos +=1;
                        break;
                    }
                }
                sb.append(currLine[tokenPosition]);
                currPos +=1;
            } else if(sb.length()>0) {
                currPos += 1;
                tokenString = sb.toString();
                break;
            } else {
                currPos +=1;
            }
        }

        //call decode
        currToken = new Token(tokenString,currLineNumber);
    }
}

