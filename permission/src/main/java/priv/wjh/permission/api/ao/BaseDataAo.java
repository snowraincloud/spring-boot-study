package priv.wjh.permission.api.ao;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;

/**
 * @author wangjunhao
 */
public class BaseDataAo<T> {

    @Max(value = 50, message = "页大小不能大于50")
    private Integer pageSize;
    @Range(min = 1, max = 20, message = "页数要在1到20之间")
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
