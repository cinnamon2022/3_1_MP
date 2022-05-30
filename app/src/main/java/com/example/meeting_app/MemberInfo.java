package com.example.meeting_app;


public class MemberInfo {
    private String name;
    private String phoneNum;
    private String birthDate;
    private String address;
    private String photoUrl;

    public MemberInfo(String name, String phoneNum, String birthDate, String address, String photoUrl){
        this.name = name;
        this.phoneNum = phoneNum;
        this.birthDate = birthDate;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public MemberInfo(String name, String phoneNum, String birthDate, String address){
        this.name = name;
        this.phoneNum = phoneNum;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getBirthDate(){
        return this.birthDate;
    }
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }

    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getPhotoUrl(){
        return this.photoUrl;
    }
    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
