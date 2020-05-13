package com.rsc.ndcvc.models;

import com.twilio.video.RemoteParticipant;
import com.twilio.video.Room;

import java.util.List;

public class ModelLiveVideo {
    //list participant in array
    private RemoteParticipant participants;
    private Room room;
    private String vid;

    //create constructor
    public ModelLiveVideo(){}

    //declare getter and setter

    public RemoteParticipant getParticipants() {
        return participants;
    }

    public void setParticipants(RemoteParticipant participants) {
        this.participants = participants;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
