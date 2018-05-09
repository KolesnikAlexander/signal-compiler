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
    public static Lexeme buffer;
    private static Iterator<Lexeme> lexIter;
    public static Node emptyNode = new Node(false, "empty");

    static {
        lexIter = Tables.lexemes.iterator();
    }

    public static void run(){
        signalProgram();
        if(errors.isEmpty() && watch() != null){
            readLex();
            err("Token after '.' found");
        }
    }

    private static Lexeme getNextLexItem(){
        return !lexIter.hasNext()? null:lexIter.next();
    }

    private static Lexeme watch(){
        if(buffer == null)
            buffer = getNextLexItem();
        return buffer;
    }

    private static void readLex(){
        Lexeme lex = watch();
        buffer = null;
        l = lex;
    }

    private static boolean nullLex(){
        return l == null;
    }

    private static boolean signalProgram(){
        tree =  new Node(false,"signal-program");
        Node program = new Node(false, "program");
        tree.getBranches().add(program);

        return PROGRAM_KEY(program) && procedureIdentifier(program)
                && SEMICOLON_KEY(program) && block(program) && DOT_KEY(program);
    }

    private static boolean PROGRAM_KEY(Node parent){
        return key(parent,401, "PROGRAM");
    }

    private static boolean procedureIdentifier(Node parent) {
        Node node = new Node(false,"procedure-identifier");
        parent.getBranches().add(node);
        return identifier(node);
    }

    private static boolean identifier(Node parent) {
        Node node = new Node(false,"identifier");
        parent.getBranches().add(node);
        readLex();

        if(nullLex() || !Lexemes.isIdentifier(l)){
            err("Identifier expected");
            return false;
        }
        else{
            Node idValueNode = new Node(true, l.getCode().toString());
            idValueNode.setLine(l.getLine());
            idValueNode.setRow(l.getRow());
            node.getBranches().add(idValueNode);
            return true;
        }
    }

    private static boolean SEMICOLON_KEY(Node parent) {
        return key(parent,4, ";");
    }

    private static boolean DOT_KEY(Node parent) {
        return key(parent,2, ".");
    }

    private static boolean block(Node parent) {
        Node node = new Node(false,"block");
        parent.getBranches().add(node);
        return declarations(node) && BEGIN_KEY(node) &&
                statementsList(node) && END_KEY(node);
    }

    private static boolean declarations(Node parent) {
        Node nodeDeclarations = new Node(false, "declarations");
        parent.getBranches().add(nodeDeclarations);
        Node node = new Node(false, "constant-declarations");
        nodeDeclarations.getBranches().add(node);

        if (!new Lexeme(402, -1, -1).equals(watch())) {//next is not CONST
            node.getBranches().add(emptyNode);
            return true;
        }
        return CONST_KEY(node) && constantDeclarationsList(node);
    }

    private static boolean constantDeclarationsList(Node parent) {
        Node node = new Node(false, "constant-declarations-list");
        parent.getBranches().add(node);
        if (watch() == null || !Lexemes.isIdentifier(watch())){
            node.getBranches().add(emptyNode);
            return true;
        }
        return constantDeclaration(node) && constantDeclarationsList(node); // TODO: 28.04.18
    }

    private static boolean constantDeclaration(Node parent) { // TODO: 28.04.18
        Node node = new Node(false, "constant-declaration");
        parent.getBranches().add(node);
        return constantIdentifier(node) && EQUALS_KEY(node) && constant(node) && SEMICOLON_KEY(node);
    }

    private static boolean constantIdentifier(Node parent) {
        Node node = new Node(false, "constant-identifier");
        return identifier(parent);
    }

    private static boolean EQUALS_KEY(Node parent) {
        return key(parent,7, "=");
    }

    private static boolean constant(Node parent) {
        Node node = new Node(false, "constant");
        parent.getBranches().add(node);
        return QUOTE(node) && complexNumber(node) && QUOTE(node);
    }

    private static boolean complexNumber(Node parent) {
        Node node = new Node(false, "complex-number");
        parent.getBranches().add(node);
        return leftPart(node) && rightPart(node);
    }

    private static boolean leftPart(Node parent) {
        Node node = new Node(false, "left-part");
        parent.getBranches().add(node);
        if (watch() == null || !Lexemes.isConstant(watch())){
            node.getBranches().add(emptyNode);
            return true;
        }
        return unsignedInteger(node);
    }

    private static boolean unsignedInteger(Node parent) {
        Node node = new Node(false,"unsigned-integer");
        parent.getBranches().add(node);
        readLex();

        if(nullLex() || !Lexemes.isConstant(l)){
            err("Constant expected");
            return false;
        }
        else{
            Node idValueNode = new Node(true, l.getCode().toString());
            node.getBranches().add(idValueNode);
            return true;
        }
    }

    private static boolean rightPart(Node parent) {
        Node node = new Node(false, "right-part");
        parent.getBranches().add(node);
        if(!nullLex() && new Lexeme(3, -1, -1).equals(watch())){
            return rightPart1(node);
        }
        else if(!nullLex() && new Lexeme(301, -1, -1).equals(watch())){
            return rightPart2(node);
        }
        else{
            node.getBranches().add(emptyNode);
            return true;
        }
    }

    private static boolean rightPart1(Node parent) {
        return COMMA_KEY(parent) && unsignedInteger(parent);
    }
    private static boolean rightPart2(Node parent) {
        return EXP(parent) && OPEN_BRACKET(parent) &&
                unsignedInteger(parent) && CLOSE_BRACKET(parent);
    }


    private static boolean EXP(Node parent) {
        return key(parent, 301, "$EXP");
    }

    private static boolean OPEN_BRACKET(Node parent) {
        return key(parent, 0, "(");
    }

    private static boolean CLOSE_BRACKET(Node parent) {
        return key(parent, 1, ")");
    }

    private static boolean COMMA_KEY(Node parent) {
        return key(parent, 3, ",");
    }

    private static boolean key(Node parent, int lexCode, String errMsg){
        readLex();

        if(nullLex() || !l.equals(new Lexeme(lexCode, -1, -1))){
            err("\""+errMsg+"\" expected");
            return false;
        }
        else{
            Node node = new Node(true, Integer.toString(lexCode));
            parent.getBranches().add(node);
            return true;
        }
    }
    private static boolean QUOTE(Node parent) {
        return key(parent,5,"'" );

    }

    private static boolean CONST_KEY(Node parent) {
        return key(parent,402, "CONST");
    }

    private static boolean CONSTN_KEY(Node parent) {
        return true;
    }
    private static boolean BEGIN_KEY(Node parent) {
        return key(parent, 403, "BEGIN");
    }

    private static boolean statementsList(Node parent) {
        Node statListNode = new Node(false, "statements-list");
        parent.getBranches().add(statListNode);
        statListNode.getBranches().add(emptyNode);
        return true;
    }

    private static boolean END_KEY(Node parent) {
        return key(parent, 404, "END");
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
}
