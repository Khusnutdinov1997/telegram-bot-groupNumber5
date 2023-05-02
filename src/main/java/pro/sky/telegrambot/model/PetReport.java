package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pet_report")
public class PetReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopterId;
    private LocalDate reportDate;
    private byte[] photo;
    private String diet;
    private String lifeStatus;
    private String behavior;

    public PetReport() {
    }

    public PetReport(Long id, Adopter adopterId, LocalDate reportDate, byte[] photo, String diet, String lifeStatus, String behavior) {
        this.id = id;
        this.adopterId = adopterId;
        this.reportDate = reportDate;
        this.photo = photo;
        this.diet = diet;
        this.lifeStatus = lifeStatus;
        this.behavior = behavior;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Adopter getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(Adopter adopterId) {
        this.adopterId = adopterId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport that = (PetReport) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}