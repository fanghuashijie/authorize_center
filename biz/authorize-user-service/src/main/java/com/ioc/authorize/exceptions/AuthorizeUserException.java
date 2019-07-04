package com.ioc.authorize.exceptions;

/**
* @Description: 用户授权服务异常类
* @Author: DeYi Peng
* @CreateDate: 2019/6/28 10:35
* @Version: 1.0
*/
public class AuthorizeUserException extends RuntimeException {

    private String errCode;
    private String errMsg;

    public AuthorizeUserException(){}

    public AuthorizeUserException(String errCode, String errMsg) {
        super( errCode + " : " + errMsg );
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public AuthorizeUserException(String errCode, String errMsg, Throwable cause) {
        super( errCode + " : " + errMsg, cause );
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public AuthorizeUserException(String message) {
        super( message );
    }

    public AuthorizeUserException(String message, Throwable cause) {
        super( message, cause );
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
