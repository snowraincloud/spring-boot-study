package priv.wjh.permission.infrastructure.rsp;


import priv.wjh.permission.infrastructure.enums.rsp.IRspEnum;

import java.util.Date;

public class Rsp<T> {

    private String url;

    private Integer code;

    private String msg;

    private T data;

    private Date createTime;

    private String op;

    private String token;

    public static <T> Rsp<T> ok(String message, T data){
        Rsp<T> rsp = new Rsp<>();
        rsp.setCode(1);
        rsp.setMsg(message);
        rsp.setData(data);
        rsp.setCreateTime(new Date());
        return rsp;
    }

    public static <T> Rsp<T> ok(T data){
        Rsp<T> rsp = new Rsp<>();
        rsp.setCode(1);
        rsp.setMsg("响应成功");
        rsp.setData(data);
        rsp.setCreateTime(new Date(System.currentTimeMillis()));
        return rsp;
    }

    public static <T> Rsp<T> fail(IRspEnum rspEnum){
        return new Rsp<>(rspEnum.getCode(), rspEnum.getMessage());
    }

    public static <T> Rsp<T> fail(int code, String msg){
        return new Rsp<>(code, msg);
    }


    public static <T> Rsp<T> error(String error){
        Rsp<T> rsp = new Rsp<>();
        rsp.setCode(0);
        rsp.setMsg(error);
        rsp.setCreateTime(new Date(System.currentTimeMillis()));
        return rsp;
    }

    public static <T> Rsp<T> error(Integer code, String error){
        Rsp<T> rsp = new Rsp<>();
        rsp.setCode(code);
        rsp.setMsg(error);
        rsp.setCreateTime(new Date(System.currentTimeMillis()));
        return rsp;
    }

    public Rsp() {
    }

    public Rsp(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.createTime = new Date(System.currentTimeMillis());
    }

    public Rsp(String url, Integer code, String msg, T data, Date createTime, String op, String token) {
        this.url = url;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.createTime = createTime;
        this.op = op;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Rsp{" +
                "url='" + url + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", createTime=" + createTime +
                ", op='" + op + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}
