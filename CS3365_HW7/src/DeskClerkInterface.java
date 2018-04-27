import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeskClerkInterface extends Frame implements ActionListener, Runnable, WindowListener {
    private Label room_lbl;
    private TextField tfInput;
    public TextArea taDisplay;
    private Button payCC;
    Connector connect;

    public DeskClerkInterface(Connector connect) {
        this.connect = connect;
        new Thread(this, "Desk Clerk Interface").start();
    }

    public void run() {
        setLayout(new FlowLayout());

        addWindowListener(this);

        room_lbl = new Label("Room #: ");
        add(room_lbl);

        tfInput = new TextField(15);
        add(tfInput);
        tfInput.addActionListener(this);

        payCC = new Button("Pay");
        add(payCC);
        payCC.addActionListener(this);

        taDisplay = new TextArea(5, 40); // 5 rows, 40 columns
        add(taDisplay);

        setTitle("Desk Clerk Interface");
        setSize(380, 180);
        setLocation(500, 200);
        setVisible(true);
    }

    // ActionEvent handler - Called back upon hitting "enter" key on TextField
    @Override
    public void actionPerformed(ActionEvent evt) {
        ArrayList<Object> message = new ArrayList<>();
        int RoomNo = Integer.parseInt(tfInput.getText());
        String btnLabel = evt.getActionCommand();
        if (btnLabel.equals("Pay")) {
            message.add("payByCash");
            message.add(RoomNo);
            taDisplay.setText(connect.send(message).toString());
        } else {
            message.add("requestBill");
            message.add(RoomNo);
            taDisplay.setText(connect.send(message).toString());
        }
    }

    public void get_result(Object message) {
        taDisplay.setText(message.toString());
    }

    public void windowClosing(WindowEvent e) {
        System.exit(1);
    }
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent arg0) {}
}