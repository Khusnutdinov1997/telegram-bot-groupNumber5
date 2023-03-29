package pro.sky.telegrambot.model;

import javax.persistence.*;

import java.util.Objects;

@Entity


public class BranchParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private String city;
    private String zip;
    private String address;
    private String workHours;
    private byte[] map;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BranchParams(Long branch, String name) {
        this.id = branch;
        this.name = name;
    }

    public BranchParams() {

    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkHours() {
        return workHours;
    }

    public byte[] getMap() {
        return map;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchParams that = (BranchParams) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public void setMap(byte[] map) {
        this.map = map;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
