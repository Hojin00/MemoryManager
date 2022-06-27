import java.util.ArrayList;

public class MemoryManager {
    private Memory memory;
    private ArrayList<Process> commands;
    private int blocksize;
    private boolean hasFixedSize = true;
    private String worstBest = "";

    MemoryManager(int size) {
        this.commands = new ArrayList<Process>((int) (size));
        this.blocksize = size;
        initFixedSizeMemory();
    }

    public ArrayList<Process> getCommandsInfo() {
        return commands;
    }

    public Process getCommandsInfoById(int id) {
        return commands.get(id);
    }

    public void addProcess(Process p) {
        commands.add(p);
    }

    void sethasFixedSize(boolean hasFixedSize) {
        this.hasFixedSize = hasFixedSize;
    }

    void setWorstBest(String s) {
        this.worstBest = s;
    }

    void initFixedSizeMemory() {
        this.memory = new Memory(blocksize);
        int eachBlockSize = (int) Math.pow(2, blocksize) / blocksize;
        for (int i = 0; i < blocksize; i++) {
            Block b = new Block(eachBlockSize, "None", "Free");
            memory.addBlocks(b);
        }
    }

    public void manage() {
        if (hasFixedSize) { // contiguous allocation
            System.out.println("Tamanho inicial -> " + " | 16 |");
            contiguousAllocation();
        }
        // else {
        // if (worstBest.equals("WorstFit")) {
        // worstFitAllocation();
        // } else {
        // bestFitAllocation();
        // }
        // }
    }

    public void contiguousAllocation() {
        for (int i = 0; i < commands.size(); i++) {
            Process c = commands.get(i);
            if (c.getState().equals("IN")) {
                System.out.print("IN(" + c.getName() + "," + c.getSize() + ") -> ");
                for (int j = 0; j < memory.partitions.size(); j++) {
                    Block b = memory.partitions.get(j);
                    if (b.getState().equals("Occupied")) {
                        continue;
                    } else {
                        if (b.getAvailableSize() >= c.getSize()) {
                            b.setAvailableSize(b.getAvailableSize() - c.getSize());
                            b.setName(c.getName());
                            b.setState();
                            break;
                        }
                    }
                }
                printFragmentations();
            } else {
                System.out.print("OUT(" + c.getName() + ") -> ");
                for (int j = 0; j < memory.partitions.size(); j++) {
                    Block b = memory.partitions.get(j);
                    if (b.getState().equals("Free")) {
                        continue;
                    } else {
                        if (!b.getName().equals(c.getName())) {
                            continue;
                        } else {
                            b.setAvailableSize(b.getAvailableSize() + c.getSize());
                            b.setName("None");
                            b.setState();
                            break;
                        }
                    }
                }
                printFragmentations();
            }
        }
    }

    public void removeProcess(String processName) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(processName)) {
                Process p = commands.get(i);
                commands.remove(p);
                break;
            }
        }
    }

    public void setProcesses(String processName, String state) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(processName)) {
                Process p = commands.get(i);
                if (p.getState().equals("IN")) {
                    p.setState();
                } else {
                    p.setState();
                }
            }
        }
    }

    public void printFragmentations() {
        int acc = 0;
        for (int i = 0; i < memory.partitions.size(); i++) {
            Block b = memory.partitions.get(i);

            if (i == memory.partitions.size() - 1) {
                acc += b.getAvailableSize();
                System.out.println("| " + acc + " | ");
            } else {
                Block b2 = memory.partitions.get(i + 1);

                if (b2.getState().equals("Occupied")) {
                    acc += b.getAvailableSize();
                    if (acc != 0) {
                        System.out.print("| " + acc + " | ");
                    }
                    acc = 0;
                    continue;
                } else {
                    acc += b.getAvailableSize();
                }
            }

        }
    }
}
