package bi.logic.drawing.oekakilogic;

import java.util.Random;

public class OekakiLogic {

    private final int[][] field;
    private final Random generator;

    private int[][] xZeroOneCount;
    private int[][] yZeroOneCount;

    public OekakiLogic() {
        this(10,10);
    }

    public OekakiLogic(int w, int h) {
        field = new int[h][w];
        generator = new Random();
        xZeroOneCount = new int[10][5];
        yZeroOneCount = new int[10][5];
    }

    public void generateRandom() {
        for(int y = 0; y < field.length; y++) {
            for(int x = 0; x < field[y].length; x++) {
                setAt(x, y, generator.nextInt(2));
            }
        }
    }

    public int getAt(int x, int y) {
        return field[y][x];
    }

    public void setAt(int x, int y, int val) {
        field[y][x] = val;
    }

    public int getXZeroOneCount(int x, int y) {
        return xZeroOneCount[x][y];
    }

    public int getYZeroOneCount(int x, int y) {
        return yZeroOneCount[x][y];
    }

    public void generateNumbers() {
        int x,y;
        int zeroOrOne = 0;
        int maxCntX = 0;
        int maxCntY = 0;

        int[] xCnt = new int[10];
        int[] yCnt = new int[10];

        for(y = 0; y < 10; y++) {
            int i = 0;
            zeroOrOne = 0;
            x = 0;
            while(x < 10 && i <= 10) {
                while( x < 10 && getAt(x,y) == zeroOrOne ) {
                    if(zeroOrOne == 1)
                        xZeroOneCount[y][i]++;
                    x++;
                }
                if(zeroOrOne == 1) i++;
                zeroOrOne = 1 - zeroOrOne;
            }
            xCnt[y] = i;
            maxCntX = Math.max(i, maxCntX);
        }

        for(x = 0; x < 10; x++) {
            int n = 0;
            int i = 0;
            zeroOrOne = 0;
            y = 0;
            while(y < 10 && i <= 10) {
                while( y < 10 && getAt(x,y) == zeroOrOne ) {
                    if(zeroOrOne == 1)
                        yZeroOneCount[x][i]++;
                    y++;
                }
                if(zeroOrOne == 1) i++;
                zeroOrOne = 1 - zeroOrOne;
            }
            yCnt[x] = i;
            maxCntY = Math.max(i, maxCntY);
        }
    }
/*
    public static void main(String args[]) {

        int c[][] = { {0,0,0,0,0,0,0,0,0,0},
                      {0,1,1,1,1,1,1,1,1,0},
                      {0,0,0,0,0,0,0,1,0,0},
                      {0,0,1,1,1,1,0,1,0,0},
                      {0,0,1,0,0,1,0,1,0,0},
                      {0,0,1,1,1,1,0,1,0,0},
                      {0,0,0,0,0,0,0,1,0,0},
                      {0,0,0,0,0,0,1,1,0,0},
                      {0,0,0,0,0,0,0,1,0,0},
                      {0,0,0,0,0,0,0,0,0,0} };
        int[][] c = new int[10][10];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Input data 0 or 1. 10 Characters x 10 Lines");
            for(int lineIndex = 0; lineIndex < 10; lineIndex++) {
                System.out.print("Line " + (lineIndex + 1) + ": ");
                String l = br.readLine();
                for(int i = 0; i < Math.min(10, l.length()); i++) {
                    char ch = l.charAt(i);
                    c[lineIndex][i] = 0;
                    if(ch == '1') {
                        c[lineIndex][i] = 1;
                    }
                }
            }
        } catch(Exception exc) {
            exc.printStackTrace();
        }
        int[][] xZeroOneCount = new int[10][5];
        int[][] yZeroOneCount = new int[10][5];

        int x,y;
        int zeroOrOne = 0;
        int maxCntX = 0;
        int maxCntY = 0;

        int[] xCnt = new int[10];
        int[] yCnt = new int[10];

        for(y = 0; y < 10; y++) {
            int i = 0;
            zeroOrOne = 0;
            x = 0;
            while(x < 10 && i <= 10) {
                while( x < 10 && c[y][x] == zeroOrOne ) {
                    if(zeroOrOne == 1)
                        xZeroOneCount[y][i]++;
                    x++;
                }
                if(zeroOrOne == 1) i++;
                zeroOrOne = 1 - zeroOrOne;
            }
            xCnt[y] = i;
            maxCntX = Math.max(i, maxCntX);
        }

        for(x = 0; x < 10; x++) {
            int n = 0;
            int i = 0;
            zeroOrOne = 0;
            y = 0;
            while(y < 10 && i <= 10) {
                while( y < 10 && c[y][x] == zeroOrOne ) {
                    if(zeroOrOne == 1)
                        yZeroOneCount[x][i]++;
                    y++;
                }
                if(zeroOrOne == 1) i++;
                zeroOrOne = 1 - zeroOrOne;
            }
            yCnt[x] = i;
            maxCntY = Math.max(i, maxCntY);
        }
        boolean[][] userData = new boolean[10][10];

        for( ; ;) {
            for(int i = 0; i < maxCntY; i++) {
                for(int j = 0; j < maxCntX; j++) {
                    System.out.print("  ");
                    if(maxCntX - 1 != j) System.out.print(" ");
                }
                System.out.print("   ");
                for(int j = 0; j < 10; j++) {
                    if(yCnt[j] == 0 || yCnt[j] - maxCntY + i < 0 ) {
                        System.out.print("   ");
                        continue;
                    }
                    int n = yZeroOneCount[j][yCnt[j] - maxCntY + i];
                    if(n == 0) {
                        System.out.print("   ");
                    } else if(n < 10) {
                        System.out.print("  " + n);
                    } else {
                        System.out.print(" " + n);
                    }
                }
                System.out.print("\n");
            }
            for(int j = 0; j < maxCntX; j++) {
                System.out.print("  ");
                if(maxCntX - 1 != j) System.out.print(" ");
            }
            System.out.println("    -----------------------------");
            for(x = 0; x < 10; x++) {
                for(int j = 0; j < maxCntX - xCnt[x]; j++) {
                    System.out.print("  ");

                    if(maxCntX - xCnt[x] != j) {
                        System.out.print(" ");
                    }
                }

                for(int j = 0; j < xCnt[x]; j++) {
                    int n = xZeroOneCount[x][j];
                    if(n < 10) {
                        System.out.print(" " + n);
                    } else {
                        System.out.print("" + n );
                    }
                    if(j != xCnt[x] -1) {
                        System.out.print(",");
                    } else {
                        System.out.print(" ");
                    }
                }

                System.out.print(" ||");
                for(int i = 0; i < 10; i++) {
                    //System.out.print(" " + c[x][i] + " ");
                    System.out.print(" " + (userData[x][i]?"#":"_") + " ");

                }
                System.out.print(" |" + x);
                System.out.print("\n");
            }
            for(int j = 0; j < maxCntX; j++) {
                System.out.print("  ");
                if(maxCntX - 1 != j) System.out.print(" ");
            }
            System.out.println("    -----------------------------");

            for(int j = 0; j < maxCntX; j++) {
                System.out.print("  ");
                if(maxCntX - 1 != j) System.out.print(" ");
            }
            System.out.println("     A  B  C  D  E  F  G  H  I  J");

            boolean gameEndFlag = true;
            for(y = 0; y < 10; y++) {
                for(x = 0; x < 10; x++) {
                    if(!((c[y][x] == 0 && !userData[y][x]) || (c[y][x] == 1 && userData[y][x]))) {
                        gameEndFlag = false;
                        break;
                    }
                }
                if(!gameEndFlag) break;
            }

            if(gameEndFlag) {
                System.out.println("Game Clear!");
                break;
            }

            System.out.println("Input check position (ex: A2, 7I... / 'x' is gameover):");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String line = br.readLine();
                int xPos = -1;
                int yPos = -1;
                if(line.equals("x")) {
                    break;
                }
                for(int i = 0; i < line.length(); i++) {
                    char m = line.charAt(i);
                    if('J' >= m && m >= 'A') {
                        xPos = (int)(m - 'A');
                    }
                    if('j' >= m && m >= 'a') {
                        xPos = (int)(m - 'a');
                    }
                    else if('9' >= m && m >= '0') {
                        yPos = (int)(m - '0');
                    }
                }

                if(xPos!=-1 && yPos!=-1) {
                    userData[yPos][xPos] = !userData[yPos][xPos];
                }
            } catch(Exception e) {}


        }
        */
}
