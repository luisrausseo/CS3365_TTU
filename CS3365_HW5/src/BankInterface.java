import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BankInterface extends Frame implements ActionListener, Runnable {
        private TextField RefNo_lbl;
        private Button auth_pay;
        private Button decline_pay;
        private Label CardNo_lbl;
        private Label Total_lbl;
        Connector connect;

        public BankInterface(Connector connect) {
            this.connect = connect;
            new Thread(this, "Bank Interface").start();
        }

        public void run() {
            ArrayList<Object> message = (ArrayList<Object>) connect.receive();
            setLayout(new FlowLayout());

            auth_pay = new Button("Authorize");
            add(auth_pay);
            auth_pay.addActionListener(this);

            decline_pay = new Button("Decline");
            add(decline_pay);
            decline_pay.addActionListener(this);

            RefNo_lbl = new TextField(15);
            add(RefNo_lbl);
            RefNo_lbl.addActionListener(this);

            CardNo_lbl = new Label("Card#: " + message.get(0).toString());
            add(CardNo_lbl);

            Total_lbl = new Label("Total: $" + message.get(1).toString());
            add(Total_lbl);

            setTitle("Bank Interface");
            setSize(350, 100);
            setLocation(500, 380);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            ArrayList<Object> message = new ArrayList<>();
            String btnLabel = evt.getActionCommand();
            setVisible(false);
            if (btnLabel.equals("Authorize")) {
                message.add("authorized");
                message.add(RefNo_lbl.getText());
                connect.reply(message);
            } else {
                message.add("Declined");
                connect.reply(message);
            }
        }
}

