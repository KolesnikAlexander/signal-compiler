package program;

import program.codeGen.CodeGenOut;
import program.codeGen.CodeGenerator;
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
//LEXER
        Tables.initTables();
        Reader.init(TRUE);
        Lexer.run();
//LEXER PRINT
        if(Tables.lexemes.isEmpty()){
            System.out.println("Empty file");
            return;
        }

        System.out.println("LEXER");
        Out.printErrors();
        Out.printLexerResult();
//PARSER
        if(Tables.errors.size() != 0)
            return;
        Parser.run();
//PARSER PRINT
        System.out.println("\n---------\nPARSER");
        ParserOut.outErrors();
        System.out.println();
        ParserOut.treeOut();

        System.out.println("==========================");

        if(!Parser.errors.isEmpty())
            return;
        else{
            CodeGenerator.run();
            CodeGenOut.printListing();
        }
    }

    private static String inp() {
        System.out.print("File path: ");
        return new Scanner(System.in).next();
    }
}
