import java.io.*;
import java.util.ArrayList;

public class Output {
    private File outputFile=null;
    private BufferedWriter writer;
    public static ArrayList<String> tokens=new ArrayList<String>();
    public static ArrayList<String> species_arr=new ArrayList<String>();
    public static ArrayList<Species> species=new ArrayList<Species>();
    public static ArrayList<Token> tokens_o=new ArrayList<Token>();

    protected Output(){
        outputFile = new File("./output.txt");
        try {
            if(!outputFile.exists()){
                outputFile.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile,true)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeTxt(){
        try {
            this.changetoSpecies();
            for(int i=0;i<species_arr.size();i++){
                writer.write(species_arr.get(i));

                //writer.write(tokens.get(i));
                writer.newLine();
            }
            writer.write("-----------------------------------------------------");
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeTxt2(){
        try {
            for (String string : LL1.production_arr){
                writer.write( string);
                //System.out.println("Generator : " + string);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changetoSpecies(){
        Species temps;
        for(int i=0;i<Output.tokens_o.size();i++){
            switch(Output.tokens_o.get(i).category){
                case "ReservedWords":
                    switch(Output.tokens_o.get(i).context){
                        case "if":
                            temps=new Species(Species.IF,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "else":
                            temps=new Species(Species.ELSE,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "while":
                            temps=new Species(Species.WHILE,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                    }
                    break;
                case "Identifiers":
                    temps=new Species(Species.ID,Output.tokens_o.get(i).context);
                    temps.addSpecies();
                    break;
                case "INT":
                    temps=new Species(Species.NUMBER,Output.tokens_o.get(i).context);
                    temps.addSpecies();
                    break;
                case "Double":
                    temps=new Species(Species.NUMBER,Output.tokens_o.get(i).context);
                    temps.addSpecies();
                    break;
                case "Operator":
                    switch(Output.tokens_o.get(i).context){
                        case "(":
                            temps=new Species(Species.LEFT_K,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case ")":
                            temps=new Species(Species.RIGHT_K,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "+":
                            temps=new Species(Species.S_ADD,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "-":
                            temps=new Species(Species.S_SUB,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "*":
                            temps=new Species(Species.S_MUL,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "/":
                            temps=new Species(Species.S_DIV,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "=":
                            temps=new Species(Species.EQUAL,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "==":
                            temps=new Species(Species.D_EQUAL,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "!=":
                            temps=new Species(Species.N_EQUAL,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "&&":
                            temps=new Species(Species.S_AND,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "||":
                            temps=new Species(Species.S_OR,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case ">":
                            temps=new Species(Species.BIG,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case ">=":
                            temps=new Species(Species.BIG_E,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "<":
                            temps=new Species(Species.LESS,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "<=":
                            temps=new Species(Species.LESS_E,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                    }
                    break;
                case "Delimiters":
                    switch (Output.tokens_o.get(i).context){
                        case ";":
                            temps=new Species(Species.SEMICOLON,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "{":
                            temps=new Species(Species.LEFT_D,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                        case "}":
                            temps=new Species(Species.RIGHT_D,Output.tokens_o.get(i).context);
                            temps.addSpecies();
                            break;
                    }
                    break;
            }
        }
        temps=new Species(Species.S_END,"$r");
        temps.addSpecies();

    }

}
