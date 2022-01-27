package priv.wjh.permission.api.vo;

import java.util.ArrayList;
import java.util.List;

public class PesVo {
    private Long id;
    private String label;
    private List<PesVo> children = new ArrayList<>();

    public void add(PesVo pesVo){
        children.add(pesVo);
    }

    public PesVo() {
    }

    public PesVo(Long id, String label, List<PesVo> children) {
        this.id = id;
        this.label = label;
        this.children = children;
    }
    public PesVo(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<PesVo> getChildren() {
        return children;
    }

    public void setChildren(List<PesVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "PesVo{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
