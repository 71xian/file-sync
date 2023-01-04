package netty.gateway.websocket.game;

public enum ChessModel {
    /**
     * 连五
     */
    LIANWU(10000000, new String[]{"11111"}),
    /**
     * 活四
     */
    HUOSI(1000000, new String[]{"011110"}),
    /**
     * 活三
     */
    HUOSAN(10000, new String[]{"001110", "011100", "010110", "011010"}),
    /**
     * 冲四
     */
    CHONGSI(9000, new String[]{"11110", "01111", "10111", "11011", "11101"}),
    /**
     * 活二
     */
    HUOER(100, new String[]{"001100", "011000", "000110", "001010", "010100"}),
    /**
     * 活一
     */
    HUOYI(80, new String[]{"00100", "0100", "0010", "01000", "00010"}),
    /**
     * 眠三
     */
    MIANSAN(30, new String[]{"00111", "01011", "01101", "11100", "11010"}),
    /**
     * 眠二
     */
    MIANER(10, new String[]{"00011", "0011", "110", "1100", "110000", "000011", "000112", "211000"}),
    /**
     * 眠一
     */
    MIANYI(1, new String[]{"001200", "002100", "000210", "000120", "210000", "000012"});

    /**
     * 分数
     */
    int score;
    /**
     * 局势数组
     */
    String[] values;

    ChessModel(int score, String[] values) {
        this.score = score;
        this.values = values;
    }
}

