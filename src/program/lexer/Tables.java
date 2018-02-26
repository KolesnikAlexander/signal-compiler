package program.lexer;


import java.util.*;

public class Tables {
    public static int constCount = 501;
    public static int identifierCount = 1001;

    //CHARACTER TABLES
    public static List<Character> letters = new ArrayList<>(30);
    public static List<Character> digits = new ArrayList<>(100);
    public static List<Character> whitespaces = new ArrayList<>(5);
    public static List<Character> delimiters = new ArrayList<>(10);

    //INPUT TABLES
    public static Map<Integer, TableCell> singleDelim = new HashMap<>();
    public static Map<Integer, TableCell> multDelim = new HashMap<>();
    public static Map<Integer, TableCell> keyWords = new HashMap<>();

    //OUTPUT TABLES
    public static Map<Integer, TableCell> constants = new HashMap<>();
    public static Map<Integer, TableCell> identifiers = new HashMap<>();
    public static List<Lexeme> lexemes = new ArrayList<>(50);

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
        delimiters.add('=');

        //INPUT TABLES
        singleDelim.put(0,new TableCell("(","S-DELIM"));
        singleDelim.put(1,new TableCell(")","S-DELIM"));
        singleDelim.put(2,new TableCell(".","S-DELIM"));
        singleDelim.put(3,new TableCell(",","S-DELIM"));
        singleDelim.put(4,new TableCell(";","S-DELIM"));
        singleDelim.put(5,new TableCell("\'","S-DELIM"));
        singleDelim.put(6,new TableCell("$","S-DELIM"));
        singleDelim.put(7,new TableCell("=","S-DELIM"));

        keyWords.put(401, new TableCell("PROGRAM","KEY"));
        keyWords.put(402, new TableCell("CONST","KEY"));
        keyWords.put(403, new TableCell("BEGIN","KEY"));
        keyWords.put(404, new TableCell("END","KEY"));
        keyWords.put(405, new TableCell("EXP","KEY"));
    }

    public static Integer getKeyByValue(Map<Integer, TableCell> table, TableCell cell){
        Set<Integer> keys = table.keySet();
        for (Integer key : keys) {
            TableCell tableCell = table.get(key);
            if (tableCell.equals(cell))
                return key;
        }

        return null;
    }
}
