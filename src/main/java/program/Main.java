package program;

import program.lexer.Lexer;
import program.lexer.table.Tables;
import program.parser.Parser;
import program.parser.ParserOut;

import java.util.Scanner;

public class Main {
    private static String FALSE1 = "parserFalse1.txt";
    private static String FALSE2 = "parserFalse2.txt";
    private static String FALSE3 = "parserFalse3.txt";
    private static String TRUE = "parserTrue.txt";
    private static String TEST = "test.txt";

    public static void main(String[] args) {
        //String path = inp();

        Tables.initTables();
        Reader.init(FALSE3);
        Lexer.run();

        Out.printErrors();
        Out.printLexerResult();

        if(Tables.errors.size() != 0)
            return;

        Parser.run();
        System.out.println("\n---------\nPARSER");
        ParserOut.outErrors();
        ParserOut.treeOut();
    }

    private static String inp() {
        System.out.print("File path: ");
        return new Scanner(System.in).next();
    }
}
