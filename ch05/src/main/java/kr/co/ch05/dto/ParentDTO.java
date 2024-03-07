package kr.co.ch05.dto;

import java.util.List;

public class ParentDTO {
    private String pid;
    private String name;
    private String birth;
    private String addr;

    private List<ChildDTO> childs;

    public List<ChildDTO> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildDTO> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "ParentDTO{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
