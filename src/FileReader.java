import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class FileReader {

    public void read(String filename, MemoryManager manager) {
        try {
            ArrayList<Process> allCommands = new ArrayList<Process>(4);
            ;
            File myObj = new File("/Users/hojinryu/Desktop/PUCRS/sisop/T2/MemoryManager/src/test3.txt");
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] parts = data.split("\\(");
                String[] values = parts[1].split(",");

                if (parts[0].equals("IN")) {
                    String value = values[1].replaceAll("\\)", "").trim();
                    Process p = new Process(values[0], Integer.parseInt(value), "IN");
                    manager.addProcess(p);
                    allCommands.add(p);
                } else if (parts[0].equals("OUT")) {
                    String value = values[0].replaceAll("\\)", "").trim();
                    for (int i = 0; i < allCommands.size(); i++) {
                        Process p = allCommands.get(i);
                        if (p.getName().equals(value)) {
                            Process aux = new Process(p.getName(), p.getSize(), "OUT");
                            manager.addProcess(aux);
                            allCommands.add(aux);
                            break;
                        }
                    }
                } else {
                    System.out.println("error");
                }
                System.out.println(data);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}