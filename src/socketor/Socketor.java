package socketor;

public class Socketor {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("""
                    Usage:\r
                    java socketor.Socketor server 8000 2 /\r
                    java socketor.Socketor client 127.0.0.1 8000\r
                    """);
            return;
        }
        Socketor socketor = new Socketor();
        if (args[0].equals("server")) {
            socketor.runServer(args[1], args[2], args[3]);
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

    private void runServer(String port, String threadsCount, String operation) {
        int threads = Integer.parseInt(threadsCount);
        Phone phone = new Phone(port);
        ServerPhone[] serverPhones = new ServerPhone[threads];
        for (int i = 0; i < threads; i++) {
            serverPhones[i] = new ServerPhone(new Phone(phone), operation);
            serverPhones[i].run();
        }
        System.out.println("Server is working on a port " + port + ", operation is " + operation);
    }
}