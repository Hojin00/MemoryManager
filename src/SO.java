import java.util.Scanner;

public class SO {
  private FileReader reader;
  Scanner in = new Scanner(System.in);

  void execute() {
    System.out.println("Informe um tipo de alocação de espaço: ");
    System.out.println("1 : Partições fixas ");
    System.out.println("2 : Worst-Fit");
    System.out.println("3 : Best-Fit");
    int type = in.nextInt();
    if (type == 1) {
      System.out.println("Informe um número total de posições: ");
      int totalPoscSize = in.nextInt();

      System.out.println("Informe um número total de partições: ");
      int partSize = in.nextInt();

      MemoryManager fixedPartition = new MemoryManager(partSize);
      reader = new FileReader();
      reader.read("test2.txt", fixedPartition);

      fixedPartition.setTotalPosc(totalPoscSize);
      fixedPartition.setTotalBlock(partSize);
      fixedPartition.setHasFixedSize(true);
      fixedPartition.initFixedSizeMemory();

      fixedPartition.manage();
    } else if (type == 2) {
      System.out.println("Informe o tamanho da partiçāo: ");
      int size = in.nextInt();
      MemoryManager worstFit = new MemoryManager(size);
      reader = new FileReader();
      reader.read("test4.txt", worstFit);

      worstFit.setTotalPosc(size);
      worstFit.setWorstBest("WorstFit");
      worstFit.setHasFixedSize(false);
      worstFit.initFixedSizeMemory();
      worstFit.manage();

    } else if (type == 3) {
      System.out.println("Informe o tamanho da partiçāo: ");
      int size = in.nextInt();
      MemoryManager bestFit = new MemoryManager(size);
      reader = new FileReader();
      reader.read("test4.txt", bestFit);

      bestFit.setTotalPosc(size);
      bestFit.setWorstBest("BestFit");
      bestFit.setHasFixedSize(false);
      bestFit.initFixedSizeMemory();
      bestFit.manage();
    } else {
      System.out.println("Opçāo inválida");
    }
  }
}
