package fi.tuni.parsta;

class Achievement {
    private int id;
    private boolean unlocked = false;
    private String longDesc = "This is a placeholder text for long text";
    private String shortDesc = "This is a placeholder text for short text";
    private int requiredValue;
    private Type type;
    private enum Type {
        CLICKS, WINS, LEVEL
    }

    public Achievement(int requiredValue) {
        this.requiredValue = requiredValue;
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

    public String getLongDesc() {
        return longDesc;
    }

    public String getIcon() {
        if (isUnlocked()) {
            return "unlocked";
        } else {
            return "locked";
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
