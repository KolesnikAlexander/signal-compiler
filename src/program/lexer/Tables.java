package program.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tables {
//    Character tables
    public static List<Character> letters = new ArrayList<>(30);
    public static List<Character> digits = new ArrayList<>(100);
    public static List<Character> whitespaces = new ArrayList<>(5);
    public static List<Character> delimiters = new ArrayList<>(10);
    //INPUT TABLES
    public static Map<Integer, TableCell> keyWords = new HashMap<>();
    public static Map<Integer, TableCell> multDelim = new HashMap<>();
    public static Map<Integer, TableCell> singleDelim = new HashMap<>();

    //OUTPUT TABLES
    public static Map<Integer, TableCell> constants = new HashMap<>();
    public static Map<Integer, TableCell> identifiers = new HashMap<>();

    //LEXEME TABLE
    public static Map<Integer, Lexeme> lexemes = new HashMap<>();

    public static void initTables(){
       //letters
        letters.add('A');
        letters.add('B');
        letters.add('C');
        letters.add('D');
        letters.add('E');
        letters.add('F');
        letters.add('G');
        letters.add('H');
        letters.add('I');
        letters.add('J');
        letters.add('K');
        letters.add('L');
        letters.add('M');
        letters.add('N');
        letters.add('O');
        letters.add('P');
        letters.add('Q');
        letters.add('R');
        letters.add('S');
        letters.add('T');
        letters.add('U');
        letters.add('V');
        letters.add('W');
        letters.add('X');
        letters.add('Y');
        letters.add('Z');

      //digits
        digits.add('0');
        digits.add('1');
        digits.add('2');
        digits.add('3');
        digits.add('4');
        digits.add('5');
        digits.add('6');
        digits.add('7');
        digits.add('8');
        digits.add('9');

      //whitespaces
        whitespaces.add('\n');
        whitespaces.add('\r');
        whitespaces.add('\t');
        whitespaces.add(' ');

      //delimiters
        delimiters.add('(');
        delimiters.add(')');
        delimiters.add('.');
        delimiters.add(',');
        delimiters.add(';');
        delimiters.add('\'');
        delimiters.add('$');
    }
}
