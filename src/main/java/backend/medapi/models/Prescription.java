package backend.medapi.models;

public class Prescription {
    String medicine;
    Double matchScore;
    String[] treatsFor;

    public Prescription(String medicine, Double matchScore, String[] treatsFor) {
        this.medicine = medicine;
        this.matchScore = matchScore;
        this.treatsFor = treatsFor;
    }

    public String getMedicine() {
        return medicine;
    }

    public Double getMatchScore() {
        return matchScore;
    }

    public String[] getTreatsFor() {
        return treatsFor;
    }
}
