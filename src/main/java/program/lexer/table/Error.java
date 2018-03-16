package program.lexer.table;

public class Error {
    private int line;
    private int row;
    private String message;

    public Error(int line, int row, String message) {
        this.line = line;
        this.row = row;
        this.message = message;
    }

    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return line+":"+row+"   "+message;
    }
}
