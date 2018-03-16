package program.lexer;

import program.lexer.table.Tables;

public class Characters {
    public static boolean isDigit(Character c){
        int d = (int)c;
        if (d < 0 || d > 127)
            return false;
        return Tables.ascii[(int)c] == 1;
        //return Tables.digits.contains(c);
    }
    public static boolean isLetter(Character c){
        int d = (int)c;
        if (d < 0 || d > 127)
            return false;
        return Tables.ascii[(int)c] == 2;
        //return Tables.letters.contains(c);
    }
    public static boolean isWhitespace(Character c){
        int d = (int)c;
        if (d < 0 || d > 127)
            return false;
        return Tables.ascii[(int)c] == 4;
        //return Tables.whitespaces.contains(c);
    }
    public static boolean isDelemiter(Character c){
        int d = (int)c;
        if (d < 0 || d > 127)
            return false;
        return Tables.ascii[(int)c] == 3;
//        return Tables.delimiters.contains(c);
    }
}
