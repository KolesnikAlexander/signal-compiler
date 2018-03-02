package program;

import program.lexer.Lexer;
import program.lexer.table.Tables;


public class Main {

    public static void main(String[] args) {
        Tables.initTables();
        Reader.init("file.txt");
        Lexer.run();

        Out.printErrors();
        Out.printLexerResult();
    }
}
