import java.util.ArrayList;

public class GameMomentumAB extends NodeGameAB {

    private static final int BOARD_SIZE = 7;
    private static final int CENTER_POSITION = 3;
    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] newBoard;
    private int myColor;
    private int newR, newC;

    private final int[][]  SCORES = {
            {1, 2, 1, 1, 1, 2, 1},
            {2, 5, 3, 3, 3, 5, 2},
            {1, 3, 20, 10, 20, 3, 1},
            {1, 3, 10, 40, 10, 3, 1},
            {1, 3, 20, 10, 20, 3, 1},
            {2, 5, 3, 3, 3, 5, 2},
            {1, 2, 1, 1, 1, 2, 1}
    };


    public GameMomentumAB(String node) {
        super(1);
        myColor = getPlayer();
        processNode(node);
    }

    public GameMomentumAB(int[][] p, int myColor, int depth) {
        super(depth);
        for (int r = 0; r < BOARD_SIZE; r++)
            for (int c = 0; c < BOARD_SIZE; c++)
                this.board[r][c] = p[r][c];
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

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] == 0) {
                    GameMomentumAB successorBoard = new GameMomentumAB(performMove(board, r, c), myColor, getDepth() + 1);
                    successors.add(new Move((r + 1) + " " + (c + 1), successorBoard));
                }
            }
        }
        return successors;
    }

    private int[][] performMove(int[][] board, int r, int c) {
        newBoard = makeCopy(board);
        newR = 0;
        newC = 0;

        if (newBoard[r][c] == 0) {
            newBoard[r][c] = myColor;

            // CIMA
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

            // BAIXO
            if (r + 1 < 7 && newBoard[r + 1][c] != 0) {
                newR = r + 1;
                while (newR < 6 && newBoard[newR][c] != 0) {
                    newR++;
                }

                if (newR == 6) {
                    newBoard[newR][c] = 0;
                } else {
                    newBoard[newR][c] = newBoard[newR - 1][c];
                    newBoard[newR - 1][c] = 0;
                }
            }

            // ESQUERDA
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

            // DIREITA
            if (c + 1 < 7 && newBoard[r][c + 1] != 0) {
                newC = c + 1;
                while (newC < 7 && newBoard[r][newC] != 0) {
                    newC++;
                }

                if (newC != 7) {
                    newBoard[r][newC] = newBoard[r][newC - 1];
                }
                newBoard[r][newC - 1] = 0;
            }

            // CIMA/ESQUERDA
            if ((r - 1) >= 0 && (c - 1) >= 0 && newBoard[r - 1][c - 1] != 0) {
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

            // CIMA/DIREITA
            if ((r - 1) >= 0 && (c + 1) < 7 && newBoard[r - 1][c + 1] != 0) {
                newR = r - 1;
                newC = c + 1;
                while (newR > 0 && newC < 6 && newBoard[newR][newC] != 0) {
                    newR--;
                    newC++;
                }

                if (newR == 0 && newBoard[newR][newC] != 0 && newC<7) {
                    newBoard[newR][newC] = 0;
                } else if (newC == 7) {
                    newBoard[newR + 1][newC - 1] = 0;
                } else if ((newR + 1) < 7 && (newC - 1) >= 0) {
                    newBoard[newR][newC] = newBoard[newR + 1][newC - 1];
                    newBoard[newR + 1][newC - 1] = 0;
                }
            }

            // BAIXO/ESQUERDA
            if ((r + 1) < 7 && (c - 1) >= 0 && newBoard[r + 1][c - 1] != 0) {
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

            // BAIXO/DIREITA
            if ((r + 1) < 7 && (c + 1) < 7 && newBoard[r + 1][c + 1] != 0) {
                newR = r + 1;
                newC = c + 1;
                while (newR < 7 && newC < 7 && newBoard[newR][newC] != 0) {
                    newR++;
                    newC++;
                }

                if (newR != 7 && newC != 7) {
                    newBoard[newR][newC] = newBoard[newR - 1][newC - 1];
                }
                newBoard[newR - 1][newC - 1] = 0;
            }

        }


        return newBoard;
    }

    @Override
    public double getH() {
        int pieceValue = 20;
        int opCount = 0;
        int myCount = 0;
        double h = 0.0;
        double opValue = 0.0;
        double myValue = 0.0;
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {

                if(board[r][c] != 0){
                    int value = SCORES[r][c];
                    if (isPlayer(r,c)) {
                        myCount ++;
                        myValue += value;
                    } else if (isOP(r,c)) {
                        opCount ++;
                        opValue += value;
                    }
                }

            }
        }
        h -= (opCount+opValue);
        h += (myCount+myValue);



        h += ((myCount - opCount)*pieceValue);
       /* System.out.println("____________________________");
        System.out.print("Board: " );
        printBoard(board);

        System.out.println("\n\n    OP Value: "+opValue);
        System.out.println("    MY Value: "+myValue);
        System.out.println("    OP Count: "+opCount);
        System.out.println("    MY Count: "+myCount);
        System.out.println("    Heuristic: "+h);*/

        if(myCount == 8){
            return 10000;
        }
        if( opCount == 8){
            return -10000;
        }

        return h;
    }



    private boolean isPlayer(int r, int c){
        return board[r][c] == myColor;
    }

    private boolean isOP(int r, int c){
        return board[r][c] != 0 && board[r][c] != myColor;
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

    private void printBoard(int[][] p) {
        for (int r = 0; r < BOARD_SIZE; r++) {
            System.out.println();
            for (int c = 0; c < BOARD_SIZE; c++) {
                System.out.print("  "+board[r][c]);
            }
        }
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
