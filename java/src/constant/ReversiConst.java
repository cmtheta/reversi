package constant;

public class ReversiConst {

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT=  900;

    public static final int HOME_WIDTH = FRAME_WIDTH;
    public static final int HOME_HEIGHT= FRAME_HEIGHT;

    public static final int GAMEBOARD_WIDTH  = FRAME_WIDTH / 4 * 3;
    public static final int GAMEBOARD_HEIGHT = FRAME_HEIGHT;

    public static final int GAMEINFO_WIDTH = FRAME_WIDTH / 4;
    public static final int GAMEINFO_HEIGHT= FRAME_HEIGHT;

    public static final int UTIL_BTN_PANEL_WIDTH  = GAMEINFO_WIDTH;
    public static final int UTIL_BTN_PANEL_HEIGHT = GAMEINFO_HEIGHT / 18;

    public static final int GAMEINFO_BOARD_WIDTH = GAMEINFO_WIDTH - UTIL_BTN_PANEL_WIDTH;
    public static final int GAMEINFO_BOARD_HEIGHT = GAMEINFO_HEIGHT - UTIL_BTN_PANEL_HEIGHT;
    
    public static final int BOARD_SIZE = 8;
    public static final int NONE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public static final int[] dx = {-1,0,1,-1,1,-1,0,1}; 
    public static final int[] dy = {-1,-1,-1,0,0,1,1,1}; 

    public static final int SUCCESS = 0;
    public static final int ERROR_OUT_OF_BOARD = 1;
    public static final int ERROR_ADJACENT_OPPONENT = 2;
    public static final int ERROR_LINE_BREAK = 3;
    public static final int ERROR_ALREADY_EXIST = 4;
    public static final int ERROR_CANT_PUT = 5;

    public static final int ANIMATION_SLEEP_TIMER = 80;
    public static final int ANIMATION_TURN_OFF = 0;
    public static final int ANIMATION_BLINKING_OFF = 17;
    public static final int ANIMATION_COUNT_BLINKING = 0;
    public static final int ANIMATION_COUNT_REVERSE = 8;
    
}
