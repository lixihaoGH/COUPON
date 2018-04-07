package cn.com.lixihao.couponapi.entity.result;

public class ReleaseStatusType {
    /**
     * 策略的投放状态
     * 0------初始化
     * 1------投放中
     * 2------暂停投放
     * 3------结束投放
     *
     */
    public static final Integer NO_RELEASE = 0 ;
    public static final Integer EIIECTIVE = 1;
    public static final Integer PAUSE = 2;
    public static final Integer END = 3;
}
