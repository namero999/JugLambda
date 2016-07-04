package lol.corrado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;

public class Binaries {

    public void run() {

        try {

            Process p = Runtime.getRuntime().exec("/var/task/HelloWorld");
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