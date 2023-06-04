package com.stackroute.kanban.services;

import com.stackroute.kanban.exception.IdAlreadyExistingException;
import com.stackroute.kanban.exception.IdNotFoundException;
import com.stackroute.kanban.exception.UserAlreadyExistingException;
import com.stackroute.kanban.model.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface KanbanDetailsService {
    public abstract User registerUserDetails(User user) throws UserAlreadyExistingException;


    public User UpdateRegisterUserDetails(String emailId, String name, String address, String phoneNumber, MultipartFile file) throws IdNotFoundException, IOException ;


    public abstract User getUserDetails(String emailId) throws UserAlreadyExistingException;


    KanbanDetails saveNewKanban(KanbanDetails kanbanDetails) throws IdAlreadyExistingException;// for creating new kanban

    public List<KanbanDetails> getAllKanban();// getting all kanban details from the date base who logged in

     KanbanDetails updatekanban(String kanbanId,KanbanDetails kanbanDetails) throws IdNotFoundException ;//for update the total kanban

    boolean deleteKanbanById(String kanbanId )throws IdNotFoundException; //delete kanban by id to delete

    public Optional<KanbanDetails> getKanbanById(String id) throws IdNotFoundException;// search by kanban id

    List<KanbanDetails> getByEmail(String emailId);




//


    KanbanDetails addNewStatusToKanban(String KanbanId,Status status) throws IdNotFoundException, IdAlreadyExistingException;

    KanbanDetails removeStatusFromKanban(String kanbanId ,Status status) throws IdAlreadyExistingException, IdNotFoundException;

    KanbanDetails updateStatusToKanban(String KanbanId,String PriviousName ,Status status) throws IdNotFoundException;

    List<Status> getAllStatusInKanban(String KanbanId) throws IdNotFoundException;


//

    KanbanDetails addNewTaskToStatus(String KanbanId,String StatusTitle,Task task) throws IdNotFoundException, IdAlreadyExistingException;

    public KanbanDetails removeTaskFromStatus(String kanbanId, String statusTitle,Task task) throws IdNotFoundException;

    List<Task> getAllTaskInStatus(String KanbanId,String statusTitle );

    KanbanDetails updateTaskToStatus(String KanbanId,String statusTitle,String taskTitle,Task task) throws IdNotFoundException;


//
       List<KanbanDetails> getByMembers(String emailId);

       KanbanDetails addGuestMembers(String kanbanId ,String emailId);


       List<String> getAllRegisterUser();

       public KanbanDetails deleteGuestMembers(String kanbanId, String emailId);

//

    List<String> getAllMemberInKanban(String kanbanId);


    public KanbanDetails addAssignToInTask( String kanbanId,String StatusTitle,String TaskTitle,String emailId);

    public KanbanDetails deleteAssignToInTask( String kanbanId,String StatusTitle,String TaskTitle,String emailId);

    public List<String> getAllAssignToInTask(String kanbanId, String StatusTitle, String TaskTitle);




    }
