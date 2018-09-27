package com.example.sridip.donate_blood;

public class all_donor {

    public String userName;
    public String userBlood;
    public String userPh;

    public String userType;



    public all_donor(){

    }


    public all_donor(String userName, String userBlood, String userPh, String userType) {
        this.userName = userName;
        this.userBlood = userBlood;
        this.userPh = userPh;
        this.userType = userType;
    }


    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBlood() {

        return userBlood;

    }

    public void setUserBlood(String userBlood) {
        this.userBlood = userBlood;
    }

    public String getUserPh() {

        return userPh;

    }

    public void setUserPh(String userPh) {
        this.userPh = userPh;
    }



    public String getUserType() {

        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
