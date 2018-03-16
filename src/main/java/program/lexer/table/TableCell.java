package program.lexer.table;

import java.util.Objects;

public class TableCell {
    private String value;
    private String type;

    public TableCell(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableCell tableCell = (TableCell) o;
        return Objects.equals(value, tableCell.value) &&
                Objects.equals(type, tableCell.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, type);
    }

    @Override
    public String toString() {
        return "    "+ value + "    " + type + "    " +"\n";
//        return String.format("%1$"+8+"s\n", type);
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
