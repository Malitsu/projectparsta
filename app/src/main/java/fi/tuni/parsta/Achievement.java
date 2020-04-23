package fi.tuni.parsta;

import java.util.Locale;

class Achievement {
    private int id;
    private boolean unlocked = false;
    private String dateOfAchievement;
    private String longDesc = "This is a placeholder text for long text";
    private String shortDesc = "This is a placeholder text for short text";
    private int requiredValue;
    private Type type;
    private enum Type {
        CLICKS, WINS, LEVEL
    }

    public Achievement() {
    }

    public boolean checkIfCompleted(int clicks, int wins, int level) {
        if (unlocked) return false;
        switch(type) {
            case CLICKS: if (clicks >= requiredValue) return true;
            case WINS: if (wins >= requiredValue) return true;
            case LEVEL: if (level == requiredValue) return true;
            default: return false;
        }
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

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
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
                ", longDesc='" + longDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", requiredValue=" + requiredValue +
                ", type=" + type +
                '}';
    }
}
