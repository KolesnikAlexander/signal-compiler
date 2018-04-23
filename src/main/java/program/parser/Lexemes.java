package program.parser;

import program.lexer.table.Lexeme;
import program.lexer.table.Tables;

public class Lexemes {
    public static boolean isIdentifier(Lexeme lexeme){
        return Tables.identifiers.containsKey(lexeme.getCode());
    }
    public static boolean isConstant(Lexeme lexeme){
        return Tables.constants.containsKey(lexeme.getCode());
    }
}
