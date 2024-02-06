package tasks;

public class Tempcode {

    public static void main(String[] args) {
        String req;
        req = "24444";
        testre(req);
    }


    public static void testre(String req) {
        if (req == null) {
            System.out.println(req);
        } else if (req.contains("-")) {
            System.out.println("checkin " + req);
        } else
            System.out.println("name  " + req);
    }


}
