import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillPrinterInterface extends Frame implements ActionListener, Runnable {
    private TextArea taDisplay;
    private Button print_btn;
    Connector connect;

    public BillPrinterInterface(Connector connect) {
        this.connect = connect;
        new Thread(this, "Bill Printer Interface").start();
    }

    public void run() {
        String message = (String) connect.receive();

        setLayout(new FlowLayout());

        print_btn = new Button("Print");
        add(print_btn);
        print_btn.addActionListener(this);

        taDisplay = new TextArea(5, 35); // 5 rows, 40 columns
        taDisplay.setText("Bill\n" + message + "(Updated)");
        add(taDisplay);

        setTitle("Bill Printer Interface");
        setSize(380, 180);
        setLocation(880, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        connect.reply("\nBill Printed!");
        setVisible(false);

    }
}