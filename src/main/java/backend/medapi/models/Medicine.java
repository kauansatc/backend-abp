package backend.medapi.models;

import java.util.Arrays;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private boolean needsPrescription;
    private UUID[] treatsFor;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNeedsPrescription(boolean needsPrescription) {
        this.needsPrescription = needsPrescription;
    }

    public boolean getNeedsPrescription() {
        return needsPrescription;
    }

    public void addDisease(Disease disease) {
        var arr = Arrays.copyOf(treatsFor, treatsFor.length + 1);
        arr[arr.length - 1] = disease.getId();
        treatsFor = arr;
    }
}
