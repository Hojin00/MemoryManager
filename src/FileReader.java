import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 

public class FileReader {
  
    void read(string filename) {
        try {
            File myObj = new File("test.txt");
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] parts = data.split("(");
                String[] values = parts[1].split(",");
                values[1].replaceAll(".$", "");
                if(parts[0].equals("IN")) {
                    Process p = new Process(values[0], values[1]);
                } else {
                    //implementar :)
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