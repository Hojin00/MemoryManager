import java.util.ArrayList;

public class MemoryManager {
    private Memory memory;
    private ArrayList<Process> commands;
    private int totalPosc;
    private int blockNumber;
    private boolean hasFixedSize;
    private String worstBest = "";

    MemoryManager(int size) {

        this.commands = new ArrayList<Process>(size);
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

    void setTotalPosc(int n) {
        this.totalPosc = n;
    }

    void setTotalBlock(int n) {
        this.blockNumber = n;
    }

    void setHasFixedSize(boolean hasFixedSize) {
        this.hasFixedSize = hasFixedSize;
    }

    void setWorstBest(String s) {
        this.worstBest = s;
    }

    void initFixedSizeMemory() {
        if (isPowerOfTwo(totalPosc)) {
            if (hasFixedSize) {

                if (isDivisionZero(totalPosc, blockNumber) && isPowerOfTwo(totalPosc / blockNumber)) {
                    int eachBlock = totalPosc / blockNumber;
                    this.memory = new Memory(blockNumber);
                    for (int i = 0; i < blockNumber; i++) {
                        Block b = new Block(eachBlock, "None", "Free");
                        memory.addBlocks(b);
                    }
                }
            } else {
                this.memory = new Memory(1);
                Block b = new Block(totalPosc, "None", "Free");
                memory.addBlocks(b);
            }
        }
    }

    boolean isPowerOfTwo(int x) {

        return x != 0 && ((x & (x - 1)) == 0);

    }

    boolean isDivisionZero(int a, int b) {
        return a % b == 0;
    }

    public void manage() {
        System.out.println("Tamanho inicial -> " + " | " + totalPosc + " |");
        if (hasFixedSize) { // contiguous allocation
            contiguousAllocation();
        } else {
            if (worstBest.equals("WorstFit")) {
                worstFitAllocation();
            } else {
                bestFitAllocation();
            }
        }
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
                        } else {
                            System.out.print("Nāo cabe na memória principal ");
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

    public void worstFitAllocation() {
        for (int i = 0; i < commands.size(); i++) {
            Process c = commands.get(i);

            if (c.getState().equals("IN")) {

                System.out.print("IN(" + c.getName() + "," + c.getSize() + ") -> ");

                int best = 0;
                int bestIndex = 0;
                boolean found = false;
                for (int j = 0; j < memory.partitions.size(); j++) {
                    Block b = memory.partitions.get(j);

                    if (b.getState().equals("Occupied")) {
                        continue;
                    } else {
                        if (b.getAvailableSize() >= c.getSize() && b.getAvailableSize() > best) {
                            best = b.getAvailableSize();
                            bestIndex = j;
                            found = true;
                        } else {
                            if (j == memory.partitions.size() - 1 && !found) {

                                System.out.print("Nāo cabe na memória principal ");
                            }
                            continue;
                        }
                    }
                }

                Block b = memory.partitions.get(bestIndex);
                Block newBlock = new Block(b.getAvailableSize() - c.getSize(), "None", "Free");
                memory.addBlocks(newBlock);

                b.setAvailableSize(0);
                b.setName(c.getName());
                b.setState();

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
                            b.setAvailableSize(c.getSize());
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

    public void bestFitAllocation() {
        for (int i = 0; i < commands.size(); i++) {
            Process c = commands.get(i);

            if (c.getState().equals("IN")) {

                System.out.print("IN(" + c.getName() + "," + c.getSize() + ") -> ");

                int best = totalPosc;
                int bestIndex = 0;
                boolean found = false;
                for (int j = 0; j < memory.partitions.size(); j++) {
                    Block b = memory.partitions.get(j);

                    if (b.getState().equals("Occupied")) {
                        continue;
                    } else {
                        if (b.getAvailableSize() >= c.getSize() && b.getAvailableSize() <= best) {
                            best = b.getAvailableSize() - c.getSize();
                            bestIndex = j;
                            found = true;
                        } else {
                            if (j == memory.partitions.size() - 1 && !found) {

                                System.out.print("Nāo cabe na memória principal ");
                            }
                            continue;
                        }
                    }
                }

                Block b = memory.partitions.get(bestIndex);
                Block newBlock = new Block(b.getAvailableSize() - c.getSize(), "None", "Free");
                memory.addBlocks(newBlock);

                b.setAvailableSize(0);
                b.setName(c.getName());
                b.setState();

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
                            b.setAvailableSize(c.getSize());
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
