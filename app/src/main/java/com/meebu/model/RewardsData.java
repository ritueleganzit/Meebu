package com.meebu.model;

public class RewardsData
{
    String background,title,amount;
    int backgroundId;

    public RewardsData(String background, String title, String amount, int backgroundId) {
        this.background = background;
        this.title = title;
        this.amount = amount;
        this.backgroundId = backgroundId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }
}
