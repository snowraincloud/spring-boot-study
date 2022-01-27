package priv.wjh.permission.api.ao;


/**
 * @author wangjunhao
 */
public class BaseDataAo<T> {
    private Integer pageSize;
    private Integer currentPage;
    private T body;

    @Override
    public String toString() {
        return "BaseDataAo{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", body=" + body +
                '}';
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
