package model;

public class baseUserData {
    String type=null;
    String id=null;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public baseUserData(){
    }
    public baseUserData(String type,String id){
        this.type=type;
        this.id=id;
    }
}
