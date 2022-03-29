
import java.util.ArrayList;

import static constant.ReversiConst.*;

public class ReversiModel {
    private int brd[][];
    private int turn;
    public ReversiModel(){
        brd = new int[BOARD_SIZE][BOARD_SIZE];
        for(int y = 0; y < BOARD_SIZE; y++)
            for(int x = 0; x < BOARD_SIZE; x++)
                brd[y][x] = NONE;
        brd[3][3] = BLACK;
        brd[4][4] = BLACK;
        brd[3][4] = WHITE;
        brd[4][3] = WHITE;
        turn = BLACK;
    }

    public int getCell(int x, int y) { return brd[y][x]; }
    public int getTurn() { return turn; }

    public ArrayList<Point> isThereCanPut(){
        ArrayList<Point> r = new ArrayList<Point>();
        for(int y = 0; y < BOARD_SIZE; y++){
            for(int x = 0; x < BOARD_SIZE; x++){
                for(int dir=0; dir<8; dir++){
                    if(putJudge(x, y, dx[dir], dy[dir]) == SUCCESS){
                        r.add(new Point(x,y));
                        break;
                    }
                }
            }
        }
        return r;
    }

    public ArrayList<Point> put(int x, int y){
        ArrayList<Point> r = new ArrayList<Point>();
        boolean canput = false;
        for(int dir=0; dir<8; dir++){
            if(putJudge(x, y, dx[dir], dy[dir]) == SUCCESS) {
                canput = true;
                reverseSton(x, y, dx[dir], dy[dir], r);
            }
        }
        if(canput) brd[y][x] = turn;
        return r;
    }

    public void reverseSton(int x, int y, int dir_x, int dir_y, ArrayList<Point> r){
        int nx = x + dir_x, ny = y + dir_y;
        int pc = this.turn; // put color
        int oc = 3 - this.turn; // opposite color
        while(true){
            if(nx<0 || nx>=BOARD_SIZE || ny<0 || ny>=BOARD_SIZE) break;
            if(brd[ny][nx] != oc) break;
            brd[ny][nx] = pc;

            r.add(new Point(nx,ny));
            nx += dir_x;
            ny += dir_y;
        }
        return ;
    }

    public int putJudge(int x, int y, int dir_x, int dir_y){
        int pc = this.turn; // put color
        int oc = 3 - this.turn; // opposite color
        
        if(x<0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) return ERROR_OUT_OF_BOARD;
        if(brd[y][x] != NONE) return ERROR_ALREADY_EXIST;

        int nx = x + dir_x, ny = y + dir_y;
        if(nx < 0 || nx >= BOARD_SIZE || ny < 0 || ny >= BOARD_SIZE) return ERROR_OUT_OF_BOARD;
        if(brd[ny][nx] != oc) return ERROR_ADJACENT_OPPONENT;

        nx += dir_x; ny += dir_y;
        while(nx >= 0 && nx < BOARD_SIZE && ny >= 0 && ny < BOARD_SIZE){
            if(brd[ny][nx] == pc) return SUCCESS;
            else if(brd[ny][nx] == 0) return ERROR_LINE_BREAK;
            nx += dir_x; ny += dir_y;
        }

        return ERROR_LINE_BREAK;
    }
    
    public void changePlayer(){
        this.turn = 3 - this.turn;
    }

    public int getStoneNum(int playerColor){
        int cnt = 0;
        for(int[] y : brd)
            for(int x : y) if(x == playerColor) cnt++;
        return cnt;
    }

    public void debugPrintBrd(){
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                System.out.print(this.brd[y][x]);
            }
            System.out.println("");
        }
    }
    
}
