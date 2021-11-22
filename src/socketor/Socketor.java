package socketor;

public class Socketor {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("""
                    Usage:\r
                    java socketor.Socketor server 8000 /\r
                    java socketor.Socketor client 127.0.0.1 8000\r
                    """);
            return;
        }
        Socketor socketor = new Socketor();
        if (args[0].equals("server")) {
            socketor.runServer(args[1], args[2]);
        } else if (args[0].equals("client")) {
            socketor.runClient(args[1], args[2], args[3], args[4]);
        }
    }

    private void runClient(String ip, String port, String digit1, String digit2) {
        Phone phone = new Phone(ip, port);
        phone.writeLine(digit1);
        phone.writeLine(digit2);
        String answer = phone.readLine();
        System.out.println(answer);
        phone.close();
    }

    private void runServer(String port, String operation) {
        Phone phone = new Phone(port);
        System.out.println("Server is working on a port " + port + ", operation is " + operation);
        while (true) {
            phone.accept();
            String a = phone.readLine();
            String b = phone.readLine();
            double result = calculate(operation, a, b);
            String message = a + " " + operation + " " + b + " = " + result;
            phone.writeLine(message);
            System.out.println("Accepted " + message);
            phone.close();
        }
    }

    private double calculate(String operation, String x, String y) throws ArithmeticException {
        double results = 0;
        double a = Double.parseDouble(x);
        double b = Double.parseDouble(y);
        switch (operation) {
            case "*" -> results = a * b;
            case "/" -> results = a / b;
            case "-" -> results = a - b;
            case "+" -> results = a + b;
            case "^" -> results = Math.pow(a, b);
            case "%" -> results = a % b;
            default -> System.out.println("This command isn't correct");
        }
        return results;
    }
}