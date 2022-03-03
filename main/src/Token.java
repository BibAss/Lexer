public class Token {
    private String type;
    private String value;

    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }


    @Override
    public String toString(){
        return "TOKEN[type=\"" + this.type + "\", value=\"" + this.value + "\"]";
    }
}
