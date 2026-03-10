package edu.touro.las.mcon364.test;

import java.util.*;

public class StudyTracker {

    private final Map<String, List<Integer>> scoresByLearner = new HashMap<>();
    private final Deque<UndoStep> undoStack = new ArrayDeque<>();
    // Helper methods already provided for tests and local inspection.
    public Optional<List<Integer>> scoresFor(String name) {
        return Optional.ofNullable(scoresByLearner.get(name));
    }

    public Set<String> learnerNames() {
        return scoresByLearner.keySet();
    }
    /**
     * Problem 11
     * Add a learner with an empty score list.
     *
     * Return:
     * - true if the learner was added
     * - false if the learner already exists
     *
     * Throw IllegalArgumentException if name is null or blank.
     */
    public boolean addLearner(String name) {
        if (scoresByLearner.containsKey(name)) {
            return false;
        }
        scoresByLearner.put(name, new ArrayList<>());
        return true;
    }

    /**
     * Problem 12
     * Add a score to an existing learner.
     *
     * Return:
     * - true if the score was added
     * - false if the learner does not exist
     *
     * Valid scores are 0 through 100 inclusive.
     * Throw IllegalArgumentException for invalid scores.
     *
     * This operation should be undoable.
     */
    public boolean addScore(String name, int score) {
        var scores = scoresByLearner.get(name);
        if (scores == null) {
            return false;
        }
        scores.add(score);
        return true;
    }

    /**
     * Problem 13
     * Return the average score for one learner.
     *
     * Return Optional.empty() if:
     * - the learner does not exist, or
     * - the learner has no scores
     */
    public Optional<Double> averageFor(String name) {
        var learners = scoresByLearner.get(name);
        if (learners == null || learners.isEmpty()) {
            return Optional.empty();
        }
        int sum = 0;
        for (var score : scoresByLearner.get(name)) {
            sum += score;
        }
        double avg = (double) sum / scoresByLearner.size();
        return Optional.of(avg);
    }

    /**
     * Problem 14
     * Convert a learner average into a letter band.
     *
     * A: 90+
     * B: 80-89.999...
     * C: 70-79.999...
     * D: 60-69.999...
     * F: below 60
     *
     * Return Optional.empty() when no average exists.
     */
    public Optional<String> letterBandFor(String name) {
        Optional<Double> avgOptional = averageFor(name);
        if (avgOptional.isEmpty()) {
            return Optional.empty();
        }
        double avg = avgOptional.get();
        // had to use AI to figure out how to use switch expression and  dividing averages
        String letter = switch ((int)avg / 10) {
            case 10, 9 -> {yield "A";}
            case 8 -> {yield "B";}
            case 7 -> {yield "C";}
            case 6 -> {yield "D";}
            default -> {yield "F";}
        };
        return Optional.of(letter);
    }

    /**
     * Problem 15
     * Undo the most recent state-changing operation.
     *
     * Return true if something was undone.
     * Return false if there is nothing to undo.
     */
    public boolean undoLastChange() {
        if (undoStack.isEmpty()) {
            return false;
        }
        var action = undoStack.pop();
        return true;
    }


}
