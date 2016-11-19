package com.restservice.participants;

import java.util.ArrayList;

public class ParticipantsManager {
    ArrayList<Participant> participants;

    public ParticipantsManager() {
        participants = new ArrayList<>();
        init();
    }

    public void addParticipant(Participant participant) {

    }

    public void deleteParticipant(){

    }

    private void init(){
        addParticipant(new Participant(Participant.GOSHA_RUB, "Designer"));
        addParticipant(new Participant(Participant.ARTEMII_LEBEDEV, "Special designer"));
        addParticipant(new Participant(Participant.VASYA_PUP, "Doctor"));
        addParticipant(new Participant(Participant.GALYA_SMITH, "Teacher"));
    }
}
