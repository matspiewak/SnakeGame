package com.example.myapplication;

public class Users {
    private String userName;
    private String userScore;

    public Users(){}

    public Users(String userName, String userScore){
        this.userName = userName;
        this.userScore = userScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsernName(String userName) {
        this.userName = userName;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String toString(){
        return this.userName+": "+userScore;
    }
}
