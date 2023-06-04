package com.stackroute.kanban.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class KanbanDetails {
    @Id
    private String kanbanId;
    private String kanbanTitle;
    private List<Status> status;
    private String emailId;
    private  List<String> members;



    public void addStatus(Status status1) {

        if (Objects.isNull(status)) {
            status = new ArrayList<>();
        }
        status.add(status1);
    }


    public void addEmail(String emailId) {

        if (Objects.isNull(members)) {
            members = new ArrayList<>();
        }
        members.add(emailId);
    }


}
