import java.util.ArrayList;

public class Memory {
  public ArrayList<Block> partitions;

  Memory(int size) {

    this.partitions = new ArrayList<Block>((int) (size));
  }

  public void addBlocks(Block b) {
    partitions.add(b);
  }

}
