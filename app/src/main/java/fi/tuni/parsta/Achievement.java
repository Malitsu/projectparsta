package fi.tuni.parsta;

class Achievement {
    private int id;
    private boolean unlocked = false;
    private String longDesc = "This is a placeholder text for long text";
    private String shortDesc = "This is a placeholder text for short text";
    private int requiredValue;
    private String lockedIcon;
    private String unlockedIcon;
    private Type type;
    private enum Type {
        CLICKS, WINS, LEVEL
    }

    public Achievement() {
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

    public String getLongDesc() {
        return longDesc;
    }

    public String getIcon() {
        if (isUnlocked()) {
            return unlockedIcon;
        } else {
            return lockedIcon;
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

    public String getLockedIcon() {
        return lockedIcon;
    }

    public void setLockedIcon(String lockedIcon) {
        this.lockedIcon = lockedIcon;
    }

    public String getUnlockedIcon() {
        return unlockedIcon;
    }

    public void setUnlockedIcon(String unlockedIcon) {
        this.unlockedIcon = unlockedIcon;
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

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", unlocked=" + unlocked +
                ", longDesc='" + longDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", requiredValue=" + requiredValue +
                ", lockedIcon='" + lockedIcon + '\'' +
                ", unlockedIcon='" + unlockedIcon + '\'' +
                ", type=" + type +
                '}';
    }
}
