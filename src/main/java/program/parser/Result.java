package program.parser;

public class Result {
    public boolean isSuccessful;
    public Node node;

    public Result(boolean isSuccessful, Node node) {
        this.isSuccessful = isSuccessful;
        this.node = node;
    }
}
