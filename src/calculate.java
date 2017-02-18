/**
 * Created by lexy on 2017/2/14.
 */
package mask;

import java.util.Date;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class calculate {
    public static String str = "";
    public static int num = 3;
    public static int num_r = 0;
    public static int numberRange = 20;
    public static number sum = new number();
    public static class number {
        private int numerator,denominator;
        public number() {
            numerator=0;
            denominator=1;
        }
        public number (String str) {
            int pos=str.indexOf('/');
            if(pos==-1)
            {
                numerator=Integer.parseInt(str);
                denominator=1;
            }
            else {
                numerator=Integer.parseInt(str.substring(0, pos).trim());
                if(pos!=str.length()-1)
                    denominator=Integer.parseInt(str.substring(pos+1).trim());
                else denominator=1;
            }
        }
        public number(int a) {
            numerator = a;
            denominator = 1;
        }

        public number(int a, int b){
            numerator = a;
            denominator = b;
        }

        public void set(int a) {
            numerator = a;
            denominator = 1;
        }

        public void set(int a,int b) {
            numerator = a;
            denominator = b;
        }

        public String toString() {
            check();
            if (numerator == 0)
            {
                return "0";
            }
            else{
                if (denominator == 1){
                    return numerator+"";
                }
                else{
                    return "("+numerator+"/"+denominator+")";
                }
            }
        }

        public void check()
        {
            if(numerator<0&&denominator<0||numerator>0&&denominator<0)
            {
                numerator=-1*numerator;
                denominator=-1*denominator;
            }
        }


        public boolean equals(number n2) {
            check();
            n2.check();
            number x1=this.yuefen();
            number x2=n2.yuefen();
            if(x1.numerator==x2.numerator&&x1.denominator==x2.denominator)
                return true;
            else return false;
        }

        public number yuefen() {
            check();
            number ans=new number(0);
            if(numerator==0||denominator==0)  return ans;
            int divisor;
            divisor = gcd(denominator, numerator);
            ans.denominator = denominator / divisor;
            ans.numerator = numerator / divisor;
            return ans;
        }


        public number add(number n2){
            number re=new number();
            int divisor;

            re.denominator = denominator * n2.denominator;
            re.numerator = numerator * n2.denominator + n2.numerator * denominator;
            divisor = gcd(re.denominator, re.numerator);
            re.denominator = re.denominator / divisor;
            re.numerator = re.numerator / divisor;
            re.check();
            return re;
        }

        private int gcd(int n1,int n2)
        {
            return n2==0?n1:gcd(n2, n1%n2);
        }

        public double ParseDouble(){
            return numerator*1.0/denominator;
        }

        public number sub(number n2){
            number re=new number();
            int divisor;

            re.denominator = denominator * n2.denominator;
            re.numerator = numerator * n2.denominator - n2.numerator * denominator;
            if (re.numerator != 0){
                divisor = gcd(re.denominator, re.numerator);
                re.denominator = re.denominator / divisor;
                re.numerator = re.numerator / divisor;
            }
            re.check();
            return re;

        }

        public number mul(number n2){
            number re=new number();
            int divisor;

            re.denominator = denominator * n2.denominator;
            re.numerator = numerator * n2.numerator;
            if (re.numerator != 0){
                divisor = gcd(re.denominator, re.numerator);
                re.denominator = re.denominator / divisor;
                re.numerator = re.numerator / divisor;
            }
            re.check();
            return re;

        }

        public number div(number n2) {
            number re=new number();
            int divisor;//最大公约数

            if(numerator==0)  return new number(0);
            re.numerator=numerator*n2.denominator;
            re.denominator=denominator*n2.numerator;
            if (re.numerator != 0){
                divisor = gcd(re.denominator, re.numerator);
                re.denominator = re.denominator / divisor;
                re.numerator = re.numerator / divisor;
            }
            re.check();
            return re;
        }


    }
    public static void main(String[] args) {
        int x;
        x=(int)((Math.random()*20)+1);
        System.out.println();
        System.out.println("Please finish the following "+ x +" questions!");
        System.out.println();
        Scanner input=new Scanner(System.in);

        String file="out.txt";

        int right=0;
        PrintStream out = null;


        if(args.length>=1)
            file=args[0];

        try {
            out=new PrintStream(new FileOutputStream(file,true));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            input.close();
            return ;
        }

        out.println();
        out.println(new Date());
        out.println();

        for (int i = 0; i < x; i++) {

            GetQuestion();
            System.out.print(i+1);
            System.out.print(". " + str +"\nYour answer:      ");
            String answer="";
            try{
                answer=input.nextLine();
            }
            catch(Exception e){
            }
            System.out.print("True answer:  "+sum.toString()+"    ");
            number re=sum.add(new number(10));
            try{
                re=new number(answer);
                if(re.equals(sum))
                {
                    System.out.println("You are right!");
                    right=right+1;
                }else {
                    System.out.println("You are wrong!");
                }
            }catch(Exception e){
                if(answer.indexOf('.')!=-1)
                {
                    try{
                        double ant=Double.parseDouble(answer);
                        if(Math.abs(ant-sum.ParseDouble())<1e-5)
                        {
                            System.out.println("You are right!");
                            right=right+1;
                        }else {
                            System.out.println("You are wrong!");
                        }
                    }catch(Exception ex){
                        System.out.println("You are wrong!");
                    }
                }else{
                    System.out.println("You are wrong!");
                }
            }
            out.print(i+1);
            out.println(". " + str );
            out.println("Your answer:     "+answer);
            out.println("True answer:     "+sum.toString());
        }
        System.out.println(right+" / "+x+", So your accuracy is "+right*0.01/x);
        out.println("right+"+x+"So your accuracy is "+right*0.01/x);
        out.println();
        out.println();
        input.close();
        out.close();
        System.out.println();
    }

    private static void GetQuestion() {

        str = "";
        sum.set(0);;
        num_r = (int) (Math.random()*3)+3;
        quesGrow();
    }

    private static void quesGrow() {
        if( num_r > 1 ) {
            int j = num_r;
            num_r--;
            quesGrow();

            int ck=(int)(Math.random()*4);
            number w;
            if(ck!=0)
                w=new number(1+(int)(Math.random()*numberRange));
            else w=new number(1+(int)(Math.random()*numberRange),1+(int)(Math.random()*numberRange));
            int t=(int)(Math.random()*2);
            int f=(int)(Math.random()*4);

            if(t == 0)
            {
                if( f == 0 ) {
                    sum = sum.add(w);
                    str = str + "+" + w.toString();
                }
                if( f == 1 ) {
                    sum = sum.sub(w);
                    str = str + "-" +w.toString();
                }
                if( f == 2 ) {
                    if( j < 3 ) {
                        sum = sum.mul(w);
                        str = str + "*" + w.toString();
                    }
                    else {
                        sum = sum.mul(w);
                        str = "(" +str+ ")" + "*" + w.toString();
                    }
                }
                if ( f == 3 ) {

                    if( j < 3 ) {
                        sum = sum.div(w);
                        str = str + " / " + w.toString();
                    }
                    else {
                        sum = sum.div(w);
                        str = "(" +str+ ")" + " / " + w.toString();
                    }
                }
            }
            else
            {
                if( f == 0 ) {
                    sum = sum.add(w);
                    str = w.toString() + "+" + str;
                }
                if( f == 1 ) {
                    if( j < 3 ) {
                        sum = w.sub(sum);
                        str = w.toString() + "-" + str;
                    }
                    else {
                        sum = w.sub(sum);
                        str = w.toString() + "-" + "(" +str+ ")";
                    }
                }
                if( f == 2 ) {
                    if( j < 3 ) {
                        sum = sum.mul(w);
                        str = w.toString()+ "*" + str;
                    }
                    else {
                        sum = sum.mul(w);
                        str = w.toString() + "*" + "(" +str+ ")";
                    }
                }
                if( f == 3) {
                    if( j < 3 ) {
                        sum = w.div(sum);
                        str = w.toString() + " / " + str;
                    }
                    else {
                        sum = w.div(sum);
                        str = w.toString()+ " / " + "(" +str+ ")";
                    }
                }
            }
        }
        else if( num_r == 1 ) {
            int ck=(int)(Math.random()*4);
            number w;
            if(ck!=0)
                w=new number(1+(int)(Math.random()*numberRange));
            else w=new number(1+(int)(Math.random()*numberRange),1+(int)(Math.random()*numberRange));
            sum = sum.add(w);
            str = str + w.toString();
        }
    }
}

