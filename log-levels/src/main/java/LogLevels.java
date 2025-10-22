public class LogLevels {
    
    public static String message(String logLine) {
        String level = logLevel(logLine);
        return logLine.substring(level.length()+3,logLine.length()).trim();
    }

    public static String logLevel(String logLine) {
        final String level = logLine.split(":")[0];
        return level.substring(1,level.length()-1).toLowerCase();
    }

    public static String reformat(String logLine) {
        String level = logLevel(logLine);
        String message = message(logLine);
        return message.concat(" (").concat(level).concat(")");
    }
}
