public class SecondClass {
    public static void main(String[] args) {
        double var1 = 20.00;
        double var2 = 80.00;
        double result = (var1 + var2) * 100.00;
        double remainder = result % 40;
        boolean isZero = remainder == 0.00;
        System.out.println(isZero);
        if (!isZero)
            System.out.println("Got some remainder");
    }
}
