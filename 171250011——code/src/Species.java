import java.util.ArrayList;

public class Species {
    //每个终结符或者非终结符都有一个种别吗
    public final static int S_START = 0;
    public final static int ID = 1;
    public final static int IF = 2;
    public final static int WHILE = 3;
    public final static int S_ADD = 4;
    public final static int S_SUB = 5;
    public final static int S_MUL = 6;
    public final static int S_DIV = 7;
    public final static int SEMICOLON = 8;
    public final static int EQUAL = 9;
    public final static int S_OR = 10;
    public final static int S_AND = 11;
    public final static int LEFT_K = 12;
    public final static int RIGHT_K = 13;
    public final static int LEFT_D = 14;
    public final static int RIGHT_D = 15;
    public final static int ELSE = 16;
    public final static int D_EQUAL = 17;
    public final static int BIG = 18;
    public final static int BIG_E = 19;
    public final static int LESS = 20;
    public final static int LESS_E = 21;
    public final static int N_EQUAL = 22;
    public final static int NUMBER = 23;
    public final static int S_END = 24;
    public static final int S = 100;
    public static final int E = 101;
    public static final int E1 = 102;
    public static final int T = 103;
    public static final int T1 = 104;
    public static final int F = 105;
    public static final int C = 106;
    public static final int C1 = 107;
    public static final int D = 108;
    public static final int G = 109;
    public static final int CMP = 110;


    private int code;
    private String value;

    public String getValue(){
        return value;
    }
    public int getCode(){
        return code;
    }
    public Species(int c, String s){
        this.code = c;
        this.value = s;
    }
    public Species(){
        this.code = -1;
        this.value = "";
    }

    public void addSpecies(){
        String s;
        s="Species : {code : "+this.code+" }"+"  {value : "+this.value+" }";
        Output.species.add(new Species(this.code,this.value));
        Output.species_arr.add(s);
    }
 //   public void add_s(){

//        Output.species.add(new Species(this.code,this.value));
//    }


}
