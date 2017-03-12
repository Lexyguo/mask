
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
    //建立算式中的数字类
    public static class number {
        //真分数的分子，分母分别为numerator和denominator，整数的分母恒为1
        private int numerator,denominator;
        private int temp=0;
        public number() {
            numerator=0;
            denominator=1;
        }
        //对数字的处理
        //判断是整数还是真分数
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
            if(a>b)
            {
                temp=a;
                a=b;
                b=temp;
            }
            if (a==b)
            {
                b+=1;
            }
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
                    if(numerator>denominator)
                    {
                        temp=numerator;
                        numerator=denominator;
                        denominator=temp;
                    }
                    if (numerator==denominator)
                    {
                        denominator+=1;
                    }
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
        //约分
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
        System.out.println("Please finish the following "+ x +" questions!If you want stop,you can input \'stop\' to achieve.");
        System.out.println();
        Scanner input=new Scanner(System.in);
        //将运行过程中的数据存入文件夹out中
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
        int i = 0;
        for (i = 0; i < x; i++) {
            boolean t=false;
            GetQuestion();
            System.out.print(i + 1);
            System.out.print(". " + str + "\nYour answer: ");
            String answer = "";
            do {
                try {
                    answer = input.nextLine();//清空键盘输入
                } catch (Exception e) {
                }
                if (answer.equals("stop"))
                {
                    System.out.println(right+" / "+(i+1)+", So your accuracy is "+right*0.01/(i+1));
                    out.println("right+"+(i+1)+"So your accuracy is "+right*0.01/(i+1));
                    out.println();
                    System.exit(0);
                }

                number re = sum.add(new number(10));

                try {
                    re = new number(answer);
                    if (re.equals(sum)) {
                        System.out.println("You are right!");
                        right = right + 1;
                        t=false;
                    }
                    else {
                        System.out.println("You are wrong!");
                        System.out.println("True answer is "+sum.toString());
                        t=false;
                    }
                } catch (Exception e) {
                    if (answer.indexOf('.') != -1) {
                        try {
                            double ant = Double.parseDouble(answer);
                            if (Math.abs(ant - sum.ParseDouble()) < 1e-5) {
                                System.out.println("You are right!");
                                right = right + 1;
                                t=false;
                            }
                            else if(answer.indexOf('.') == 0) {
                                System.out.println("Your input is wrong!");
                                System.out.print("Please input your answer again:");
                                t = true;
                            }
                            else {
                                System.out.println("You are wrong!");
                                System.out.println("True answer is "+sum.toString());
                                t=false;
                            }
                        } catch (Exception ex) {
                            System.out.println("Your input is wrong!");
                            System.out.print("Please input your answer again:");
                            t=true;
                        }
                    }

                    else {
                        System.out.println("Your input is wrong!");
                        System.out.print("Please input your answer again:");
                        t=true;
                    }
                }
            }while(t);
            out.print(i+1);
            out.println(". " + str );
            out.println("Your answer: "+answer);
            out.println("True answer: "+sum.toString());
        }
        System.out.println(right+" / "+(i)+", So your accuracy is "+right*1.0/(i));
        out.println("right+"+(i)+"So your accuracy is "+right*1.0/(i));
        out.println();
        input.close();
        out.close();
        System.out.println();
    }

    private static void GetQuestion() {

        str = "";
        sum.set(0);;
        num_r = (int) (Math.random()*2)+2;
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
            {
                w=new number(1+(int)(Math.random()*numberRange));
            }
            else
            {
                w = new number(1 + (int) (Math.random() * numberRange),1 + (int) (Math.random() * numberRange));
            }
            int t=(int)(Math.random()*2);
            int f=(int)(Math.random()*4);

            if(t == 0)
            {
                if( f == 0 ) {
                    sum = sum.add(w);
                    str = str + " + " + w.toString();
                }
                if( f == 1 ) {
                    sum = sum.sub(w);
                    str = str + " - " +w.toString();
                }
                if( f == 2 ) {
                    if( j < 3 ) {
                        sum = sum.mul(w);
                        str = str + " ✖ " + w.toString();
                    }
                    else {
                        sum = sum.mul(w);
                        str = "(" +str+ ")" + " ✖ " + w.toString();
                    }
                }
                if ( f == 3 ) {

                    if( j < 3 ) {
                        sum = sum.div(w);
                        str = str + "÷" + w.toString();
                    }
                    else {
                        sum = sum.div(w);
                        str = "(" +str+ ")" + " ÷ " + w.toString();
                    }
                }
            }
            else
            {
                if( f == 0 ) {
                    sum = sum.add(w);
                    str = w.toString() + " + " + str;
                }
                if( f == 1 ) {
                    if( j < 3 ) {
                        sum = w.sub(sum);
                        str = w.toString() + " - " + str;
                    }
                    else {
                        sum = w.sub(sum);
                        str = w.toString() + " - " + "(" +str+ ")";
                    }
                }
                if( f == 2 ) {
                    if( j < 3 ) {
                        sum = sum.mul(w);
                        str = w.toString()+ " ✖ " + str;
                    }
                    else {
                        sum = sum.mul(w);
                        str = w.toString() + " ✖ " + "(" +str+ ")";
                    }
                }
                if( f == 3) {
                    if( j < 3 ) {
                        sum = w.div(sum);
                        str = w.toString() + " ÷ " + str;
                    }
                    else {
                        sum = w.div(sum);
                        str = w.toString()+ " ÷ " + "(" +str+ ")";
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

