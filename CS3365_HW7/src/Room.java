import java.util.ArrayList;

public class Room {
    ArrayList<Integer> Rooms = new ArrayList<>();
    ArrayList<String> Status = new ArrayList<>();

    public Room(ArrayList<Integer> Rooms, ArrayList<String> Status) {
        this.Rooms = Rooms;
        this.Status = Status;
    }

    public String releaseRoom(int RoomNo) {
        int index = Rooms.indexOf(RoomNo);
        if(index > -1) {
            this.Status.set(index, "Available");
            return "\nRoom " + RoomNo + " Released!";
        } else
            return "\nRoom " + RoomNo + " Not Released!";
    }
}
