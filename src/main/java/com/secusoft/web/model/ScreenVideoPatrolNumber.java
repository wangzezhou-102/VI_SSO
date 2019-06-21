package com.secusoft.web.model;

public class ScreenVideoPatrolNumber {
    private Integer patrolNumber;
    private Integer patrolAlaramNumber;
    private Integer personAlaramNumber;
    private Integer eventAlaramNumber;
    private Integer vehicleAlaramNumber;

    public ScreenVideoPatrolNumber() {
    }

    public ScreenVideoPatrolNumber(Integer patrolNumber, Integer patrolAlaramNumber, Integer personAlaramNumber, Integer eventAlaramNumber, Integer vehicleAlaramNumber) {
        this.patrolNumber = patrolNumber;
        this.patrolAlaramNumber = patrolAlaramNumber;
        this.personAlaramNumber = personAlaramNumber;
        this.eventAlaramNumber = eventAlaramNumber;
        this.vehicleAlaramNumber = vehicleAlaramNumber;
    }

    public Integer getPatrolNumber() {
        return patrolNumber;
    }

    public void setPatrolNumber(Integer patrolNumber) {
        this.patrolNumber = patrolNumber;
    }

    public Integer getPatrolAlaramNumber() {
        return patrolAlaramNumber;
    }

    public void setPatrolAlaramNumber(Integer patrolAlaramNumber) {
        this.patrolAlaramNumber = patrolAlaramNumber;
    }

    public Integer getPersonAlaramNumber() {
        return personAlaramNumber;
    }

    public void setPersonAlaramNumber(Integer personAlaramNumber) {
        this.personAlaramNumber = personAlaramNumber;
    }

    public Integer getEventAlaramNumber() {
        return eventAlaramNumber;
    }

    public void setEventAlaramNumber(Integer eventAlaramNumber) {
        this.eventAlaramNumber = eventAlaramNumber;
    }

    public Integer getVehicleAlaramNumber() {
        return vehicleAlaramNumber;
    }

    public void setVehicleAlaramNumber(Integer vehicleAlaramNumber) {
        this.vehicleAlaramNumber = vehicleAlaramNumber;
    }

    @Override
    public String toString() {
        return "ScreenVideoPatrolNumber{" +
                "patrolNumber=" + patrolNumber +
                ", patrolAlaramNumber=" + patrolAlaramNumber +
                ", personAlaramNumber=" + personAlaramNumber +
                ", eventAlaramNumber=" + eventAlaramNumber +
                ", vehicleAlaramNumber=" + vehicleAlaramNumber +
                '}';
    }
}
