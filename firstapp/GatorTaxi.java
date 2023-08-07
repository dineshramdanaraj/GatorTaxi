import java.io.*;
import java.util.*;


public class GatorTaxi{
      // create the data structures to store ride
        private MinHeap minHeap;
        private redBlackTree rbt;
        
        public GatorTaxi(){
            //initialize the data structures
            this.minHeap = new MinHeap();
            this.rbt = new redBlackTree();
        }

    public static void main(String[] args) throws IOException {
            //get input file as an argument, as mentioned in the project doc
        String inputFile = args[0];
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        // output the results to an output file.
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/Dinesh Ram/.virtualenvs/backend-3rMdtp_Y/myapp/static/output_file.txt"));
        String line;
        GatorTaxi gatorTaxi = new GatorTaxi();//create instance of the class
        while ((line = br.readLine()) != null) {
    //use regular expression to extract the command and arguments for the commands from on the given input format
    //ex : command(args)
            int st = line.indexOf("(");
            int en = line.indexOf(")");
            String command = line.substring(0, st);
            String[] argsStr = line.substring(st+1, en).split(",");
//use a switch code to determine the different operations from the input \
// ( i.e, insert, print, cancelride, getnextride, updatetripduration)
            switch(command) {
                case "Insert":
                    int rideNumber = Integer.parseInt(argsStr[0]);
                    int rideCost = Integer.parseInt(argsStr[1]);
                    int tripDuration = Integer.parseInt(argsStr[2]);
                    gatorTaxi.insert(rideNumber, rideCost, tripDuration, bw, br);
                    break;
                case "Print":
                    if (argsStr.length == 1) {
                        int printRideNumber = Integer.parseInt(argsStr[0]);
                        gatorTaxi.print(printRideNumber, bw);
                    } else {
                        int rideNumber1 = Integer.parseInt(argsStr[0]);
                        int rideNumber2 = Integer.parseInt(argsStr[1]);
                        gatorTaxi.print(rideNumber1, rideNumber2, bw);
                    }
                    break;
                case "UpdateTrip":
                    int updateRideNumber = Integer.parseInt(argsStr[0]);
                    int newTripDuration = Integer.parseInt(argsStr[1]);
                    gatorTaxi.updateTrip(updateRideNumber, newTripDuration, bw, br);
                    break;
                case "CancelRide":
                    int cancelRideNumber = Integer.parseInt(argsStr[0]);
                    gatorTaxi.cancelRide(cancelRideNumber);
                    break;
                case "GetNextRide":
                    Ride nextRide = gatorTaxi.getNextRide();
                    if (nextRide != null) {
                        bw.write("(" + nextRide.rideNumber + "," + nextRide.rideCost + "," + nextRide.tripDuration + ")\n");
                    } else {
                        bw.write("No active ride requests\n");
                    }
                    break;
                default:
                    break;
            }
        }
        br.close();
        bw.close();
    }
    //print where only 1 argument is given. if it exists in the data structure print it. if not result out (0,0,0)
        public void print(int rideNumber, BufferedWriter bw) throws IOException {
            Node node = rbt.findNode(rideNumber);//search from the red black tree to find it's existance
            if (node != null){
                Ride ride = node.ride;
                bw.write("(" + ride.rideNumber + "," + ride.rideCost + "," + ride.tripDuration + ")\n");
            } else {
                bw.write("(0,0,0)\n");
            }
        }
        //2 arguments print. print the range of ride numbers based on the given 2 ride numbers

    public void print(int rn1, int rn2, BufferedWriter bw) throws IOException {
        if (rbt.root == null) {//if no ride is active print (0,0,0)
            bw.write("(0,0,0)\n");
            return;
        }

        Node node = rbt.root;//traverse from root of the rbt
        boolean start = true;

        while (node != null) {// to find the 1st ride that exists in the range
            if (node.ride.rideNumber >= rn1 && node.ride.rideNumber <= rn2) {
                rbt.printRange(node, rn1, rn2, bw, start);
                break;
            } else if (node.ride.rideNumber < rn1) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        if (start == true){// if no ride exists in that range print (0,0,0)
            bw.write("(0,0,0)");
        }


        bw.write("\n");
    }
    public void insert(int rideNumber, int rideCost, int tripDuration, BufferedWriter bw, BufferedReader br) throws IOException {
        Node node = rbt.findNode(rideNumber);//find if the ride already exists or nor
        if (node == null) {//if not then insert into the 2 data structures used
            Ride ride = new Ride(rideNumber, rideCost, tripDuration);
            minHeap.insert(ride);
            rbt.insert(ride);
        }
        else{
            bw.write("Duplicate RideNumber\n");// if yes then print duplicate ridenumber
            bw.close();//exit from writing
            br.close();//exit from reading
            System.err.println("error; ride number " + rideNumber+  " exists");//print error in the console
            System.exit(-1);//exit from the code as mentioned in the project doc.
        }
    }
    public Ride getNextRide() {// it gets the ride with the least ride cost
        Ride ride = minHeap.extractMin();//for this extract the minimum value stored in the minheap
        if(ride != null) {
            rbt.delete(ride.rideNumber);//then delete the ride from the rbt
        }
        return ride;
    }
    
    public void cancelRide(int rideNumber) {// to cancel a ride based on the given ride number
        Node node = rbt.findNode(rideNumber);// if found from the tree
        if (node != null) {//delete it from the 2 data structures
            Ride ride = node.ride;
            minHeap.delete(rideNumber);
            rbt.delete(rideNumber);
        }
    }

public void updateTrip(int rideNumber, int newTripDuration, BufferedWriter bw, BufferedReader br) throws IOException {
    Node node = rbt.findNode(rideNumber);// check if it's there in rbt
    if (node != null) {//if yes, then update the trip duration based on the given conditions
        Ride ride = node.ride;
        if (newTripDuration <= ride.tripDuration) {// if less than the old trip duration, update the value
            ride.tripDuration = newTripDuration;

        }
        else if (ride.tripDuration < newTripDuration && newTripDuration <= (2 * ride.tripDuration)) {
            cancelRide(rideNumber);// if lesser than twice of the old trip duration update the cost and change the structure of the ds accordingly
            insert(rideNumber, ride.rideCost + 10, newTripDuration, bw, br);
        }
         else {
            cancelRide(rideNumber);// if greater than twice then cancel the ride
        }
    }
}

}






