package whyellow.tk.numad20s_huiyunwu;

public class Link {
    private final String HTTP="https://";
    private String name;
    private String url;

    public Link(){}

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String get_name(){
        return this.name;
    }

    public String get_url(){
        return this.url;
    }


}