import javax.swing.JFrame;

public class Runner {
  public static void main(String[] args) {
    JFrame frame = new JFrame("Q3 Project");
    Table table = new Table();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(table);
    frame.pack();
    frame.setVisible(true);

    table.animate();
  }
}
