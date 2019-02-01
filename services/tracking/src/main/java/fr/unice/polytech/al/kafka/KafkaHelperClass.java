package fr.unice.polytech.al.kafka;

public class KafkaHelperClass {

    Long idGood;

    Long idDriver;

    public KafkaHelperClass() {}

    public KafkaHelperClass(Long idGood, Long idDriver) {
        this.idGood = idGood;
        this.idDriver = idDriver;
    }

    public Long getIdGood() {
        return idGood;
    }

    public void setIdGood(Long idGood) {
        this.idGood = idGood;
    }

    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }


}
