import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;
import org.nevec.rjm.BigDecimalMath;
/**
 * Created by USUARIO on 27/09/2017.
 */

class BasicCalculator {
    private String operation;
    private static String operationSings= "+-*/()^sinsqrtcos";
    private static String operations="+-*/^sinsqrtcos";
    private static String oneOperatorSings = "sincostanlnsqrt";
    private static boolean inDegree = true;
    private static int numberOfDecimals=10;

    public static void main(String[] args) {

        //Entrada de datos
        System.out.println("*^Escribe una expresión algebraica: ");
        //String operacion="sqrt(1.2+2.8) - sqrt(3*3)";
        String operacion="cos(90)";

        //Depurar la expresion algebraica
        String expr = depurar(operacion);
        String[] arrayInfix = expr.split(" ");

        //Declaración de las pilas
        Stack < String > E = new Stack < String > (); //Pila entrada
        Stack < String > P = new Stack < String > (); //Pila temporal para operadores
        Stack < String > S = new Stack < String > (); //Pila salida

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfix.length - 1; i >= 0; i--) {
            E.push(arrayInfix[i]);
        }

        try {
            //Algoritmo Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())){
                    case 1:
                        P.push(E.pop());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        while(pref(P.peek()) >= pref(E.peek())) {
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    case 2:
                        while(!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }

            //Eliminacion de `impurezas´ en la expresiones algebraicas
            String infix = expr.replace(" ", "");
            String postfix = S.toString().replaceAll("[\\]\\[,]", "");
            System.out.println("Expresion Infija: " + infix);
            System.out.println("Expresion Postfija: " + postfix);
            String resultado = evaluar(S);
            //Mostrar resultados:
            System.out.println("resultado: " + resultado);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error en la expresión algebraica");
        }
    }

    //evaluación

    private static String evaluar(Stack <String> postPila){
        BigDecimal resultado = new BigDecimal("0");
        //Declaración de las pilas
        Stack < String > E = new Stack < String > (); //Pila entrada
        Stack < String > P = new Stack < String > (); //Pila de operandos

        //Añadir post (array) a la Pila de entrada (E)
        Object [] post = postPila.toArray();
        for (int i = post.length - 1; i >= 0; i--) {
            E.push((String)post[i]);
        }

        //Algoritmo de Evaluación Postfija
        while (!E.isEmpty()) {
            if (operations.contains("" + E.peek())) {
                //agrega operador
                System.out.println(" : calculando con operador: " + E.peek());
                System.out.print("pila ANTES de operar: ");
                for(int i = 0; i<P.size(); i++) {
                    System.out.print(P.elementAt(i)+ ", ");
                }
                if(oneOperatorSings.contains(E.peek())){
                    String sencondOperando = "";
                    boolean masDeUnOperando = P.size() > 1;
                    /*if(masDeUnOperando) {//si tiene mas operandos
                        sencondOperando = P.pop();
                    }*/
                    String result = cualcular1Operando(E.pop(), P.pop());
                    /*if(masDeUnOperando) {
                        P.push(sencondOperando);
                    }*/
                    P.push(result);
                }else {
                    P.push(calcular2Operandos(E.pop(), P.pop(), P.pop()) + "");
                }
                System.out.println(" : resultado: " + P.peek());
                System.out.print("pila DESPUES de operar: ");
                for(int i = 0; i<P.size(); i++) {
                    System.out.print(P.elementAt(i)+ ", ");
                }
            }else {
                //agrega operando
                System.out.print(": agregando a pila: " + E.peek());
                P.push(E.pop());
            }
        }

        return P.pop();
    }

    private static String calcular2Operandos(String op, String n2, String n1) {
        BigDecimal result = new BigDecimal("0");
        System.out.print("Calculando:" + n1 + "" + op + "" + n2);
        try{
            BigDecimal num1 = new BigDecimal(n1);
            BigDecimal num2 = new BigDecimal(n2);
            if (op.equals("+")) result = num1.add (num2);
            if (op.equals("-")) result = num1.subtract(num2);
            if (op.equals("*")) result = num1.multiply(num2);
            if (op.equals("/")) result = num1.divide(num2, numberOfDecimals, RoundingMode.HALF_DOWN);
            if (op.equals("%")) result = num1.multiply(num2.divide(new BigDecimal("100"),numberOfDecimals,RoundingMode.HALF_DOWN));
            if(op.equals("^")) result = BigDecimalMath.pow(num1.setScale(numberOfDecimals),num2.setScale(numberOfDecimals)).setScale(numberOfDecimals);
        }catch (Exception ex){
            result = new BigDecimal("-1");
            ex.printStackTrace();
        }

        return result + "";
    }


    private static String cualcular1Operando(String op, String n1) {
        BigDecimal result = new BigDecimal("0");
        System.out.print("Calculando:" + op + "("+ n1 + ")");
        try{
            BigDecimal num1 = new BigDecimal(n1);
            /*radians = degrees x PI / 180 (deg to rad conversion)
            degrees = radians x 180 / PI (rad to deg conversion) */
            if("sincostansihcoh".contains(op)) {
                //BigDecimal numRadian = num1.multiply(BigDecimalMath.pi(MathContext.DECIMAL32).divide(new BigDecimal("180"), numberOfDecimals, RoundingMode.HALF_DOWN));
               // BigDecimal numRadian = num1;
                BigDecimal numSemi = num1.multiply(new BigDecimal("3.14159"));
                BigDecimal numRadian = numSemi.divide(new BigDecimal("180"), numberOfDecimals, RoundingMode.HALF_DOWN);

                System.out.print("\nconversion de grado: " + num1 + " a radian: " + numRadian);
                if (op.equals("sin")){
                    if (inDegree) result = BigDecimalMath.sin(numRadian.setScale(numberOfDecimals));
                    else result = BigDecimalMath.sin(num1.setScale(numberOfDecimals)).setScale(numberOfDecimals);
                }if (op.equals("cos")){
                    if (inDegree) result = BigDecimalMath.cos(numRadian).setScale(numberOfDecimals);
                    else result = BigDecimalMath.cos(num1.setScale(numberOfDecimals)).setScale(numberOfDecimals);
                }
                if (op.equals("tan")) result = BigDecimalMath.tan(num1.setScale(numberOfDecimals)).setScale(numberOfDecimals);
                if (op.equals("sih")) result = BigDecimalMath.sinh(num1.setScale(numberOfDecimals)).setScale(numberOfDecimals);
                if (op.equals("coh")) result = BigDecimalMath.cosh(num1.setScale(numberOfDecimals)).setScale(numberOfDecimals);
            }
            System.out.println();

            if (op.equals("sqrt")) result = BigDecimalMath.sqrt(num1.setScale(numberOfDecimals)).setScale(numberOfDecimals);

        }catch (Exception ex){
            result = new BigDecimal("-1");
            ex.printStackTrace();
        }

        return result + "";
    }

    //Depurar expresión algebraica
    private static String depurar(String s) {
        System.out.println("depurar expresiónAlgebraica: " + s);
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if(operationSings.contains("" + s.charAt(i)) & !Character.isLetter(s.charAt(i))){
                str += " " + s.charAt(i) + " ";
            }else{ str += s.charAt(i);}
        }
        System.out.println("separaciób; " + str);
        return str.replaceAll("\\s+", " ").trim();
    }

    //Jerarquia de los operadores
    private static int pref(String op) {
        int prf = 99;
        if (op.equals("^") || op.equals("sin") || op.equals("sqrt")|| op.equals("cos")|| op.equals("tan")|| op.equals("sih")) prf = 5;
        if (op.equals("*") || op.equals("/")) prf = 4;
        if (op.equals("+") || op.equals("-")) prf = 3;
        if (op.equals(")")) prf = 2;
        if (op.equals("(")) prf = 1;
        return prf;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
