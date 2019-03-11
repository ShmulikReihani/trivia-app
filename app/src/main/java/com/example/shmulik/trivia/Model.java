package com.example.shmulik.trivia;

public class Model {

    public String mName,mScore, mId, mDate;

    public Model(String name,String score, String mId, String mDate)
    {
        this.mName = name;
        this.mScore = score;
        this.mId = mId;
        this.mDate = mDate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String name) {
        this.mName = name;
    }

    public String getmScore() {
        return mScore;
    }

    public void setmScore(String score) {
        this.mScore = score;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
