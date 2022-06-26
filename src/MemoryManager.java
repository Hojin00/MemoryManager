import java.util.ArrayList;

public class MemoryManager {

    private ArrayList<Process> memory;
    private int blocksize;
    private boolean hasFixedSize = true;

    public MemoryManager(int size) {
        size = (int) Math.pow(2, size);
        this.memory = new ArrayList<Process>((int) (size));
    }

    public ArrayList<Process> getMemoryInfo() {
        return memory;
    }

    public Process getMemoryInfoById(int id) {
        return memory.get(id);
    }

    public void addProcess(Process p) {
        memory.add(p);
    }

    public void removeProcess(String processName) {
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).getName().equals(processName)) {
                Process p = memory.get(i);
                memory.remove(p);
                break;
            }
        }
    }
}
