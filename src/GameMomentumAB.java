import java.util.ArrayList;

public class GameMomentumAB extends NodeGameAB {

    private static final int BOARD_SIZE = 7;
    private static final int CENTER_POSITION = 3;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] newBoard;
    private int myColor;
    private int newR, newC;

    public GameMomentumAB(String node) {
        super(1);
        myColor = getPlayer();
        processNode(node);
    }

    public GameMomentumAB(int[][] p, int myColor, int depth) {
        super(depth);
        for (int l = 0; l < BOARD_SIZE; l++)
            for (int c = 0; c < BOARD_SIZE; c++)
                this.board[l][c] = p[l][c];
        this.myColor = myColor;
    }

    public void processNode(String node) {
        String[] v = node.trim().split(" ");
        for (int l = 0; l < BOARD_SIZE; l++)
            for (int c = 0; c < BOARD_SIZE; c++)
                try {
                    board[l][c] = Integer.parseInt(v[l * BOARD_SIZE + c]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("board " + v + "  l " + l + "  c " + c);
                }
    }

    @Override
    public ArrayList<Move> expandAB() {
        ArrayList<Move> successors = new ArrayList<>();

        newBoard = makeCopy(board);

        if (!boardEmpty(newBoard)) {
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    if (newBoard[r][c] == 0) {
                        GameMomentumAB successorBoard = new GameMomentumAB(performMove(newBoard, r, c), myColor, getDepth() + 1);
                        successors.add(new Move((r + 1) + " " + (c + 1), successorBoard));
                    }
                }
            }
        }

        else {
            GameMomentumAB centerBoard = new GameMomentumAB(performMove(newBoard, 3, 3), myColor, getDepth() + 1);
            successors.add(new Move("4 4", centerBoard));
            return successors;
        }

        return successors;
    }

    private boolean boardEmpty(int[][] newBoard) {
        boolean empty= true;
        for (int r = 0; r < BOARD_SIZE; r++){
            for (int c = 0; c < BOARD_SIZE; c++){
                if(newBoard[r][c]!=0){
                    empty = false;
                }
            }
        }
        return empty;
    }

    private int[][] performMove(int[][] newBoard, int r, int c) {

        newR = 0;
        newC = 0;

        if (newBoard[r][c] == 0) {
            newBoard[r][c] = myColor;

            // Mover para cima
            if (r - 1 >= 0 && newBoard[r - 1][c] != 0) {
                newR = r - 1;
                while (newR > 0 && newBoard[newR][c] != 0) {
                    newR--;
                }

                if (newR == 0) {
                    newBoard[newR][c] = 0;
                } else {
                    newBoard[newR][c] = newBoard[newR + 1][c];
                    newBoard[newR + 1][c] = 0;
                }
            }

            // Mover para baixo
            if (r + 1 < 7 && newBoard[r + 1][c] != 0) {
                newR = r + 1;
                while (newR < 6 && newBoard[newR][c] != 0) {
                    newR++;
                }

                if (newR != 7) {
                    newBoard[newR][c] = newBoard[newR - 1][c];
                }
                newBoard[newR - 1][c] = 0;
            }

            // Mover para a esquerda
            if (c - 1 >= 0 && newBoard[r][c - 1] != 0) {
                newC = c - 1;
                while (newC > 0 && newBoard[r][newC] != 0) {
                    newC--;
                }

                if (newC == 0) {
                    newBoard[r][newC] = 0;
                } else {
                    newBoard[r][newC] = newBoard[r][newC + 1];
                    newBoard[r][newC + 1] = 0;
                }
            }

            // Mover para a direita
            if (c + 1 < 7 && newBoard[r][c + 1] != 0) {
                newC = c + 1;
                while (newC < 6 && newBoard[r][newC] != 0) {
                    newC++;
                }

                if (newC != 7) {
                    newBoard[r][newC] = newBoard[r][newC - 1];
                }
                newBoard[r][newC - 1] = 0;
            }

            // Mover nas diagonais negativas
            if (r - 1 >= 0 && c - 1 >= 0 && newBoard[r - 1][c - 1] != 0) {
                newR = r - 1;
                newC = c - 1;
                while (newR > 0 && newC > 0 && newBoard[newR][newC] != 0) {
                    newR--;
                    newC--;
                }

                if (newR == 0 || newC == 0) {
                    newBoard[newR][newC] = 0;
                } else {
                    newBoard[newR][newC] = newBoard[newR + 1][newC + 1];
                    newBoard[newR + 1][newC + 1] = 0;
                }
            }

            // Mover nas diagonais positivas
            if (r + 1 < 7 && c + 1 < 7 && newBoard[r + 1][c + 1] != 0) {
                newR = r + 1;
                newC = c + 1;
                while (newR < 6 && newC < 6 && newBoard[newR][newC] != 0) {
                    newR++;
                    newC++;
                }

                if (newR != 7 && newC != 7) {
                    newBoard[newR][newC] = newBoard[newR - 1][newC - 1];
                }
                newBoard[newR - 1][newC - 1] = 0;
            }

            // Mover na diagonal negativa/positiva
            if ((r - 1) >= 0 && (c + 1) < 7 && newBoard[r - 1][c + 1] != 0) {
                newR = r - 1;
                newC = c + 1;
                while (newR > 0 && newC < 6 && newBoard[newR][newC] != 0) {
                    newR--;
                    newC++;
                }

                if (newR == 0 && newBoard[newR][newC] != 0 && newC < 7) {
                    newBoard[newR][newC] = 0;
                } else if (newC == 7) {
                    newBoard[newR + 1][newC - 1] = 0;
                } else if ((newR + 1) < 7 && (newC - 1) >= 0) {
                    newBoard[newR + 1][newC - 1] = 0;
                }
            }

            // Mover na diagonal positiva/negativa
            if (r + 1 < 7 && c - 1 >= 0 && newBoard[r + 1][c - 1] != 0) {
                newR = r + 1;
                newC = c - 1;
                while (newR < 6 && newC > 0 && newBoard[newR][newC] != 0) {
                    newR++;
                    newC--;
                }

                if (newC == 0 && newBoard[newR][newC] != 0) {
                    newBoard[newR][newC] = 0;
                } else if (newR == 7) {
                    newBoard[newR - 1][newC + 1] = 0;
                } else {
                    newBoard[newR][newC] = newBoard[newR - 1][newC + 1];
                    newBoard[newR - 1][newC + 1] = 0;
                }
            }
        }

        return newBoard;
    }


    @Override
    public double getH() {
        int count = 0;
        double h;
        double pos = 1;
        int op = 1;
        if(myColor == 1){
            op = 2;
        }

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] == myColor) {
                    count++;

                    if (isOnCenter(r, c)) {
                        pos += 1000;
                    } else if (isOnBoardEdge(r, c)) {
                        pos += 0;
                    } else if (isOnBoardLateral(r, c)) {
                        pos += 0.5;
                    } else if (isOnMiddle(r, c)) {
                        pos += 1.5;
                    } else if (isOfCenter(r, c)) {
                        pos += 2.5;
                    } else {
                        pos += 5;
                    }

                } else if (board[r][c] != op) {
                    count--;
                    if (isOnCenter(r, c)) {
                        pos = pos - 1000;
                    } else if (isOnBoardEdge(r, c)) {
                        pos -= 0;
                    } else if (isOnBoardLateral(r, c)) {
                        pos -= 0.5;
                    } else if (isOnMiddle(r, c)) {
                        pos -= 1.5;
                    } else if (isOfCenter(r, c)) {
                        pos -= 2.5;
                    } else {
                        pos -= 5;
                    }

                }
            }
        }

        if( count < 0 && pos < 0){
            return  0.0;
        }
        h = count * pos;
        //h = (5 * dist + count) * pos;

        if(h < 0){
            return 0.0;
        }

        return h;
    }

    private boolean isOfCenter(int r, int c) {
        return r == 2 || c == 2 || r == BOARD_SIZE - 3 || c == BOARD_SIZE - 3;
    }

    private boolean isOnBoardEdge(int r, int c) {
        return r == 0 && c == 0 || r == BOARD_SIZE - 1 && c == BOARD_SIZE - 1 || r == BOARD_SIZE - 1 && c == 0 || r == 0 && c == BOARD_SIZE - 1;
    }

    private boolean isOnBoardLateral(int r, int c) {
        return r == 0 || c == 0 || r == BOARD_SIZE - 1 || c == BOARD_SIZE - 1;
    }

    private boolean isOnMiddle(int r, int c) {
        return r == 1 || c == 1 || r == BOARD_SIZE - 2 || c == BOARD_SIZE - 2;
    }

    private boolean isOnCenter(int r, int c) {
        return r == CENTER_POSITION && c == CENTER_POSITION;
    }

    private int[][] makeCopy(int[][] p) {
        int[][] np = new int[BOARD_SIZE][BOARD_SIZE];
        for (int l = 0; l < BOARD_SIZE; l++)
            for (int c = 0; c < BOARD_SIZE; c++)
                np[l][c] = p[l][c];
        return np;
    }

    public void setMyColor(int color) {
        myColor = color;
    }

    public String toString() {
        StringBuilder st = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                st.append(" ").append(cell == 0 ? "." : String.valueOf(cell));
            }
            st.append("\n");
        }
        st.append("\n");
        return st.toString();
    }
}
