import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReceiptPrinterInterface extends Frame implements ActionListener, Runnable {
    private TextArea taDisplay;
    private Button print_btn;
    Connector connect;

    public ReceiptPrinterInterface(Connector connect) {
        this.connect = connect;
        new Thread(this, "Receipt Printer Interface").start();
    }

    public void run() {
        ArrayList<Object> message = (ArrayList<Object>) connect.receive();

        setLayout(new FlowLayout());

        print_btn = new Button("Print");
        add(print_btn);
        print_btn.addActionListener(this);

        taDisplay = new TextArea(5, 35); // 5 rows, 40 columns
        taDisplay.setText("Receipt\nCard#: " + message.get(0) + "\nTotal: $" + message.get(1) + "\nReference#: "  + message.get(2));
        add(taDisplay);

        setTitle("Receipt Printer Interface");
        setSize(380, 180);
        setLocation(880, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        connect.reply("\nReceipt Printed!");
        setVisible(false);

    }
}