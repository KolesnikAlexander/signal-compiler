package program.parser;

import program.lexer.table.Error;
import program.lexer.table.Lexeme;
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
    private static int lexCount;

    private static Iterator<Lexeme> savedIter;

    static {
        lexIter = Tables.lexemes.iterator();
    }

    public static void run(){
        signalProgram();
    }

    private static void saveLexIter(){
        savedIter = Tables.lexemes.iterator();
        for (int i = 0; i < lexCount; i++){
            savedIter.next();
        }
    }

    private static Iterator<Lexeme> copyLexIter(){
        Iterator<Lexeme> copy = Tables.lexemes.iterator();
        for (int i = 0; i < lexCount; i++){
            copy.next();
        }
        return copy;
    }

    private static Iterator<Lexeme> copyLexIterMinusOne(){
        Iterator<Lexeme> copy = Tables.lexemes.iterator();
        for (int i = 0; i < lexCount-1; i++){
            copy.next();
        }
        return copy;
    }

    private static Lexeme readLex(){
        lexCount++;
        if (!lexIter.hasNext())
            return null;
        else
            return lexIter.next();
    }

    private static boolean signalProgram(){
        tree =  new Node(false,"signal-program");
        Result res = program();
        tree.getBranches().add(res.node);
        return res.isSuccessful;
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
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = procedureIdentifier();
        root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = SEMICOLON_KEY();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = block();
        root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = DOT_KEY();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

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
            err("\";\" expected");
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
        Node root = new Node(false,"block");

        Result res = declarations();
        root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = BEGIN_KEY();
        if(!res.isSuccessful){
            return new Result(false, root);
        }
        root.getBranches().add(res.node);

        res = statementsList();
        root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }


        res = END_KEY();
        if(!res.isSuccessful){
            return new Result(false, root);
        }
        root.getBranches().add(res.node);

        return new Result(true, root);

    }

    private static Result END_KEY() {
        int lexCode = 404;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
            err("\"END\" expected");
            return new Result(false, null);
        }
        else{
            //OUT
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result statementsList() {
        Node node =  new Node(false, "statements-list");
        node.getBranches().add(new Node(false, "empty"));
        return new Result(true, node );
    }

    private static Result BEGIN_KEY() {
        int lexCode = 403;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
            err("\"BEGIN\" expected");
            return new Result(false, null);
        }
        else{
            //OUT
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result declarations() {
        Node node = new Node(false, "declarations");
        Result res = constantDeclarations();
        node.getBranches().add(res.node);
        System.err.println("DECLARATIONS: "+l);

        return new Result(res.isSuccessful, node);
    }

    private static Result constantDeclarations() {
        Node root = new Node(false,"constant-declarations");
        Node emptyNode = new Node(false,"empty");
        saveLexIter();

        Result res = CONST_KEY();
        if(!res.isSuccessful){
            lexIter = savedIter;
            root.getBranches().add(emptyNode);
            return new Result(true, root);//empty node
        }
        root.getBranches().add(res.node);

        res = constantDeclarationsList();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
//            lexIter = savedIter;
            return new Result(false, root);
        }

        return new Result(true, root);

    }

    private static Result constantDeclarationsList() {
        Node root = new Node(false,"constant-declarations-list");
        Node emptyNode = new Node(false,"empty");

        Iterator<Lexeme> lexIterCopy = copyLexIter();

        Result res = constantDeclaration();

        root.getBranches().add(res.node);
        if(res.isSuccessful){
            res = constantDeclarationsList();
            root.getBranches().add(res.node);
            return new Result(true, root);
        }
        else {
            lexIter = lexIterCopy;
            return new Result(true, emptyNode );
        }
    }

    private static Result constantDeclaration() {
        Node root = new Node(false,"constant-declaration");

        Result res = constantIdentifier();
        root.getBranches().add(res.node);
        if(!res.isSuccessful){
            System.err.println("KEK"+l);
            return new Result(false, root);
        }

        res = EQUALS();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = constant();
        System.err.println("REWS: "+l);
        root.getBranches().add(res.node);

        if(!res.isSuccessful){
            return new Result(false, root);
        }

//        System.err.println("ROOT: "+root.getBranches().get(0));
        return new Result(true, root);
    }

    private static Result constant() {
        Node root = new Node(false,"constant");

        Result res = QUOTE();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = complexNumber();
        root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = QUOTE();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        System.err.println("AFTER QUOTE: "+l);

        return new Result(true, root);
    }

    private static Result complexNumber1() {
        Node root = new Node(false,"complex-number");

        Result res = leftPart();
        root.getBranches().add(res.node);

        res = rightPart();
        root.getBranches().add(res.node);
        return new Result(true, root);

    }

    private static Result complexNumber() {
        Node root = new Node(false,"complex-number");

        Result res = leftPart();
        root.getBranches().add(res.node);

        if(res.isSuccessful){
            root.getBranches().add(res.node);
            res = rightPart();
            if(res.isSuccessful){
                root.getBranches().add(res.node);
                return new Result(true, root);
            }
            else {
                return new Result(false, root);
            }
        }
        else {
            return new Result(false, root);
        }

    }

    private static Result leftPart() {
        Node node = new Node(false,"left-part");

        Result res = unsignedInteger();
        Iterator<Lexeme> iterCopy = copyLexIter();

        if(res.isSuccessful){
            System.err.println("SUCCESS: "+l);
            node.getBranches().add(res.node);
            return new Result(true, node);

        }
        else{
            System.err.println("UNSUCCESS: "+l);
                lexIter = iterCopy;
            //node.getBranches().add(new Node(false, "empty"));
            return new Result(false, node);

        }



//        node.getBranches().add(new Node(false,"empty"));
//        return new Result(true, node);


//        Node node = new Node(false,"left-part");
//        Iterator<Lexeme> lexIterCopy = copyLexIter();
//        System.err.println("COMPLEX NUMBER LEX: "+l);
//        Result res = unsignedInteger();
//        if (res.isSuccessful){
//            node.getBranches().add(res.node);
//            return new Result(true, node);
//        }
//        else{
//            lexIter = lexIterCopy;
//            System.err.println("COMPLEX NUMBER LEX111111111111: "+l);
//
//            node.getBranches().add(new Node(false,"empty"));
//            return new Result(true, node);
//        }
    }
    private static Result rightPartExp() {
        Node root = new Node(false,"right-part");

        Result res = EXP();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = BRACKET();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = unsignedInteger();
        if(res.isSuccessful){
            root.getBranches().add(res.node);
            return new Result(true, root);
        }
        else{
            root.getBranches().add(new Node(false, "empty"));
            return new Result(false, root);
        }
    }

    private static Result BRACKET() {
        int lexCode = 0;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
//            err("\"=\" expected");
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result EXP() {
        int lexCode = 301;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
//            err("\"=\" expected");
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result rightPart() {
        Node root = new Node(false,"right-part");

        Result res = COMMA();
        if(res.node != null)
            root.getBranches().add(res.node);
        if(!res.isSuccessful){
            return new Result(false, root);
        }

        res = unsignedInteger();
        if(res.isSuccessful){
            System.err.println("SUCCESS: "+l);
            root.getBranches().add(res.node);
            return new Result(true, root);
        }
        else{
            System.err.println("UNSUCCESS: "+l);
            root.getBranches().add(new Node(false, "empty"));
            return new Result(false, root);

        }
    }


    private static Result unsignedInteger() {
        Node node = new Node(false,"unsigned-integer");
        l = readLex();

        if((l == null) || !Lexemes.isConstant(l)){
//            System.err.println("CONSTANT EXPECTED");
            return new Result(false, node);

        }
        else{
            System.err.println("IS CONSTANT: "+l);
            Node idValueNode = new Node(true, l.getCode().toString());
            node.getBranches().add(idValueNode);
            return new Result(true, node);
        }
    }

    private static Result QUOTE() {
        int lexCode = 5;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }
    private static Result COMMA() {
        int lexCode = 3;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
//            err("\"=\" expected");
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }
    private static Result EQUALS() {
        int lexCode = 7;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
//            err("\"=\" expected");
            return new Result(false, null);
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result constantIdentifier() {
        Node root = new Node(false,"constant-identifier");
        Result res = identifierNoErr();
        if(res.isSuccessful){
            root.getBranches().add(res.node);
            return new Result(true, root);
        }
        else{
            return new Result(false, root);
        }
    }

    private static Result identifier() {
        Node node = new Node(false,"identifier");

        l = readLex();

        if((l == null) || !Lexemes.isIdentifier(l)){
            err("Identifier expected");
            return new Result(false, node);
        }
        else{
            //OUT

            Node idValueNode = new Node(true, l.getCode().toString());
            node.getBranches().add(idValueNode);
            return new Result(true, node);
        }
    }
    private static Result identifierNoErr() {
        Node node = new Node(false,"identifier");

        l = readLex();

        if((l == null) || !Lexemes.isIdentifier(l)){
            return new Result(false, node);
        }
        else{
            //OUT

            Node idValueNode = new Node(true, l.getCode().toString());
            node.getBranches().add(idValueNode);
            return new Result(true, node);
        }
    }

    private static Result CONST_KEY() {
        int lexCode = 402;
        l = readLex();

        if(l == null){
            return new Result(false, null); //empty file
        }
        else if(!l.equals(new Lexeme(lexCode, -1, -1))){
            return new Result(false, null);
        }
        else{
            //OUT
            Node node = new Node(true, Integer.toString(lexCode));
            return new Result(true, node);
        }
    }

    private static Result procedureIdentifier() {
        Node node = new Node(false,"procedure-identifier");
        Result res = identifier();
        node.getBranches().add(res.node);
        return new Result(res.isSuccessful, node);
    }
}
