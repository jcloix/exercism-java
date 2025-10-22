import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Tournament {
    Map<String, TeamStats> teams = new HashMap<>();
    String printTable() {
        return printResults(teams);
    }

    void applyResults(String resultString) {
        teams= saveResults(resultString);
    }

    Map<String, TeamStats> saveResults(String input) {
        Map<String, TeamStats> teams = new HashMap<>();

        String[] lines = input.split("\\n");

        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length != 3) continue;

            String team1 = parts[0].trim();
            String team2 = parts[1].trim();
            String result = parts[2].trim();

            teams.putIfAbsent(team1, new TeamStats());
            teams.putIfAbsent(team2, new TeamStats());

            switch (result) {
                case "win":
                    teams.get(team1).addWin();
                    teams.get(team2).addLoss();
                    break;
                case "loss":
                    teams.get(team1).addLoss();
                    teams.get(team2).addWin();
                    break;
                case "draw":
                    teams.get(team1).addDraw();
                    teams.get(team2).addDraw();
                    break;
                default:
                    System.err.println("Unknown result: " + result);
            }
        }

        return teams;
    }
    String printResults(Map<String, TeamStats> teams) {
        // Sort teams by points descending, then name ascending
        List<Map.Entry<String, TeamStats>> sortedTeams = new ArrayList<>(teams.entrySet());
        sortedTeams.sort((a, b) -> {
            int pCompare = Integer.compare(b.getValue().getPoints(), a.getValue().getPoints());
            if (pCompare != 0) return pCompare;
            return a.getKey().compareTo(b.getKey());
        });

        // Print header
        StringBuilder output = new StringBuilder(String.format("%-30s | MP |  W |  D |  L |  P\n", "Team"));


        // Print each team stats
        for (var entry : sortedTeams) {
            String team = entry.getKey();
            TeamStats stats = entry.getValue();
            output.append(String.format(
                    "%-30s | %2d | %2d | %2d | %2d | %2d\n",
                    team, stats.played, stats.wins, stats.draws, stats.losses, stats.getPoints()
            ));
        }
        return output.toString();
    }

    static class TeamStats {
        int played = 0;
        int wins = 0;
        int draws = 0;
        int losses = 0;

        void addWin() {
            wins++;
            played++;
        }

        void addDraw() {
            draws++;
            played++;
        }

        void addLoss() {
            losses++;
            played++;
        }

        int getPoints() {
            return wins * 3 + draws;
        }
    }
}


