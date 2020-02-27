import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LL1 {
    private static Stack<Species> speciesStack = new Stack<>();//栈
    private static Queue<Species> speciesQueue = new LinkedList<>();//队列
    private static int[][] ppt = PPT.getPPT();
    private Output output=new Output();
    private static String[] production={
            "S→id=E;S",
            "S→if(C){S}else{S}S",
            "S→while(C){S}S",
            "S→ε",
            "E→T E'",
            "E'→+TE'",
            "E'→-TE'",
            "E'→ε",
            "T→F T'",
            "T'→* FT'",
            "T'→/ FT'",
            "T'→ε",
            "F→(E)",
            "F→id",
            "F→number",
            "C→D C'",
            "C'→||DC'",
            "C'→&&DC'",
            "C'→ε",
            "D→(C)",
            "D→G CMP G",
            "G→number",
            "G→id",
            "CMP → >",
            "CMP → >=",
            "CMP → <",
            "CMP → <=",
            "CMP → ==",
            "CMP → !="
    };
    public static  ArrayList<String> production_arr = new ArrayList<String>();
    public void ll1_start(){
        speciesStack = new Stack();
        speciesStack.push(new Species(Species.S, "$l"));
        speciesStack.push(new Species(Species.S, "S"));
        for (int i=0;i<Output.species.size();i++) {
            speciesQueue.add(Output.species.get(i));
        }
        Species species1 = null;
        Species species2 = null;
        while (speciesQueue.peek().getCode() != Species.S_END) {
            species1 = speciesStack.peek();
            species2 = speciesQueue.peek();
            if (species1.getCode() >= 100) {
                if (!generate(species1, species2.getCode())) {
                    break;
                }
            } else {
                if (species1.getCode() == species2.getCode()) {
                    speciesStack.pop();
                    speciesQueue.remove();
                } else {
                    break;
                }
            }
        }
        output.writeTxt2();
    }

    private boolean generate(Species species, int next) {
        try {
            int nextGenerator = ppt[species.getCode() - 100][next-1];
            if (nextGenerator < 0) {
                return false;
            }
            production_arr.add(production[nextGenerator]);
            speciesStack.pop();
            switch (nextGenerator) {
                case 0:
                    speciesStack.push(new Species(Species.S, species.getValue()));
                    speciesStack.push(new Species(Species.SEMICOLON, species.getValue()));
                    speciesStack.push(new Species(Species.E, species.getValue()));
                    speciesStack.push(new Species(Species.EQUAL, species.getValue()));
                    speciesStack.push(new Species(Species.ID, species.getValue()));
                    break;
                case 1:
                    speciesStack.push(new Species(Species.S, species.getValue()));
                    speciesStack.push(new Species(Species.RIGHT_D, species.getValue()));
                    speciesStack.push(new Species(Species.S, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_D, species.getValue()));
                    speciesStack.push(new Species(Species.ELSE, species.getValue()));
                    speciesStack.push(new Species(Species.RIGHT_D, species.getValue()));
                    speciesStack.push(new Species(Species.S, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_D, species.getValue()));
                    speciesStack.push(new Species(Species.RIGHT_K, species.getValue()));
                    speciesStack.push(new Species(Species.C, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_K, species.getValue()));
                    speciesStack.push(new Species(Species.IF, species.getValue()));
                    break;
                case 2:
                    speciesStack.push(new Species(Species.S, species.getValue()));
                    speciesStack.push(new Species(Species.RIGHT_D, species.getValue()));
                    speciesStack.push(new Species(Species.S, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_D, species.getValue()));
                    speciesStack.push(new Species(Species.RIGHT_K, species.getValue()));
                    speciesStack.push(new Species(Species.C, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_K, species.getValue()));
                    speciesStack.push(new Species(Species.WHILE, species.getValue()));
                    break;
                case 3:
                    break;
                case 4:
                    speciesStack.push(new Species(Species.E1, species.getValue()));
                    speciesStack.push(new Species(Species.T, species.getValue()));
                    break;
                case 5:
                    speciesStack.push(new Species(Species.E1, species.getValue()));
                    speciesStack.push(new Species(Species.T, species.getValue()));
                    speciesStack.push(new Species(Species.S_ADD, species.getValue()));
                    break;
                case 6:
                    speciesStack.push(new Species(Species.E1, species.getValue()));
                    speciesStack.push(new Species(Species.T, species.getValue()));
                    speciesStack.push(new Species(Species.S_SUB, species.getValue()));
                    break;
                case 7:
                    break;
                case 8:
                    speciesStack.push(new Species(Species.T1, species.getValue()));
                    speciesStack.push(new Species(Species.F, species.getValue()));
                    break;
                case 9:
                    speciesStack.push(new Species(Species.T1, species.getValue()));
                    speciesStack.push(new Species(Species.F, species.getValue()));
                    speciesStack.push(new Species(Species.S_MUL, species.getValue()));
                    break;
                case 10:
                    speciesStack.push(new Species(Species.T1, species.getValue()));
                    speciesStack.push(new Species(Species.F, species.getValue()));
                    speciesStack.push(new Species(Species.S_DIV, species.getValue()));
                    break;
                case 11:
                    break;
                case 12:
                    speciesStack.push(new Species(Species.RIGHT_K, species.getValue()));
                    speciesStack.push(new Species(Species.E, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_K, species.getValue()));
                    break;
                case 13:
                    speciesStack.push(new Species(Species.ID, species.getValue()));
                    break;
                case 14:
                    speciesStack.push(new Species(Species.NUMBER, species.getValue()));
                    break;
                case 15:
                    speciesStack.push(new Species(Species.C1, species.getValue()));
                    speciesStack.push(new Species(Species.D, species.getValue()));
                    break;
                case 16:
                    speciesStack.push(new Species(Species.C1, species.getValue()));
                    speciesStack.push(new Species(Species.D, species.getValue()));
                    speciesStack.push(new Species(Species.S_OR, species.getValue()));
                    break;
                case 17:
                    speciesStack.push(new Species(Species.C1, species.getValue()));
                    speciesStack.push(new Species(Species.D, species.getValue()));
                    speciesStack.push(new Species(Species.S_AND, species.getValue()));
                    break;
                case 18:
                    break;
                case 19:
                    speciesStack.push(new Species(Species.RIGHT_K, species.getValue()));
                    speciesStack.push(new Species(Species.C, species.getValue()));
                    speciesStack.push(new Species(Species.LEFT_K, species.getValue()));
                    break;
                case 20:
                    speciesStack.push(new Species(Species.G, species.getValue()));
                    speciesStack.push(new Species(Species.CMP, species.getValue()));
                    speciesStack.push(new Species(Species.G, species.getValue()));
                    break;
                case 21:
                    speciesStack.push(new Species(Species.NUMBER, species.getValue()));
                    break;
                case 22:
                    speciesStack.push(new Species(Species.ID, species.getValue()));
                    break;
                case 23:
                    speciesStack.push(new Species(Species.BIG, species.getValue()));
                    break;
                case 24:
                    speciesStack.push(new Species(Species.BIG_E, species.getValue()));
                    break;
                case 25:
                    speciesStack.push(new Species(Species.LESS, species.getValue()));
                    break;
                case 26:
                    speciesStack.push(new Species(Species.LESS_E, species.getValue()));
                    break;
                case 27:
                    speciesStack.push(new Species(Species.D_EQUAL, species.getValue()));
                    break;
                case 28:
                    speciesStack.push(new Species(Species.N_EQUAL, species.getValue()));
                    break;
                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
