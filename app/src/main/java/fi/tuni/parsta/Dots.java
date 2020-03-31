package fi.tuni.parsta;

public class Dots {
    private int Thumbnail;
    private String notPlayedIcon;
    private String rightIcon;
    private String wrongIcon;
    private String currentIcon;
    private boolean isPlayed;
    private boolean isRight;
    private boolean isCurrent;

    public Dots() {

    }

    public String getImage() {
        if(isPlayed && !isCurrent) {
            if(isRight) {
                return rightIcon;
            } else {
                return wrongIcon;
            }
        } else if(isCurrent) {
            return currentIcon;
        } else {
            return notPlayedIcon;
        }
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public boolean isRight() {
        return isRight;
    }

    public String getNotPlayedIcon() {
        return notPlayedIcon;
    }

    public void setNotPlayedIcon(String notPlayedIcon) {
        this.notPlayedIcon = notPlayedIcon;
    }

    public String getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(String rightIcon) {
        this.rightIcon = rightIcon;
    }

    public String getWrongIcon() {
        return wrongIcon;
    }

    public void setWrongIcon(String wrongIcon) {
        this.wrongIcon = wrongIcon;
    }

    public String getCurrentIcon() {
        return currentIcon;
    }

    public void setCurrentIcon(String currentIcon) {
        this.currentIcon = currentIcon;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public Dots(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
