public class Process {
    private String name;
    private int size;
    private String state;

    Process(String name, int size, String state) {
        this.name = name;
        this.size = size;
        this.state = state;
    }

    String getName() {
        return name;
    }

    int getSize() {
        return size;
    }

    String getState() {
        return state;
    }

    void setState() {
        if (state.equals("IN")) {
            state = "IN";
        } else {
            state = "OUT";
        }
    }

}