package com.xuecheng.base.execption;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 学成在线项目异常类
 * @date 2023/1/29 18:15
 */
public class XueChengPlusException extends RuntimeException{
    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public XueChengPlusException() {
        super();
    }

    public XueChengPlusException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError){
        throw new XueChengPlusException(commonError.getErrMessage());
    }
    public static void cast(String errMessage){
        throw new XueChengPlusException(errMessage);
    }
}
