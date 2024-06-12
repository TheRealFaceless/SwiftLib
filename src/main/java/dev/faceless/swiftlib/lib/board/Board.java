package dev.faceless.swiftlib.lib.board;

import dev.faceless.swiftlib.lib.text.TextContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.LinkedHashMap;

@SuppressWarnings("unused")
public class Board {

    private final Scoreboard scoreboard;
    private Objective objective;
    private final LinkedHashMap<String, Integer> scores;
    private String title;

    public Board(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.scores = new LinkedHashMap<>();
        this.objective = scoreboard.registerNewObjective(title, Criteria.DUMMY, TextContext.formatLegacy(title));
        this.title = title;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Board blankLine() {
        add(" ");
        return this;
    }

    public Board add(String text) {
        add(text, null);
        return this;
    }

    public Board add(String text, Integer score) {
        scores.put(text, score);
        return this;
    }

    public void setCriteria(Criteria criteria) {
        if (objective != null) {
            objective.unregister();
        }
        objective = scoreboard.registerNewObjective(title, criteria, TextContext.formatLegacy(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.displayName(TextContext.formatLegacy(title));
    }

    public void setDisplaySlot(DisplaySlot displaySlot) {
        objective.setDisplaySlot(displaySlot);
    }

    public void setTitle(String title) {
        this.title = title;
        objective.displayName(TextContext.formatLegacy(title));
    }

    public Board reset() {
        scores.clear();
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
        return this;
    }

    public void build() {
        int index = scores.size();
        for (String text : scores.keySet()) {
            int score = scores.get(text) != null ? scores.get(text) : index;

            text = TextContext.formatLegacy2(text);
            Score s1 = objective.getScore(text);
            s1.setScore(score);

            index -= 1;
        }
    }

    public void send(Player... players) {
        build();
        for (Player p : players) p.setScoreboard(scoreboard);
    }
}