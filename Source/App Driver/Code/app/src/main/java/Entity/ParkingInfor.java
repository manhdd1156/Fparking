package Entity;

public class ParkingInfor {

    int parkingID;
    String address;
    String phongeNumber;
    double latitude;
    double longitude;
    int space;
    int ownerID;
    int status;
    int flag_del;
    double price;
    int empty;

    public ParkingInfor(int parkingID, String address, String phongeNumber, double latitude, double longitude, int space, int status, double price, int empty) {
        this.parkingID = parkingID;
        this.address = address;
        this.phongeNumber = phongeNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.space = space;
        this.status = status;
        this.price = price;
        this.empty = empty;
    }


    public int getParkingID() {
        return parkingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhongeNumber() {
        return phongeNumber;
    }

    public void setPhongeNumber(String phongeNumber) {
        this.phongeNumber = phongeNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFlag_del() {
        return flag_del;
    }

    public void setFlag_del(int flag_del) {
        this.flag_del = flag_del;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getEmpty() {
        return empty;
    }

    public void setEmpty(int empty) {
        this.empty = empty;
    }


}
