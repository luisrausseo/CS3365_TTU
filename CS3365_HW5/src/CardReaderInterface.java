import java.awt.*;
import java.awt.event.*;

public class CardReaderInterface extends Frame implements ActionListener, Runnable{
    Connector connect;
    private Label card_lbl;
    private TextField tfInput;

    CardReaderInterface(Connector connect) {
        this.connect = connect;
        new Thread(this, "Card Reader Interface").start();
    }

    public void run() {
        setLayout(new FlowLayout());

        card_lbl = new Label("Card #: ");
        add(card_lbl);

        tfInput = new TextField(15);
        add(tfInput);
        tfInput.addActionListener(this);

        setTitle("Desk Clerk Interface");
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
