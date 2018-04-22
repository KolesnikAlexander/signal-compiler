package program;

import program.lexer.Lexer;
import program.lexer.table.Tables;
import program.parser.Parser;
import program.parser.ParserOut;

import java.util.Scanner;

public class Main {
    private static String FILE = "file.txt";
    private static String FALSE = "falseTest.txt";
    private static String TRUE = "trueTest.txt";
    private static String TEST = "test.txt";

    public static void main(String[] args) {
        //String path = inp();

        Tables.initTables();
        Reader.init(TEST);
        Lexer.run();

        Out.printLexerResult();
        Out.printErrors();

        if(Tables.errors.size() != 0)
            return;

        Parser.run();
        ParserOut.outErrors();
    }

    private static String inp() {
        System.out.print("File path: ");
        return new Scanner(System.in).next();
    }
}
