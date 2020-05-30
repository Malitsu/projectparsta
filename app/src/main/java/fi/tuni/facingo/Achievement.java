package fi.tuni.facingo;

import java.util.Locale;

class Achievement {
    private int id;
    private boolean unlocked = false;
    private String dateOfAchievement;
    private int requiredValue;
    private Type type;
    private enum Type {
        CLICKS, WINS, LEVEL, SPECIAL
    }

    public Achievement() {
    }

    public boolean checkIfCompleted(int clicks, int wins, int level, int id) {
        if (unlocked) return false;
        switch(type) {
            case CLICKS: if (clicks >= requiredValue) return true; break;
            case WINS: if (wins >= requiredValue) return true; break;
            case LEVEL: if (level == requiredValue) return true; break;
            case SPECIAL: if (id == requiredValue) return true; break;
        }
        return false;
    }

    public String getShortDescKey() {
        return getIdKey() + "_short_desc";
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        unlocked = true;
    }

    public int getRequiredValue() {
        return requiredValue;
    }

    public String getLongDescKey() {
        return getIdKey() + "_long_desc";
    }

    public String getIcon() {
        if (isUnlocked()) {
            return getIdKey() + "_unlocked";
        } else {
            return getIdKey() + "_locked";
        }
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setRequiredValue(int requiredValue) {
        this.requiredValue = requiredValue;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateOfAchievement() {
        return dateOfAchievement;
    }

    public void setDateOfAchievement(String dateOfAchievement) {
        this.dateOfAchievement = dateOfAchievement;
    }

    public String getIdKey() {
        return "ach_" + String.format(Locale.getDefault(), "%03d", getId());
    }

    public String getADateKey() {
        return "achDate_" + String.format(Locale.getDefault(), "%03d", getId());
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", unlocked=" + unlocked +
                ", requiredValue=" + requiredValue +
                ", type=" + type +
                '}';
    }
}
