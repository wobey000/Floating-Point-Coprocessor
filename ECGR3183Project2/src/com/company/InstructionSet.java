package com.company;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

public class InstructionSet {

    HashMap<String, Float> registers;
    //HashMap<>

    public InstructionSet() {
        this.registers = new HashMap<>();
    }

    public InstructionSet(HashMap<String, Float> registers) {
        this.registers = registers;
    }
    //helping fuctions

    private String encodedString(String[] bitString){
        StringBuilder x = new StringBuilder();
        for(int i =0; i < bitString.length; i++){
            x.append(bitString[i]);
        }
        if(x.toString().length() != 32){
            while (x.toString().length() < 32){
                x.append("0");
            }
        }
        return x.toString();
    }

    private String regToBinary(String reg){
        String x = reg.replaceAll("R", "");
        int num = Integer.parseInt(x);
        StringBuilder binary = new StringBuilder();
        while (binary.length() < 4) {
            if((num&1)==1){
                binary.append(1);
            }else
                binary.append(0);
            num>>=1;
        }
        return binary.reverse().toString();
    }

    private void updateRegister(String reg, float value){
        if(isRegisterSet(reg)){
            //update
            registers.replace(reg, value);
        }else{
            //add
            registers.putIfAbsent(reg, value);
        }
    }

    private Boolean isRegisterSet(String reg){
        return registers.containsKey(reg);
    }

    private float getRegisterValue(String reg){
        return registers.get(reg);
    }

    public String floatToBinary(float x){
        int intBits = Float.floatToIntBits(x);
        String intBitsStr = Integer.toBinaryString(intBits);
        if(intBitsStr.length() == 31){
            StringBuilder str = new StringBuilder();
            str.append("0");
            str.append(intBitsStr);
            intBitsStr = str.toString();
        }
        return intBitsStr;
    }

    private String cleanRegister(String x){
        return x.replaceAll(",", "");
    }

    private String cleanFloat(String x){
        return x.replaceAll("#", "");
    }

    public void assembly(String x){
        System.out.println(" ");
        System.out.println(x);
        String[] code = x.toUpperCase().split(" ");
        String command = code[0];
        String[] sublist = Arrays.copyOfRange(code, 1, code.length);
        String opcode = "";
        if(sublist.length == 1){
            String reg1 = cleanRegister(sublist[0]); //store bit
            if(isRegisterSet(reg1)){
                //command encode with register encoding
                opcode = "000010";
                float value = getRegisterValue(reg1);
                System.out.println(command + " = " + opcode+ ", " + reg1 + " = " + regToBinary(reg1));
                System.out.println("This is encoded as: ");
                System.out.println(encodedString(new String[]{opcode,regToBinary(reg1)}));
                System.out.println("Value of Register " + reg1 + ": " + value); //+ " / " + floatToBinary(value));
            }else{
                System.out.println(command + " Command Failed -> Empty Register Specified");
            }

        }else if(sublist.length == 2){
            String reg1 = cleanRegister(sublist[0]); //store bit
            String reg2 = cleanRegister(sublist[1]);

            if(isRegisterSet(reg2) && !command.equals("SET")){
                //switch
                float ans = 0.0f;
                switch (command){
                    case "MOVE" :
                        opcode = "000011";
                        ans = getRegisterValue(reg2);
                        break;
                    case "FNEG" :
                        opcode = "000110";
                        ans = -1 * getRegisterValue(reg2);
                        break;
                    case "FLOOR" :
                        opcode = "001001";
                        ans = (float) Math.floor((double) getRegisterValue(reg2));
                        break;
                    case "CEIL" :
                        opcode = "001010";
                        ans = (float) Math.ceil((double) getRegisterValue(reg2));
                        break;
                    case "ROUND" :
                        opcode = "001011";
                        ans = Math.round(getRegisterValue(reg2));
                        break;
                    case "FABS" :
                        opcode = "001100";
                        ans = Math.abs(getRegisterValue(reg2));
                        break;
                    case "FINV" :
                        opcode = "001101";
                        ans = 1 / getRegisterValue(reg2);
                        break;
                    case "SIN" :
                        opcode = "010001";
                        ans = (float) Math.sin((double) getRegisterValue(reg2));
                        break;
                    case "COS" :
                        opcode = "010010";
                        ans = (float) Math.cos((double) getRegisterValue(reg2));
                        break;
                    case "TAN" :
                        opcode = "010011";
                        ans = (float) Math.tan((double) getRegisterValue(reg2));
                        break;
                    case "EXP" :
                        opcode = "010100";
                        ans = (float) Math.exp((double) getRegisterValue(reg2));
                        break;
                    case "LOG" :
                        opcode = "010101";
                        ans = (float) Math.log((double) getRegisterValue(reg2));
                        break;
                    case "SQRT" :
                        opcode = "010110";
                        ans = (float) Math.sqrt((double) getRegisterValue(reg2));
                        break;
                }
                updateRegister(reg1, ans);
                System.out.println(command + " = " + opcode+ ", " + reg1 + " = " + regToBinary(reg1));
                System.out.println("This is encoded as: ");
                System.out.println(encodedString(new String[]{opcode,regToBinary(reg1)}));
                System.out.println("Register: " + reg1 + " Set to => " + getRegisterValue(reg1));
            }else if(command.equals("SET")){
                opcode = "000001";
                float f = Float.parseFloat(cleanFloat(reg2));
                updateRegister(reg1, f);
                System.out.println(command + " = " + opcode + ", " + reg1 + " = " + regToBinary(reg1));
                System.out.println(f + " = " + floatToBinary(getRegisterValue(reg1)));
                System.out.println("This is encoded as: ");
                System.out.println(encodedString(new String[]{opcode,regToBinary(reg1)}));
                System.out.println(floatToBinary(getRegisterValue(reg1)));
                System.out.println("Register: " + reg1 + " Set to => " + getRegisterValue(reg1));
            }
            else{
                System.out.println(command + " Command Failed -> Empty Register Specified");
            }
        }else if(sublist.length == 3){
            String reg1 = cleanRegister(sublist[0]); //store bit
            String reg2 = cleanRegister(sublist[1]);
            String reg3 = cleanRegister(sublist[2]);
            if(isRegisterSet(reg2) && isRegisterSet(reg3) && !command.equals("POW")){
                //switch
                float ans = 0.0f;
                switch (command) {
                    case "FADD":
                        opcode = "000100";
                        ans = getRegisterValue(reg2) + getRegisterValue(reg3);
                        break;
                    case "FSUB":
                        opcode = "000101";
                        ans = getRegisterValue(reg2) - getRegisterValue(reg3);
                        break;
                    case "FMUL":
                        opcode = "000111";
                        ans = getRegisterValue(reg2) * getRegisterValue(reg3);
                        break;
                    case "FDIV":
                        opcode = "001000";
                        ans = getRegisterValue(reg2) / getRegisterValue(reg3);
                        break;
                    case "MIN":
                        opcode = "001110";
                        ans = Math.min(getRegisterValue(reg2), getRegisterValue(reg3));
                        break;
                    case "MAX":
                        opcode = "001111";
                        ans = Math.max(getRegisterValue(reg2), getRegisterValue(reg3));
                        break;
                }
                updateRegister(reg1, ans);
                System.out.println(command + " = " + opcode+ ", " + reg1 + " = " + regToBinary(reg1) + ", " + reg2 + " = " + regToBinary(reg2));
                System.out.println("This is encoded as: ");
                System.out.println(encodedString(new String[]{opcode,regToBinary(reg1),regToBinary(reg2)}));
                System.out.println("Register: " + reg1 + " Set to => " + getRegisterValue(reg1));
            }else if(command.equals("POW")){
                opcode = "010000";
                float f = Float.parseFloat(cleanFloat(reg3));
                float ans = (float) Math.pow(getRegisterValue(reg2), f);
                updateRegister(reg1, ans);
                System.out.println(command + " = " + opcode+ ", " + reg1 + " = " + regToBinary(reg1) + ", " + reg2 + " = " + regToBinary(reg2));
                System.out.println(f + " = " + floatToBinary(getRegisterValue(reg1)));
                System.out.println("This is encoded as: ");
                System.out.println(encodedString(new String[]{opcode,regToBinary(reg1),regToBinary(reg2)}));
                System.out.println(floatToBinary(getRegisterValue(reg1)));
                System.out.println("Register: " + reg1 + " Set to => " + getRegisterValue(reg1)); // +  " / " + floatToBinary(getRegisterValue(reg1)));
            }else{
                System.out.println(command + " Command Failed -> Empty Register Specified");
            }
        }else{
            System.out.println(command + ": Assembly code is not available or Failed to Execute");
        }
    }

}
