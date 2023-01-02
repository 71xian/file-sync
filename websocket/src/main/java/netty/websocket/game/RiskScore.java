package netty.websocket.game;


public record RiskScore(int score) {

    static RiskScore HIGH_RISK = new RiskScore(800000);

    static RiskScore MEDIUM_RISK = new RiskScore(500000);

    static RiskScore LOW_RISK = new RiskScore(100000);

    /**
     * 判断分数是否处于区间 [leftScore, rightScore)
     *
     * @param score      分数
     * @param leftScore  左区分值
     * @param rightScore 右区分值
     * @return
     */
    public static boolean between(int score, RiskScore leftScore, RiskScore rightScore) {
        return score >= leftScore.score && score < rightScore.score;
    }
}
