public class Token {
    public String category;
    public  String context;
    public String error;
//    private Output output;
    public Token(String category, String context,String error){
        this.category=category;
        this.context=context;
        this.error=error;
    }
    public void addTokens(){
        String s;
        s="category : "+this.category+" ,\t context : "+this.context+" ,\t error : "+this.error;
        Output.tokens.add(s);
    }
    public void addTokens_o(){
        Output.tokens_o.add(this);
    }

}
