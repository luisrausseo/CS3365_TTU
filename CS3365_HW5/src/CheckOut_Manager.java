import java.util.ArrayList;

public class CheckOut_Manager implements Runnable {
    Connector connect;
    Bill current_bills;
    Customer Customers;
    Room HotelRooms;
    DeskClerkInterface DCI;

    CheckOut_Manager(Connector connect, DeskClerkInterface DCI) {
        //Customer's Information
        ArrayList<Integer> Cust_Rooms = new ArrayList<>();
        Cust_Rooms.add(301);
        Cust_Rooms.add(303);
        ArrayList<String> Name = new ArrayList<>();
        Name.add("John");
        Name.add("Sam");
        ArrayList<String> Phone = new ArrayList<>();
        Phone.add("806-333-1234");
        Phone.add("806-333-4567");
        ArrayList<String> Bill_name = new ArrayList<>();
        Bill_name.add("John's Bill");
        Bill_name.add("Sam's Bill");
        ArrayList<Integer> Total = new ArrayList<>();
        Total.add(100);
        Total.add(200);
        ArrayList<Integer> Rooms = new ArrayList<>();
        Rooms.add(301);
        Rooms.add(302);
        Rooms.add(303);
        ArrayList<String> Status = new ArrayList<>();
        Status.add("Occupied");
        Status.add("Available");
        Status.add("Occupied");
        //Creates Bills, Customers and Rooms currently stored
        this.current_bills = new Bill(Cust_Rooms, Bill_name, Total);
        this.Customers = new Customer(Cust_Rooms, Name, Phone);
        this.HotelRooms = new Room(Rooms, Status);
        //Creates connector
        this.connect = connect;
        this.DCI = DCI;
        new Thread(this, "Check Out Manager").start();
    }

    public void run() {
        boolean end_loop = false;
        while (!end_loop) {
            ArrayList<Object> message = (ArrayList<Object>) connect.receive();
            if (message.get(0).equals("requestBill")) {
                connect.reply(requestBill((int) message.get(1))); //Sends RoomNo to get Bill
            } else if (message.get(0).equals("payByCreditCard")) {
                connect.reply(DCI.taDisplay.getText() + "\nGetting Credit Card Authorized");
                new CardReaderInterface(connect);
                int CardNo = (int) connect.send("getCard");
                payByCreditCard((int) message.get(1), CardNo);
                end_loop = true;
            } else {
                end_loop = true;
            }
        }
    }

    public String requestBill(int RoomNo) {
        return current_bills.readBill(RoomNo);
    }

    public void payByCreditCard(int RoomNo, int CardNo) {
        int Total = current_bills.readTotal(RoomNo);
        new BankInterface(connect);
        ArrayList<Object> message = new ArrayList<>();
        message.add(CardNo);
        message.add(Total);
        message = (ArrayList<Object>) connect.send(message);
        if (message.get(0).equals("authorized")) {
            int RefNo = Integer.parseInt((String)message.get(1));
            message.clear();
            message.add(CardNo);
            message.add(Total);
            message.add(RefNo);
            new ReceiptPrinterInterface(connect);
            DCI.get_result(DCI.taDisplay.getText() + connect.send(message));
            current_bills.updateReference(RoomNo, RefNo);
            DCI.get_result(DCI.taDisplay.getText() + HotelRooms.releaseRoom(RoomNo));
            DCI.get_result(DCI.taDisplay.getText() + Customers.deleteCustomer(RoomNo));
            new BillPrinterInterface(connect);
            DCI.get_result(DCI.taDisplay.getText() + connect.send(current_bills.readBill(RoomNo)));
        } else {
            DCI.get_result(DCI.taDisplay.getText() + "\nPayment Declined!");
        }
    }

    public static void main(String[] args) {
        Connector conn = new Connector();
        DeskClerkInterface DCI = new DeskClerkInterface(conn);
        new CheckOut_Manager(conn, DCI);
    }
}
