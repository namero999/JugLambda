package lol.corrado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ExploreEnvironment {

    public void run() {

        System.out.println("--- CPUs ---");
        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println("--- Classpath ---");
        System.out.println(System.getProperty("java.class.path"));

        List<String> commands = Arrays.asList(
                "cat /proc/cpuinfo",
                "pwd",
                "ls -la",
                "which java",
                "env",
                "ps aux",
                "whoami",
                "df -h",
                "perl -v"
        );

        commands.forEach(this::executeCommand);

    }

    private void executeCommand(String command) {

        System.out.println("--- " + command + " ---");

        try {

            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                System.out.println(buffer.lines().collect(joining("\n")));
            }

        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }

    }

}