package netty.websocket.game;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AI机器人
 */
public class AIServer {

    /**
     * 棋盘行数
     */
    private static final int rows = 15;

    /**
     * 棋盘列数
     */
    private static final int cols = 15;

    /**
     * 棋盘数据
     */
    private int[][] chessData;

    public float attackFactor = 1.25F;

    private Point bestPoint;

    private long hashCode;

    private static final int blackFlag = 1;

    private static final int whiteFlag = 2;

    private static final int INFINITY = 999999999;

    private Map<Long, SituationCache> situationCacheMap;

    private static final Map<String, Integer> SCORE = new LinkedHashMap<>();

    private final long[][] blackZobrist = new long[rows][cols];

    private final long[][] whiteZobrist = new long[rows][cols];


    /**
     * 初始化zobrist
     */
    private void init() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                blackZobrist[i][j] = ThreadLocalRandom.current().nextLong();
                whiteZobrist[i][j] = ThreadLocalRandom.current().nextLong();
            }
        }

        hashCode = ThreadLocalRandom.current().nextLong();

        for (ChessModel chessModel : ChessModel.values()) {
            for (String value : chessModel.values) {
                SCORE.put(value, chessModel.score);
            }
        }
    }

    public AIServer(int[][] chessData) {
        init();
        setChessData(chessData);
    }

    /**
     * 设置棋盘数据  每次客户端连接时都需要进行重新设置
     *
     * @param chessData 棋盘数据
     */
    public void setChessData(int[][] chessData) {
        this.chessData = chessData;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int e = chessData[i][j];

                if (e != 0) {
                    putChess(new ScorePoint(i,j,e));
                }
            }
        }
    }

    /**
     * ai自动下棋
     *
     * @param blackChess 玩家棋子
     * @return ai棋子
     */
    public Point go(Point blackChess) {
        // 玩家下棋
        putChess(blackChess);

        // ai搜索最佳下棋位置
        Point whiteChess = deepening();

        if (whiteChess == null) {
            return new ScorePoint(1,1,whiteFlag);
        }

        // ai下棋
        putChess(whiteChess);

        // 返回给客户端
        return whiteChess;
    }

    /**
     * 下棋
     *
     * @param chess 棋子
     */
    private void putChess(Point chess) {
        chessData[chess.x()][chess.y()] = chess.role();
        calculateHashCode(chess);
    }

    /**
     * 悔棋
     *
     * @param chess 棋子
     */
    private void undoChess(Point chess) {
        chessData[chess.x()][chess.y()] = 0;
        calculateHashCode(chess);
    }

    /**
     * 计算code
     *
     * @param chess 棋子
     */
    private void calculateHashCode(Point chess) {
        int x = chess.x();
        int y = chess.x();
        int role = chess.role();

        long zobrist = role == blackFlag ? blackZobrist[x][y] : whiteZobrist[x][y];

        hashCode ^= zobrist;

    }

    /**
     * 启发式获取搜索节点
     *
     * @param role 下棋角色
     * @return 启发式节点
     */
    private Collection<ScorePoint> getHeuristicPointList(int role) {

        boolean findFive = false;

        // 进攻节点队列
        List<ScorePoint> attackList = new ArrayList<>();

        // 候选节点队列
        List<ScorePoint> alternateList = new ArrayList<>();

        // 查找可以下棋的点
        // 并且该点下棋分数要高
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int e = chessData[i][j];

                if (e != 0) {
                    continue;
                }

                ScorePoint ai = new ScorePoint(i, j, e, evaluate(i, j, e));

                // 找到连五可以直接返回
                if (ai.score() >= 100000) {
                    return Collections.singletonList(ai);
                }

                if (findFive) {
                    continue;
                }

                ScorePoint foe = new ScorePoint(i, j, 3 - role, evaluate(i, j, 3 - role));

                if (foe.score() >= 100000) {
                    attackList.add(foe);
                    findFive = true;
                    continue;
                }

                if (ai.score() >= 10000 || foe.score() >= 10000) {
                    attackList.add(ai);
                    continue;
                }

                // 垃圾位置 放弃
                if (ai.score() <= 1000) {
                    continue;
                }

                alternateList.add(ai);
            }
        }

        // 算杀节点
        if (!attackList.isEmpty()) {
            Collections.sort(attackList);
            return attackList;
        }

        Collections.sort(alternateList);
        return alternateList.size() <= 16 ? alternateList : alternateList.subList(0, 16);

    }

    /**
     * 评估ai在当前棋盘局面分数
     *
     * @return ai局面得分
     */
    private int evaluate() {
        int whiteScore = 0;
        int blackScore = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int e = chessData[i][j];

                if (e == 0) {
                    continue;
                }

                int score = evaluate(i, j, e);
                if (e == blackFlag) {
                    blackScore += score;
                } else {
                    whiteScore += score;
                }
            }
        }

        return Math.round(whiteScore * attackFactor) - blackScore;
    }


    /**
     * 评估该位置的得分
     *
     * @param x
     * @param y
     * @param role
     * @return
     */
    private int evaluate(int x, int y, int role) {
        int x1, x2;
        int radius = 4;
        int score = 0;
        int[][] vectors = new int[][]{{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] vector : vectors) {
            StringBuilder sb = new StringBuilder();
            for (int k = -radius; k <= radius; k++) {
                x1 = x + vector[0] * k;
                x2 = y + vector[1] * k;

                if (x1 < 0 || x1 >= rows || x2 < 0 || x2 >= cols) {
                    continue;
                }

                int e = chessData[x1][x2];

                if (e == 3 - role) {
                    if (k > 0) {
                        break;
                    } else {
                        sb = new StringBuilder();
                    }
                } else {
                    sb.append(e == role ? '1' : '0');
                }
            }
            score += getSituation(sb.toString());
        }
        return score;
    }

    private int getSituation(String s) {
        int score = 0;
        int count = 0;
        for (ChessModel chessModel : ChessModel.values()) {
            if (count == 2) {
                break;
            }
            for (String value : chessModel.values) {
                if (s.contains(value)) {
                    score += chessModel.score;
                    count++;
                    break;
                }
            }
        }
        return score;
    }

    public int negamax(boolean isRoot, int depth, int alpha, int beta, int role) {

        // 局面缓存
        SituationCache situationCache = situationCacheMap.get(hashCode);
        if (situationCache != null && situationCache.depth() > depth) {
            return situationCache.score();
        }

        if (depth == 0) {
            return evaluate();
        }

        int bestValue = -INFINITY;
        int value = 0;
        for (ScorePoint h : getHeuristicPointList(role)) {
            // 找到连五的点  直接返回
            if (h.score() >= 1000) {
                value = role == whiteFlag ? INFINITY - 1 : -INFINITY + 1;
                return value;
            }


            putChess(h);
            value = negamax(false, depth - 1, -beta, -alpha, 3 - role);
            undoChess(h);

            if (value > bestValue) {
                bestValue = value;
                if (isRoot) {
                    bestPoint = h;
                }
            }

            if (alpha >= beta) {
                break;
            }
        }

        return bestValue;
    }

    public Point deepening() {
        situationCacheMap = new HashMap<>(2048);

        Point best = null;
        int alpha = INFINITY;
        int beta = -INFINITY;
        int maxDepth = 6;
        for (int i = 2; i <= maxDepth; i += 2) {
            int score = negamax(true, i, alpha, beta, whiteFlag);
            best = bestPoint;
            if (score >= alpha - 1) {
                break;
            }
        }

        return best;
    }

}
