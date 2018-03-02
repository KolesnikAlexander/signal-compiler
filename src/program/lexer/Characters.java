package program.lexer;

import program.lexer.table.Tables;

public class Characters {
    public static boolean isDigit(Character c){
        return Tables.digits.contains(c);
    }
    public static boolean isLetter(Character c){
        return Tables.letters.contains(c);
    }
    public static boolean isWhitespace(Character c){
        return Tables.whitespaces.contains(c);
    }
    public static boolean isDelemiter(Character c){
        return Tables.delimiters.contains(c);
    }
}
