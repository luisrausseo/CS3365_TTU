import java.util.ArrayList;

public class Bill {

    ArrayList<Integer> Rooms = new ArrayList<>();
    ArrayList<String> Bill_name = new ArrayList<>();
    ArrayList<Integer> Total = new ArrayList<>();
    ArrayList<Integer> ReferenceNo;

    public Bill(ArrayList<Integer> Rooms, ArrayList<String> Bill_name, ArrayList<Integer> Total) {
        this.Rooms = Rooms;
        this.Bill_name = Bill_name;
        this.Total = Total;
        this.ReferenceNo = new ArrayList<>();
        for (int i=0; i < Rooms.size(); i++) {
            this.ReferenceNo.add(0);
        }
    }

    public String readBill(int RoomNo) {
        if(this.Rooms.indexOf(RoomNo) > -1)
            return this.Bill_name.get(Rooms.indexOf(RoomNo));
        else
            return "Room not found!";
    }

    public int readTotal(int RoomNo) {
        int index = Rooms.indexOf(RoomNo);
        if(index > -1)
            return this.Total.get(index);
        else
            return -1;
    }

    public void updateReference(int RoomNo, int refNo) {
        int index = Rooms.indexOf(RoomNo);
        if(index > -1) {
            this.ReferenceNo.add(index, refNo);
        }
    }

}
