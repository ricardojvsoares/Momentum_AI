import java.util.ArrayList;

public class GameMomentumAB extends NodeGameAB {

    private static final int BOARD_SIZE = 7;
    private static final int CENTER_POSITION = 3;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] newBoard;
    private int myColor;
    private int newR, newC;

    private final int[][]  SCORES = {
            {0, 1, 1, 1, 1, 1, 0},
            {1, 2, 2, 2, 2, 2, 1},
            {1, 2, 8, 8, 8, 2, 1},
            {1, 2, 8, 100, 8, 2, 1},
            {1, 2, 8, 8, 8, 2, 1},
            {1, 2, 2, 2, 2, 2, 1},
            {0, 1, 1, 1, 1, 1, 0}
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

        if (!boardEmpty(board)) {
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    if (board[r][c] == 0) {
                        GameMomentumAB successorBoard = new GameMomentumAB(performMove(board, r, c), myColor, getDepth() + 1);
                        successors.add(new Move((r + 1) + " " + (c + 1), successorBoard));
                    }
                }
            }
        }

        else {
            GameMomentumAB centerBoard = new GameMomentumAB(performMove(board, 3, 3), myColor, getDepth() + 1);
            successors.add(new Move("4 4", centerBoard));

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

    private int[][] performMove(int[][] board, int r, int c) {
        newBoard = makeCopy(board);
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
                while (newC < 7 && newBoard[r][newC] != 0) {
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
        h -= (opCount*opValue);
        h += (myCount*myValue);
        System.out.println("    _______");
        System.out.println("    OP Value: "+opValue);
        System.out.println("    MY Value: "+myValue);
        System.out.println("    OP Count: "+opCount);
        System.out.println("    MY Count: "+myCount);
        System.out.println("    _______");

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
