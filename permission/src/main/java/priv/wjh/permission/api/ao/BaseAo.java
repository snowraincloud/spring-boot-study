package priv.wjh.permission.api.ao;


/**
 * @author wangjunhao
 */
public class BaseAo {
    private String data;
    private String op;
    private String token;

    public BaseAo() {
    }

    public BaseAo(String data, String op, String token) {
        this.data = data;
        this.op = op;
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        return "BaseAo{" +
                "data='" + data + '\'' +
                ", op='" + op + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
