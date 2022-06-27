public class Block {
  private int availableSize;
  private String name;
  private String state;

  Block(int availableSize, String name, String state) {
    this.availableSize = availableSize;
    this.name = name;
    this.state = state;
  }

  int getAvailableSize() {
    return availableSize;
  }

  String getState() {
    return state;
  }

  String getName() {
    return name;
  }

  void setAvailableSize(int n) {
    this.availableSize = n;
  }

  void setName(String s) {
    this.name = s;
  }

  void setState() {
    if (state.equals("Occupied")) {
      state = "Free";
    } else {
      state = "Occupied";
    }
  }

}
