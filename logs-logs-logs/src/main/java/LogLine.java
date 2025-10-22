public class LogLine {
    String line;

    public LogLine(String logLine) {
        this.line=logLine;
    }

    public LogLevel getLogLevel() {
        String level =this.line.split(":")[0];
        level = level.substring(1,level.length()-1);
        return LogLevel.valueOfNull(level);
    }

    public String getOutputForShortLog() {
        LogLevel levelEnum = getLogLevel();
        String[] split =this.line.split(":",2);
        return levelEnum.getNum()+":"+split[1].trim();
    }
}
