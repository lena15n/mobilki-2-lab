package com.restservice.participants;

public class Participant {
    public static String VASYA_PUP = "Gosha Rubchinskii";
    public static String ARTEMII_LEBEDEV = "Artemii Lebedev";
    public static String GOSHA_RUB = "Gosha Rubchinskii";
    public static String GALYA_SMITH = "Galya Smith";

    private String fio;
    private String post;

    public Participant(String fio, String post){
        this.fio = fio;
        this.post = post;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
