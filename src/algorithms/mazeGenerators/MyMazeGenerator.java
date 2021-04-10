package algorithms.mazeGenerators;
import java.util.List;
import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;

public class MyMazeGenerator extends  AMazeGenerator {
    //cells that have to check them
    Stack<Position> pathStack;
    // walls who optionally might be cells path
    ArrayList<Position> neigbors;
    //number of visited node
    int visited;

    public MyMazeGenerator() {
        pathStack = new Stack<Position>();
        neigbors = new ArrayList<Position>();
    }

    /**
     * creates maze with only walls (1)
     */
    private void fillMaze() {

        int rowS = maze.getMazeRows();
        int colS = maze.getMazeCols();

        for (int i = 0; i < maze.getMazeRows(); i++) {
            for (int j = 0; j < maze.getMazeCols(); j++) {
                maze.setCell(i, j, 1);
            }
        }
    }


    /**
     * @param x position in maze from x-numboer of row
     * @param y position in maze from x-numboer of col
     *  add to neighbor all the posibole next postion for the DFS maze genratore
     */
    private void GetAvailableneighbors(int x, int y) {
        if (x + 2 < maze.getMazeRows() && x + 2 >= 0 && y < maze.getMazeCols() && y >= 0)
            if (maze.maze[x + 2][y] == 1) {
                Position pos = new Position(x + 2, y);
                this.neigbors.add(pos);
            }
        if (x - 2 >= 0 && x - 2 < maze.getMazeRows() && y < maze.getMazeCols() && y >= 0)
            if (maze.maze[x - 2][y] == 1) {
                Position pos = new Position(x - 2, y);
                this.neigbors.add(pos);
            }
        if (y - 2 >= 0 && y - 2 < maze.getMazeCols() && x < maze.getMazeRows() && x >= 0)
            if (maze.maze[x][y - 2] == 1) {
                Position pos = new Position(x, y - 2);
                this.neigbors.add(pos);
            }
        if (y + 2 < maze.getMazeCols() && y + 2 >= 0 && x < maze.getMazeRows() && x >= 0)
            if (maze.maze[x][y + 2] == 1) {
                Position pos = new Position(x, y + 2);
                this.neigbors.add(pos);
            }

    }

    /**
     * @param src source postion
     * @param target next postion
     *               the func break the wall btw the two postions
     */
    private void breakWalls(Position src, Position target) {
        if (src.getRowIndex() == target.getRowIndex())
            if (src.getColumnIndex() > target.getColumnIndex()) {
                maze.maze[target.getRowIndex()][target.getColumnIndex() + 1] = 0;
                maze.maze[target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        if (src.getRowIndex() == target.getRowIndex())
            if (src.getColumnIndex() < target.getColumnIndex()) {
                maze.maze[target.getRowIndex()][target.getColumnIndex() - 1] = 0;
                maze.maze[target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        if (src.getColumnIndex() == target.getColumnIndex())
            if (src.getRowIndex() < target.getRowIndex()) {
                maze.maze[target.getRowIndex() - 1][target.getColumnIndex()] = 0;
                maze.maze[target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        if (src.getColumnIndex() == target.getColumnIndex())
            if (src.getRowIndex() > target.getRowIndex()) {
                maze.maze[target.getRowIndex() + 1][target.getColumnIndex()] = 0;
                maze.maze[target.getRowIndex()][target.getColumnIndex()] = 0;
            }
    }

    /**
     * @param array give arrandom postion from the array
     * @return
     */
    public static Position getRandom(ArrayList<Position> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }


    /**
     * genreate the maze by the DFS algo
     */
    private void dfsGenerate() {
        Position current = new Position(maze.startRow, maze.startCol);
        Position choosen;
        maze.setCell(current.getRowIndex(), current.getColumnIndex(), 0);
        pathStack.push(current);

        while (pathStack.empty() == false) {
            current = pathStack.pop();
            this.GetAvailableneighbors(current.getRowIndex(), current.getColumnIndex());
            if (neigbors.size() > 0) {
                pathStack.push(current);
                choosen = getRandom(neigbors);
                breakWalls(current, choosen);
                current = choosen;
                neigbors.clear();
                pathStack.push(current);
            }

        }
    }

    /**
     * create goal postion to the maze
     */
        private void createEnd() {
        int strow = maze.getStartRow();
        int stcol= maze.getStartCol();
        boolean flag = true;
            while (flag = true) {
                createStartEnd();
                if(maze.getMazeRows()>2 && maze.getMazeCols()>2){
                if (maze.maze[maze.getEndRow()][maze.getEndCol()]==1)
                    continue;}
                if(maze.getEndRow()== strow && maze.getEndCol()==stcol)
                    continue;
                break;
            }
            maze.setStartRow(strow);
            maze.setStartCol(stcol);
        }

    /**
     * @param row number or row
     * @param col number of cols
     * @return genrerate the maze
     */
        @Override
        public Maze generate( int row , int col ) {
            if(!(legalSize(row,col)))
                return null;
            maze = new Maze(row, col);

            fillMaze();
            createStartEnd();

            dfsGenerate();

            createEnd();

            return this.maze;
        }


    }