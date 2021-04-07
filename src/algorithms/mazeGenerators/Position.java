package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRowIndex() {
        return row;
    }


    public int getColumnIndex() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }



    public String toString() {
        return "{" + row + ',' + col + '}';
    }
    public boolean equals(Position obj) {
        if(row==obj.row && col==obj.col)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return (row+col)%13;
    }


}