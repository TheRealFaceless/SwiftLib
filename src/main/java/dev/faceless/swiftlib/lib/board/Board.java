package dev.faceless.swiftlib.lib.board;

import dev.faceless.swiftlib.lib.text.TextContext;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Board {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final List<String> lines;

    private Board(Builder builder) {
        this.scoreboard = builder.scoreboard;
        this.objective = builder.objective;
        this.lines = builder.lines;
    }

    public static class Builder {
        private final Scoreboard scoreboard;
        private final Objective objective;
        private final List<String> lines;

        public Builder(Component title) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            this.scoreboard = manager.getNewScoreboard();
            this.objective = scoreboard.registerNewObjective("dummy", Criteria.DUMMY, title, RenderType.INTEGER);
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.objective.numberFormat(NumberFormat.blank());
            this.lines = new ArrayList<>();
        }

        public Builder addLine(String line) {
            lines.add(line);
            return this;
        }

        public Builder blankLine() {
            lines.add("");
            return this;
        }

        public Board build() {
            int lineNumber = lines.size();
            for (String line : lines) {
                Score score = objective.getScore(TextContext.formatLegacy2(line));
                score.setScore(lineNumber--);
            }
            return new Board(this);
        }
    }

    public void reset() {
        scoreboard.getEntries().forEach(scoreboard::resetScores);
        lines.clear();
    }

    public void send(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void send(Player... players) {
        Arrays.stream(players).forEach(this::send);
    }

    public void updateLine(int index, String newLine) {
        if (index >= 0 && index < lines.size()) {
            reset();
            lines.set(index, newLine);
            int lineNumber = lines.size();
            for (String line : lines) {
                Score score = objective.getScore(TextContext.formatLegacy2(line));
                score.setScore(lineNumber--);
            }
        }
    }

    public void removeLine(int index) {
        if (index >= 0 && index < lines.size()) {
            reset();
            lines.remove(index);
            int lineNumber = lines.size();
            for (String line : lines) {
                Score score = objective.getScore(TextContext.formatLegacy2(line));
                score.setScore(lineNumber--);
            }
        }
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }
}
