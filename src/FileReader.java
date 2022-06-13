import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
public class FileReader {
  
    public void read(String filename, MemoryManager manager) {
        try {
            File myObj = new File("test.txt");
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] parts = data.split("(");
                String[] values = parts[1].split(",");
                values[1].replaceAll(".$", "");
                if(parts[0].equals("IN")) {
                    System.out.println("in");
                    Process p = new Process(values[0], Integer.parseInt(values[1]));
                    manager.addProcess(p);
                } else if (parts[0].equals("OUT")){
                    System.out.println("out");
                    manager.removeProcess(values[0]);
                } else {
                    System.out.println("error");
                }
                System.out.println(data);
                System.out.println(manager.toString());
      }
      scanner.close();
    }   catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

  
}