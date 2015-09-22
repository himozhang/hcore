package com.ideal.framework.utils.string;

import java.util.Scanner;
 
public class Num2Chinese {
    private String integerPart;
    private String floatPart;
    private static String[] cnNumArr={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
    private static String[] unitArr={"","十","百","千"};
 
    public Num2Chinese(String num){
 
        integerPart = "";
        floatPart = "";
 
        if(num.contains(".")){
            int dotIndex = num.indexOf(".");
            integerPart = num.substring(0,dotIndex);
            floatPart = num.substring(dotIndex+1);
        }else{
            integerPart = num;
        }
    }
 
    public String getCnSting(){
 
        int integer=Integer.parseInt(integerPart);
 
        int[] parts = new int[10];
        int partCount = 0;
        for(int i=0; ; i++) {
            if(integer == 0)
                break;
            int part = integer % 10000;
            parts[i] = part;
            partCount ++;
            integer = integer / 10000;
        }
 
        boolean beforeWanIsZero = true;
 
        String chineseStr = "";
 
        for(int i=0; i<partCount; i++) {
            String partChinese = integerPart2Chinese(parts[i]);
            if(i % 2 == 0) {
                if("".equals(partChinese)){
                    beforeWanIsZero = true;
                }else{
                    beforeWanIsZero = false;
                }
            }
 
            if(i!= 0){
                if(i % 2 == 0){
                    chineseStr = "亿" + chineseStr;
                }else {
                    if("".equals(partChinese) && !beforeWanIsZero)
                        chineseStr = "零" + chineseStr;
                    else {
                        if(parts[i-1] < 1000 && parts[i-1] > 0)
                            chineseStr = "零" + chineseStr;
                        chineseStr = "万" + chineseStr;
 
                    }
                }
            }
            chineseStr = partChinese + chineseStr;
        }
 
        if(floatPart.length()>0){
            chineseStr = chineseStr + "点";
            for(int i=0;i<floatPart.length();i++){
                String floatStr = String.valueOf(floatPart.charAt(i));
                int floatIndex = Integer.parseInt(floatStr);
                chineseStr=chineseStr + cnNumArr[floatIndex];
            }
        }
        return chineseStr;
    }
 
    private static String integerPart2Chinese(int integer) {
        int temp = integer;
        boolean lastNumIsZero = true;
 
        String integerStr = new Integer(integer).toString();
 
        String chineseStr = "";
 
        for(int i=0; i<integerStr.length(); i++) {
            if(temp == 0)
                break;
            int digit = temp % 10;
            if(digit == 0) {
                if(!lastNumIsZero)
                    chineseStr = "零" + chineseStr;
                lastNumIsZero = true;
            }else {
                chineseStr = cnNumArr[digit] + unitArr[i] + chineseStr;
                lastNumIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }
 
 
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String num = scan.nextLine();
        String numRegEx = "^[0-9]*[\\.]?[0-9]*$";
        if(!num.matches(numRegEx)){
            System.out.println("请输入正确的数字格式！");
        }
        Num2Chinese n2c = new Num2Chinese(num);
        String str = n2c.getCnSting();
        System.out.println(str);
    }
 
}