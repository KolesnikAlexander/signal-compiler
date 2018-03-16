package program;

import program.lexer.Lexer;
import program.lexer.table.Tables;

import java.util.Scanner;

public class Main {
    private static String FILE = "file.txt";
    private static String FALSE = "falseTest.txt";
    private static String TRUE = "trueTest.txt";

    public static void main(String[] args) {
        String path = inp();

        Tables.initTables();
        Reader.init(TRUE);
        Lexer.run();

        Out.printErrors();
        Out.printLexerResult();
    }

    private static String inp() {
        System.out.print("File path: ");
        return new Scanner(System.in).next();
    }
}
