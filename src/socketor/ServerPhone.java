package socketor;

public class ServerPhone implements Runnable {
    private final String operation;
    Phone phone;

    public ServerPhone(Phone phone, String operation) {
        this.phone = phone;
        this.operation = operation;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Waiting for client...");
            phone.accept();
            String a = phone.readLine();
            String b = phone.readLine();
            double result = calculate(operation, a, b);
            String message = a + " " + operation + " " + b + " = " + result;
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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