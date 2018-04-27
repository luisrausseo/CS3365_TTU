import java.awt.*;
import java.awt.event.*;

public class CashPayInterface extends Frame implements ActionListener, Runnable{
    Connector connect;
    private Label card_lbl;
    private TextField tfInput;

    CashPayInterface(Connector connect) {
        this.connect = connect;
        new Thread(this, "Card Reader Interface").start();
    }

    public void run() {
        setLayout(new FlowLayout());

        card_lbl = new Label("Cash Amount: ");
        add(card_lbl);

        tfInput = new TextField(15);
        add(tfInput);
        tfInput.addActionListener(this);

        setTitle("Cash Payment");
        setSize(350, 100);
        setLocation(500, 380);
        setVisible(true);
    }

    // ActionEvent handler - Called back upon hitting "enter" key on TextField
    @Override
    public void actionPerformed(ActionEvent evt) {
        setVisible(false);
        connect.reply(Integer.parseInt(tfInput.getText()));
    }
}
