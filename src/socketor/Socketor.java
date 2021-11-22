package socketor;

import phone.PhoneService;

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
        PhoneService phoneService = new PhoneService(ip, port);
        phoneService.writeLine(digit1);
        phoneService.writeLine(digit2);
        String answer = phoneService.readLine();
        System.out.println(answer);
        phoneService.close();
    }

    private void runServer(String port, String operation) {
        PhoneService phoneServiceServer = new PhoneService(port);
        System.out.println("Server with " + operation + " on " + port + " port was ran");
        while (true) {
            PhoneService phoneService = new PhoneService(phoneServiceServer);
            System.out.println("Client accepted");
            new Thread(() -> {
                String a = phoneService.readLine();
                String b = phoneService.readLine();
                double result = calculate(operation, a, b);
                String message = a + " " + operation + " " + b + " = " + result;
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                phoneService.writeLine(message);
                System.out.println("Accepted " + message);
                phoneService.close();
            }).start();
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