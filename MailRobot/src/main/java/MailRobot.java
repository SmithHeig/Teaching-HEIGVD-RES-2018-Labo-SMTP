import model.prank.PrankGenerator;

public class MailRobot {

    public static void main(String[] args){
        PrankGenerator prankGenerator = new PrankGenerator();
        prankGenerator.generatePrank();
        prankGenerator.runPrank();
    }
}
