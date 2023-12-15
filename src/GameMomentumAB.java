import java.util.ArrayList;

public class GameMomentumAB extends NodeGameAB {

    private int[][] board = new int[7][7];
    private int[][] newBoard;
    private int myColor;
    private int newR;
    private int newC;

    public GameMomentumAB(String node) {
        super(1);
        myColor = getPlayer();
        processNode(node);
    }

    public GameMomentumAB(int[][] p, int myColor, int depth) {
        super(depth);
        for (int l = 0; l < 7; l++)
            for (int c = 0; c < 7; c++)
                this.board[l][c] = p[l][c];
        this.myColor = myColor;
    }

    public void processNode(String node) {
        String[] v = node.trim().split(" ");
        for (int l = 0; l < 7; l++)
            for (int c = 0; c < 7; c++)
                try {
                    board[l][c] = Integer.parseInt(v[l * 7 + c]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("board " + v + "  l " + l + "  c " + c);
                }
    }

    public ArrayList<Move> expandAB() {
        ArrayList<Move> suc = new ArrayList<>();

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
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

                    GameMomentumAB sucBoard = new GameMomentumAB(newBoard, myColor, getDepth() + 1);
                    int moveR = r + 1;
                    int moveC = c + 1;
                    suc.add(new Move(moveR+" "+moveC, sucBoard));
                }
            }
        }

        return suc;
    }


    public double getH() {
        int count= 0;
        double dist = 0;
        double h;
        double distance = 0;
        double pos = 1;

        int opponentColor;
        if(myColor == 1){
            opponentColor = 2;
        } else {
            opponentColor = 1;
        }


        for(int r = 0; r < 7; r++) {
            for(int c = 0; c < 7; c++) {
                if(board[r][c] == myColor) {
                    count++;
                    distance = 7 - (Math.abs(r - 3) + Math.abs(c - 3));
                    dist += distance;

                    if(c== 3 && r == 3){
                        pos += 1000;
                    } else if((c==0 && r == 0) || (c==0 && r == 6) || (c==6 && r==0) || (c==6 && r==6)){
                        pos += 0;
                    } else if (r == 0 || c == 0 || r == 6 || c == 6){
                        pos += 0.1;
                    } else if(c== 1 || r == 1 || c == 5 || r == 5){
                        pos += 1.5;
                    } else if(c== 2 || r == 2 || c == 4 || r == 4){
                        pos += 5;
                    } else {
                        pos += 50;
                    }

                }else if (board[r][c] == opponentColor){
                    count--;

                    if(c== 3 && r == 3){
                        pos -= 1000;
                    } else if((c==0 && r == 0) || (c==0 && r == 6) || (c==6 && r==0) || (c==6 && r==6)){
                        pos -= 0;
                    } else if (r == 0 || c == 0 || r == 6 || c == 6){
                        pos -= 0.1;
                    } else if(c== 1 || r == 1 || c == 5 || r == 5){
                        pos -= 1.5;
                    } else if(c== 2 || r == 2 || c == 4 || r == 4){
                        pos -= 5;
                    } else {
                        pos -= 50;
                    }


                }
            }
        }


        h = (5*dist + count) * pos;

        return h;
    }

    private int[][] makeCopy(int[][] p) {
        int[][] np = new int[7][7];
        for (int l = 0; l < 7; l++)
            for (int c = 0; c < 7; c++)
                np[l][c] = p[l][c];
        return np;
    }

    public void setMyColor(int color) {
        myColor = color;
    }

    public String toString() {
        String st = "";
        for (int l = 0; l < 7; l++) {
            for (int c = 0; c < 7; c++) {
                st += " " + (board[l][c] == 0 ? "." : "" + (board[l][c]));
            }
            st += "\n";
        }
        st += "\n";
        return st;
    }



}
