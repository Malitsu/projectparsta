package fi.tuni.parsta;

class Achievement {
    private boolean unlocked = false;
    private String longDesc;
    private String shortDesc = "This is a placeholder text: ";
    private int requiredValue;
    private int lockedIcon;
    private int unlockedIcon;
    private Type type;
    private enum Type {
        CLICKS, WINS, LEVEL
    }

    public Achievement(int requiredValue) {
        this.requiredValue = requiredValue;
        this.shortDesc = this.getShortDesc() + requiredValue;
        this.lockedIcon = R.drawable.question;
        this.unlockedIcon = R.drawable.star;
        this.type = Type.CLICKS;
    }

    public boolean checkIfCompleted(int clicks, int wins) {
        if (unlocked) return false;
        switch(type) {
            case CLICKS: if (clicks > requiredValue) return true;
            case WINS: if (wins > requiredValue) return true;
            default: return false;
        }
    }

    public String getShortDesc() {
        return shortDesc;
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

    public int getLockedIcon() {
        return lockedIcon;
    }

    public int getUnlockedIcon() {
        return unlockedIcon;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public int getIcon() {
        if (isUnlocked()) {
            return unlockedIcon;
        } else {
            return lockedIcon;
        }
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "unlocked=" + unlocked +
                ", longDesc='" + longDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clicksRequired=" + requiredValue +
                '}';
    }
}
