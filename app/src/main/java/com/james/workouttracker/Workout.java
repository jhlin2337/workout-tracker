package com.james.workouttracker;

public class Workout {
    private String exerciseName;
    private int rep1;
    private int rep2;
    private int rep3;
    private int weight;

    public Workout(String exerciseName, int rep1, int rep2, int rep3, int weight) {
        this.exerciseName = exerciseName;
        this.rep1 = rep1;
        this.rep2 = rep2;
        this.rep3 = rep3;
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getRep1() {
        return rep1;
    }

    public int getRep2() {
        return rep2;
    }

    public int getRep3() {
        return rep3;
    }

    public int getWeight() {
        return weight;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setRep1(int rep1) {
        this.rep1 = rep1;
    }

    public void setRep2(int rep2) {
        this.rep2 = rep2;
    }

    public void setRep3(int rep3) {
        this.rep3 = rep3;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
