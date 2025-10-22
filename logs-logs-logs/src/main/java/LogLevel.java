import java.util.Arrays;

public enum LogLevel {
    TRACE("TRC",1),DEBUG("DBG",2),INFO("INF",4),
    WARNING("WRN",5),ERROR("ERR",6),
    FATAL("FTL",42),UNKNOWN("",0);
    private String shortName;
    private int num;
    LogLevel(String shortName,int num) {
        this.shortName=shortName;
        this.num=num;
    }

    public Integer getNum(){return this.num;}
    public String getShortName(){return this.shortName;}

    public static LogLevel valueOfNull(String name) {
        return Arrays.stream(values()).filter(q->q.getShortName().equals(name)).findFirst().orElse(UNKNOWN);
    }
}
