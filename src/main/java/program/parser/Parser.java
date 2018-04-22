package program.parser;

import program.lexer.table.Error;
import program.lexer.table.Lexeme;
import program.lexer.table.TableCell;
import program.lexer.table.Tables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static program.Out.tableCellByCode;

public class Parser {
    public static List<Error> errors = new ArrayList<>();
    public static Node tree;
    public static Lexeme l;
    private static Iterator<Lexeme> lexIter;

    static {
        lexIter = Tables.lexemes.iterator();
    }

    public static void run(){
        signalProgram();
    }

    private static Lexeme readLex(){
        if (!lexIter.hasNext())
            return null;
        else
            return lexIter.next();
    }

    private static boolean signalProgram(){
        tree =  new Node(false,"signal-program");
        return program().isSuccessful;
    }

    private static void err(String message){
        if (l == null){
            Lexeme lexeme = Tables.lexemes.get(Tables.lexemes.size() - 1);
            String value = tableCellByCode(lexeme.getCode()).getValue();
            int line = lexeme.getLine();
            int row = lexeme.getRow() + value.length();
            errors.add(new Error(line, row, message));
        }
        else
            errors.add(new Error(l.getLine(), l.getRow(), message));
    }


    private static Result program() {
        Node root = new Node(false,"program");

        Result res = PROGRAM_KEY();
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        root.getBranches().add(res.node);
        res = procedureIdentifier();
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        root.getBranches().add(res.node);
        res = SEMICOLON_KEY();
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        root.getBranches().add(res.node);
        res = block();
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        root.getBranches().add(res.node);
        res = DOT_KEY();
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        root.getBranches().add(res.node);
        return new Result(true, root);

    }

    private static Result DOT_KEY() {
        int lexCode = 2;
        l = readLex();

        if((l == null) || !l.equals(new Lexeme(lexCode, -1, -1))){
            err("\".\" expected");
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result SEMICOLON_KEY() {
        int lexCode = 4;
        l = readLex();

        if((l == null) || !l.equals(new Lexeme(lexCode, -1, -1))){
            err("\"SEMICOLON\" expected");
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result PROGRAM_KEY(){
        int lexCode = 401;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
            //ERR EOF
//            System.out.println("ERROR!");
            err("\"PROGRAM\" expected");
            return new Result(false, null);
        }
        else{
            //OUT
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result block() {
        Node node = new Node(false,"block");
        return new Result(true, node);
    }
    private static Result procedureIdentifier() {
        Node node = new Node(false,"procedure-identifier");

        l = readLex();

        if((l == null) || !Lexemes.isIdentifier(l)){
            //ERR EOF
//            System.out.println("Identifier expected");
            err("Identifier expected");

            return new Result(false, null);
        }
        else{
            //OUT
            Node idNode = new Node(false,"identifier");
            Node idValueNode = new Node(true, l.getCode().toString());

            System.out.println("ID: "+l.getCode().toString());

            idNode.getBranches().add(idValueNode);
            node.getBranches().add(idNode);
            return new Result(true, node);
        }


//
//        Lexeme id = getFirstId();
//        if (id == null){
//            //ERR EOF
//            System.out.println("ERROR!");
//            return false;
//        }
//        else{
//            //OUT
//            parent.branches.add(new Node("PROGRAM"));
//            System.out.println("PROGRAM FOUND!");
//            return true;
//        }
    }
}
