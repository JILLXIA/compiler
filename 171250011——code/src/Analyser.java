public class Analyser {
    private Output output;
    private STATE state;
    private Input input;
    private StringBuffer buffer_char;
    private StringBuffer buffer_digit;
    private StringBuffer buffer_annotation;
    private StringBuffer buffer_quote;
    private Token temptoken;
    public Analyser(){
        this.input=new Input();
        this.output = new Output();
        this.state = STATE.END;
        this.buffer_char = new StringBuffer();
        this.buffer_digit = new StringBuffer();
        this.buffer_annotation = new StringBuffer();
        this.buffer_quote = new StringBuffer();
    }
    private static String[] reservedWords={"abstract","assert","boolean","break","byte","case","catch",
            "char","class","const","continue","default","do","double","else","enum",
            "extends","final","finally","float","for","false","goto",
            "if","implements","import","instanceof","int",
            "interface","long","main","native","new","package",
            "private","protected","public","return","short","static",
            "super","switch","synchronized","String","this",
            "throw","throws","transient","true","try","void","volatile","while" };

    public static boolean isLetter(char c){
        return Character.isLetter(c)||c=='$'||c=='_';
    }

    public static boolean isDigit(char c){
        return Character.isDigit(c);
    }
    public static boolean isnzDigit(char c){
        return Character.isDigit(c)&&c!='0';
    }
    public static boolean isreservedWords(String s){
        for(int i=0;i<reservedWords.length;i++){
            if(s.equals(reservedWords[i])){
                return true;
            }
        }
        return false;
    }

    public void start(){
        char[] temp_c;
        int index;
        //for(int i=0;i<Input.s.length;i++){
        //    temp_c=Input.s[i].toCharArray();//把每一行变成char数组
        index=0;
        temp_c=Input.s.toCharArray();
        while(index!=temp_c.length){
            switch(this.state){
                case END:
                    changeState(temp_c[index],index,temp_c);
                    break;
                case LETTER:
                    if(isDigit(temp_c[index]) || isLetter(temp_c[index])){
                        buffer_char.append(temp_c[index]);
                    }else{
                        if(isreservedWords(buffer_char.toString())){
                            temptoken=new Token("ReservedWords",buffer_char.toString(),"no error");
                            temptoken.addTokens();
                            temptoken.addTokens_o();
                            //output.outputIntoBuffer("Keyword            " + buffer_char.toString());
                        }else{
                            temptoken=new Token("Identifiers",buffer_char.toString(),"no error");
                            temptoken.addTokens();
                            temptoken.addTokens_o();
                        }

                        state = STATE.END;
                        buffer_char = new StringBuffer();//清空了
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case NZ_INT:
                    if(isDigit(temp_c[index])){
                        buffer_digit.append(temp_c[index]);
                    }else if(temp_c[index] == '.'){
                        buffer_digit.append(temp_c[index]);
                        state = STATE.NZ_DOT;
                    }else if(isLetter(temp_c[index])){
                        int temp=index;
                        while(isDigit(temp_c[temp])||isLetter(temp_c[temp])){
                            //System.out.println(buffer_digit.toString());
                            buffer_digit.append(temp_c[temp]);
                            temp++;
                        }
                        index=temp;
                        temptoken=new Token("UNKNOWN",buffer_digit.toString(),"iserror");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);//出现
                    }else{
                        state = STATE.END;
                        temptoken=new Token("INT",buffer_digit.toString(),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();//清空了
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case NZ_DOT:
                    state = STATE.NZ_DOUBLE;
                    buffer_digit.append(temp_c[index]);
                    break;
                case NZ_DOUBLE:
                    if(isDigit(temp_c[index])){
                        buffer_digit.append(temp_c[index]);
                    }else if(isLetter(temp_c[index])){
                        int temp=index;
                        while(isDigit(temp_c[temp])||isLetter(temp_c[temp])){
                            //System.out.println(buffer_digit.toString());
                            buffer_digit.append(temp_c[temp]);
                            temp++;
                        }
                        index=temp;
                        temptoken=new Token("UNKNOWN",buffer_digit.toString(),"iserror");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }else{
                        state = STATE.END;
                        temptoken=new Token("Double",buffer_digit.toString(),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case Z_INT:
                    if(temp_c[index]=='0'){
                        buffer_digit.append(temp_c[index]);
                    }else if(temp_c[index] == '.'){
                        buffer_digit.append(temp_c[index]);
                        state = STATE.NZ_DOT;
                    }else if(isLetter(temp_c[index])||isnzDigit(temp_c[index])){
                        //System.out.println(buffer_digit.toString());
                        int temp=index;
                        while(isDigit(temp_c[temp])||isLetter(temp_c[temp])){
                            //System.out.println(buffer_digit.toString());
                            buffer_digit.append(temp_c[temp]);
                            temp++;
                        }
                        index=temp;

                        temptoken=new Token("UNKNOWN",buffer_digit.toString(),
                                "iserror");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);//出现
                    }else{
                        state = STATE.END;
                        temptoken=new Token("INT",buffer_digit.toString(),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();//清空了
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case Z_DOT:
                    state = STATE.Z_DOUBLE;
                    buffer_digit.append(temp_c[index]);
                    break;
                case Z_DOUBLE:
                    if(isDigit(temp_c[index])){
                        buffer_digit.append(temp_c[index]);
                    }else if(isLetter(temp_c[index])){
                        int temp=index;
                        while(isDigit(temp_c[temp])||isLetter(temp_c[temp])){
                            //System.out.println(buffer_digit.toString());
                            buffer_digit.append(temp_c[temp]);
                            temp++;
                        }
                        index=temp;
                        temptoken=new Token("UNKNOWN",buffer_digit.toString(),"iserror");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }else{
                        state = STATE.END;
                        temptoken=new Token("Double",buffer_digit.toString(),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_digit = new StringBuffer();
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case ADD:
                    if(isnzDigit(temp_c[index])&&index>=2&&!isDigit(temp_c[index-2])&&!isLetter(temp_c[index-2])){
                        buffer_digit.append(temp_c[index]);
                        state=STATE.NZ_INT;
                    }else if(temp_c[index]=='0'){
                        buffer_digit.append(temp_c[index]);
                        state=STATE.Z_INT;
                    }else if(temp_c[index] == '+' || temp_c[index] == '='){
                        temptoken=new Token("Operator","+"+temp_c[index],"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                    }else if(temp_c[index] == ' '||temp_c[index] == '\n'||temp_c[index] == '\t'||isDigit(temp_c[index])){
                        temptoken=new Token("Operator","+","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }else{
                        //temptoken=new Token("Operator","+","no error");
                        //temptoken.addTokens();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case SUB:

                    if(isnzDigit(temp_c[index])&&index>=2&&!isDigit(temp_c[index-2])&&!isLetter(temp_c[index-2])){
                        buffer_digit.append(temp_c[index]);
                        state=STATE.NZ_INT;
                    }else if(temp_c[index]=='0'){
                        buffer_digit.append(temp_c[index]);
                        state=STATE.Z_INT;
                    }else if(temp_c[index] == '-' || temp_c[index] == '='){
                        temptoken=new Token("Operator","-"+temp_c[index],"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                    }else if(temp_c[index] == ' '||temp_c[index] == '\n'||temp_c[index] == '\t'||isDigit(temp_c[index])){
                        temptoken=new Token("Operator","-","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }else{
                        temptoken=new Token("Operator","-","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case DIV:
                    if(temp_c[index] == '='){
                        temptoken=new Token("Operator","/"+temp_c[index],"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                    }else if(temp_c[index] == '/'){
                        temptoken=new Token("Annotation Mark","//","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.NOTE2;
                    }else if(temp_c[index] == '*'){
                        temptoken=new Token("Annotation Mark","/*","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.NOTE1;
                    }else{
                        temptoken=new Token("Operator","/","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case PRO:
                    if(temp_c[index] == '=') {
                        temptoken = new Token("Operator", "*" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","*","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case PER:
                    if(temp_c[index] == '=') {
                        temptoken = new Token("Operator", "%" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","%","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case NOT:
                    if(temp_c[index] == '=') {
                        temptoken = new Token("Operator", "!" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","!","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case EQU:
                    if(temp_c[index] == '=') {
                        temptoken = new Token("Operator", "=" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","=","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case LT:
                    if(temp_c[index] == '='||temp_c[index] == '<') {
                        temptoken = new Token("Operator", "<" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","<","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case GT:
                    if(temp_c[index] == '='||temp_c[index] == '>') {
                        temptoken = new Token("Operator", ">" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator",">","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case AND:
                    if(temp_c[index] == '&') {
                        temptoken = new Token("Operator", "&" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","&","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case OR:
                    if(temp_c[index] == '|') {
                        temptoken = new Token("Operator", "|" + temp_c[index], "no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;

                    }else{
                        temptoken=new Token("Operator","|","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        state = STATE.END;
                        changeState(temp_c[index],index,temp_c);
                    }
                    break;
                case NOTE2:
                    if(temp_c[index] == '\n'){  //说明读完了一行注释
                        state = STATE.END;
                        temptoken=new Token("Annotation",buffer_annotation.toString(),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_annotation = new StringBuffer();
                    }else{
                        buffer_annotation.append(temp_c[index]);
                    }
                    break;
                case NOTE1:
                    if(temp_c[index] == '*'){
                        state = STATE.NOTE3;
                        //buffer_annotation.append(temp_c[index]);
                    }else{
                        ;//还是在这个状态没有动
                    }
                    buffer_annotation.append(temp_c[index]);
                    //System.out.println(buffer_annotation.toString());
                    //changeState(temp_c[index],index,temp_c);
                    break;
                case NOTE3:
                    if(temp_c[index]=='/'){
                        state = STATE.END;
                        //System.out.println(buffer_annotation.toString());
                        temptoken=new Token("Annotation",buffer_annotation.substring(0, buffer_annotation.length()-2),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        temptoken=new Token("Annotation Mark","*/","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_annotation=new StringBuffer();
                    }else if(temp_c[index]=='*'){
                        //state = STATE.NOTE1;
                        buffer_annotation.append(temp_c[index]);
                    }else{
                        state = STATE.NOTE1;
                        buffer_annotation.append(temp_c[index]);
                    }
                    break;
                case STRING:
                    if(temp_c[index] == '"'){
                        state = STATE.END;
                        temptoken=new Token("String",buffer_quote.toString(),"no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        temptoken=new Token("Operator","\"","no error");
                        temptoken.addTokens();
                        temptoken.addTokens_o();
                        buffer_quote = new StringBuffer();
                    }else{
                        buffer_quote.append(temp_c[index]);
                    }
                    break;
            }
            index++;
        }
        output.writeTxt();
    }

    public void changeState(char c,int index,char[] temp_c){
        if(isLetter(c)){
            buffer_char.append(c);
            state =STATE.LETTER;
        }else if(isnzDigit(c)){
            buffer_digit.append(c);
            state=STATE.NZ_INT;
        }else if(c=='0'){
            buffer_digit.append(c);
            state=STATE.Z_INT;
        }else if(c=='+'){
            //加减是数字还是操作符？？？？？？？？
            if(index==0||(!isDigit(temp_c[index-1])&&!isLetter(temp_c[index-1]))){
                //如果加号出现在第一个，或者前一个不是数字，前一个不是字母
                //那么这个就是操作符
                buffer_digit.append(c);
            }else{
                //System.out.println("gg");
            }
            state=STATE.ADD;
        }else if(c=='-'){
            if(index==0||(!isDigit(temp_c[index-1])&&!isLetter(temp_c[index-1]))){
                //System.out.println("hh");
                buffer_digit.append(c);
                //如果加号出现在第一个，或者前一个不是数字，前一个不是字母
                //那么这个就是操作符
            }else{
            }
            state=STATE.SUB;
        }else if(c=='/'){
            state=STATE.DIV;
        }else if(c=='*'){
            state=STATE.PRO;
        }else if(c=='%'){
            state=STATE.PER;
        }else if(c=='='){
            state=STATE.EQU;
        }else if(c=='!'){
            state=STATE.NOT;
        } else if(c=='<'){
            state=STATE.LT;
        }else if(c=='>'){
            state=STATE.GT;
        }else if(c=='&'){
            state=STATE.AND;
        }else if(c=='|'){
            state=STATE.OR;
        }else if(c=='"'){
            state=STATE.STRING;
            temptoken = new Token("Operator",String.valueOf(c), "no error");
            temptoken.addTokens();
            temptoken.addTokens_o();
        }else if(c==';'||c=='{'||c=='}') {
            state = STATE.END;
            temptoken = new Token("Delimiters",String.valueOf(c), "no error");
            temptoken.addTokens();
            temptoken.addTokens_o();
        }else if(c=='('||c==')'||c=='[' ||c==']'||c=='^'||c=='~'||c=='='||c=='.'||c=='\''){
            state = STATE.END;
            temptoken = new Token("Operator",String.valueOf(c), "no error");
            temptoken.addTokens();
            temptoken.addTokens_o();
        }else if(c==' '||c=='\n'||c=='\t') {

        }else{
                state = STATE.END;
                temptoken = new Token("UNKNOWN",String.valueOf(c),
                        "This is not a valid character.");
                temptoken.addTokens();
                temptoken.addTokens_o();
        }

    }

    public static  void main(String[] args){
        Analyser a=new Analyser();
        a.start();
       // System.out.println(Output.tokens_o.get(0).category);
        LL1 ll1=new LL1();
        ll1.ll1_start();
    }
}
