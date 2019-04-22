package cn.bysj.yty.qyyg;

public class Test {
    final static int x = 12;
    public static void method(int x){
        x+=x;
        System.out.println(x);
    }
    public static void main(String[] args) {
        method(5);
    }
}
