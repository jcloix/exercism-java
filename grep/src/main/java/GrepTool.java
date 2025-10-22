import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Line {
    String fileName;
    int numLine;
    String line;

    Line(String fileName, int numLine, String line) {
        this.fileName = fileName;
        this.numLine = numLine;
        this.line = line;

    }

    String formatLine(Set<String> flags, int nbFile) {
        return Stream.of(
                        nbFile != 1 ? fileName : null,
                        flags.contains("-n") ? String.valueOf(numLine) : null,
                        line
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(":"));
    }
}


class GrepTool {

    String grep(String pattern, List<String> flags, List<String> files) {
        Set<String> flagSet = new HashSet<>(flags);
        StringBuilder result = new StringBuilder();
        List<Line> matchingLines = new ArrayList<>();
        readFiles(files,line->{
            if(isMatchingLine(line.line,pattern,flagSet)) {
                matchingLines.add(line);
            }
        });
        return printResult(matchingLines,flagSet,files);

    }

    String printResult(List<Line> matchingLines, Set<String> flags, List<String> files) {
        if(flags.contains("-l")) {
            return matchingLines.stream().map(l->l.fileName).distinct().collect(Collectors.joining("\n"));
        }
        return matchingLines.stream().map(l->l.formatLine(flags,files.size()))
                .collect(Collectors.joining("\n"));
    }

    void readFiles(List<String> files, Consumer<Line> lineConsumer) {
        for (String fileName : files) {
            File file = new File(fileName);
            if (!file.exists()) continue;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int i=1;
                while ((line = br.readLine()) != null) {
                    lineConsumer.accept(new Line(fileName,i,line));
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace(); // optionally handle error more gracefully
            }
        }
    }

    boolean isMatchingLine(String line, String pattern,Set<String> flags) {
        String cleanLine = flags.contains("-i") ? line.toLowerCase():line;
        String cleanPattern = flags.contains("-i") ? pattern.toLowerCase():pattern;
        boolean matching = flags.contains("-x") ? cleanLine.equals(cleanPattern):cleanLine.contains(cleanPattern);
        return flags.contains("-v") ? !matching:matching;
    }

}