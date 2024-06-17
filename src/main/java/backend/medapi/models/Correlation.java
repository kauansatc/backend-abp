package backend.medapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine-sympton")
public class Correlation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private int id;
    private String medicine;
    private String sympton;

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getSympton() {
        return sympton;
    }

    public void setSympton(String sympton) {
        this.sympton = sympton;
    }
}
