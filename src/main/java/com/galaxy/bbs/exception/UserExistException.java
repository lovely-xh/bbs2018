package com.galaxy.bbs.exception;

public class UserExistException extends Exception
{
    public UserExistException(String errorMsg)
    {
        super(errorMsg);
    }
}
