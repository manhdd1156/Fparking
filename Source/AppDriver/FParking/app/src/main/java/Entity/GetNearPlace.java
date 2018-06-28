package Entity;

public class GetNearPlace {

    double lattitude;
    double longitude;
    String addressParking;

    public GetNearPlace(double lattitude, double longitude, String addressParking) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.addressParking = addressParking;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddressParking() {
        return addressParking;
    }

    public void setAddressParking(String addressParking) {
        this.addressParking = addressParking;
    }



}
