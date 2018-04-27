import java.util.*;

public class Customer {
    ArrayList<Integer> Rooms = new ArrayList<>();
    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Phone = new ArrayList<>();

    public Customer(ArrayList<Integer> Rooms, ArrayList<String> Name, ArrayList<String> Phone) {
        this.Rooms = Rooms;
        this.Name = Name;
        this.Phone = Phone;
    }

    public String deleteCustomer(int RoomNo) {
        int index = Rooms.indexOf(RoomNo);
        if(index > -1) {
            this.Name.remove(index);
            this.Phone.remove(index);
            return "\nCustomer Deleted!";
        } else
            return "\nCustomer Not Deleted!";
    }
}
