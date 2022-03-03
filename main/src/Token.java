public class Token {
    private final String type;
    private final String value;

    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }


    @Override
    public String toString(){
        return "TOKEN[type=\"" + this.type + "\", value=\"" + this.value + "\"]";
    }
}
