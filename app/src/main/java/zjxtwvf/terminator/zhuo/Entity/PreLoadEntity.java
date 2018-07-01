package zjxtwvf.terminator.zhuo.Entity;

public class PreLoadEntity {
    private String url;
    private String flag;

    public PreLoadEntity(String url,String flag){
        this.url = url;
        this.flag = flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public String getUrl() {
        return url;
    }
}
