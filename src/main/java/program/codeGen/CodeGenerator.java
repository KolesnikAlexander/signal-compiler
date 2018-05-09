package program.codeGen;

import program.parser.Node;
import program.parser.Parser;

import java.util.LinkedList;
import java.util.List;

public class CodeGenerator {
    private static Node tree = Parser.tree;
    public static List<String> listing = new LinkedList<>();
    public static List<String> labels = new LinkedList<>();
    private static String segmentName;
    public static void run(){
        program(tree.child("program"));
    }

    private static void program(Node node) {
        procedureIdentifier(node.child("procedure-identifier"));
        constantDeclarations(node.child("block").child("declarations")
                .child("constant-declarations"));
        listing.add("?"+segmentName +" ENDS");

    }

    private static void constantDeclarations(Node node) {
        if (node.child("empty") != null)
            return;
        constantDeclarationsList(node.child("constant-declarations-list"));

    }

    private static void constantDeclarationsList(Node node) {
        if (node.child("empty") != null)
            return;
        constantDeclaration(node.child("constant-declaration"));
        constantDeclarationsList(node.child("constant-declarations-list"));
    }

    private static void constantDeclaration(Node node) {
        String id = regIdentifier(node.child("identifier"));
        if(id == null){
            System.err.println("IDENTIFIER EXISTS!");
            return;
        }
        else{
            Node complexNum = node.child("constant").child("complex-number");
            Node left = complexNum.child("left-part");
            Node right = complexNum.child("right-part");

            leftPart(left, id);
            rightPart(right, id);
        }
    }

    private static void leftPart(Node node, String id) {
        String integer;
        if (node.child("empty") != null)
            integer = "0";
        else
            integer = unsignedInteger(node.child("unsigned-integer"));

        listing.add("?"+ id +"_R DD "+integer);
    }

    private static String unsignedInteger(Node node) {
//        String id = node.firstChild().terminalStr();
        return node.firstChild().terminalStr();
    }

    private static void rightPart(Node node, String id) {
        String integer;
        if (node.child("empty") != null)
            integer = "0";
        else
            integer = unsignedInteger(node.child("unsigned-integer"));

        listing.add("?"+ id +"_IM DD "+integer);
    }


    private static void procedureIdentifier(Node node) {
        String id = regIdentifier(node.child("identifier"));
        if(id == null){
            System.err.println("IDENTIFIER EXISTS!");
            return;
        }
        listing.add("?"+ id +" SEGMENT");
        segmentName = id;
    }

    private static String regIdentifier(Node node){
        String id = node.firstChild().terminalStr();
        if(labels.contains(id))
            return null;
        else {
            labels.add(id);
            return id;
        }
    }

    private static void segment(Node noe) {
    }

}
