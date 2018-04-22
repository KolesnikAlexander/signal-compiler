package program.parser;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private boolean isTerminal;
    private String value;
    private List<Node> branches;

    public Node(boolean isTerminal,String value) {
        this.value = value;
        this.isTerminal = isTerminal;
        this.branches = new LinkedList<>();
    }

    public Node() {
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
}
