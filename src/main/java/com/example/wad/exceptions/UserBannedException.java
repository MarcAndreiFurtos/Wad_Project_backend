package com.example.wad.exceptions;

public class UserBannedException extends RuntimeException{
    public UserBannedException(String exMessage,Exception exception){
        super (exMessage,exception);
    }
    public UserBannedException(String exMessage){
        super (exMessage);
    }
    public UserBannedException(){
        super ("the user is banned");
    }
}
