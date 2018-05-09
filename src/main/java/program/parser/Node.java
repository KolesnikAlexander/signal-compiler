package program.parser;

import java.util.LinkedList;
import java.util.List;

import static program.lexer.LexerOut.tableCellByCode;

public class Node {
    private boolean isTerminal;
    private String value;
    private List<Node> branches;
    private int line;
    private int row;

    public Node(boolean isTerminal,String value) {
        this.value = value;
        this.isTerminal = isTerminal;
        this.branches = new LinkedList<>();
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Node> getBranches() {
        return branches;
    }

    public Node child(String name){
        for (Node node : this.getBranches()) {
            if (node.getValue().equals(name))
                return node;
        }
        return null;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Node firstChild(){
        return this.getBranches().isEmpty()? null: this.getBranches().get(0);
    }

    public String terminalStr(){
        return tableCellByCode(Integer.parseInt(this.value)).getValue();
    }

    @Override
    public String toString() {

        if (this.isTerminal){
            String str = tableCellByCode(Integer.parseInt(this.value)).getValue();
            return this.value + " "+str;
        }
        else{
            return "<"+value+">";
        }
    }
}
