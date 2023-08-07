//Comparable is used for implementing the compareTo function.
public class Ride implements Comparable<Ride> {
    public int rideNumber;
    public int rideCost;
    public int tripDuration;

    public Ride(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    //the below function is overriding the comparable superclass
    //Used to compare the rides based on their cost and if the costs are same, then it compares the trip duration
    public int compareTo(Ride other) {
        if (rideCost == other.rideCost) {
            return Integer.compare(tripDuration, other.tripDuration);
        }
        return Integer.compare(rideCost, other.rideCost);
    }

}
