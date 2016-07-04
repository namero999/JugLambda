package lol.corrado.model;

import lombok.Data;

@Data
public class Message {

    private String text;
    private Chat chat;

}