package com.stackroute.kanban.services;

import com.stackroute.kanban.config.EmailDetails;
import com.stackroute.kanban.config.Producer;
import com.stackroute.kanban.exception.IdAlreadyExistingException;
import com.stackroute.kanban.exception.IdNotFoundException;
import com.stackroute.kanban.exception.UserAlreadyExistingException;



import com.stackroute.kanban.model.*;
import com.stackroute.kanban.repositry.KanbanRepositry;
import com.stackroute.kanban.repositry.UserDetailsRepositry;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class KanbanDetailsServiceImpl implements KanbanDetailsService {

    private final UserDetailsRepositry userDetailsRepositry;
    private final KanbanRepositry kanbanRepositry;

    private final Producer producer;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public KanbanDetailsServiceImpl(UserDetailsRepositry userDetailsRepositry, KanbanRepositry kanbanRepositry, Producer producer) {
        this.userDetailsRepositry = userDetailsRepositry;
        this.kanbanRepositry = kanbanRepositry;


        this.producer = producer;
    }

    @Override
    public User registerUserDetails(User user) throws UserAlreadyExistingException {
        if (userDetailsRepositry.findById(user.getEmailId()).isEmpty()) {
            EmailDetails emailDetails = new EmailDetails(user.getEmailId(), user.getName(), "YOU REGISTRATION HAS DONE SUCCESSFULLY", "KANBAN APPLICATION");
            producer.sendMessageToRabbitMq(emailDetails);

            return userDetailsRepositry.save(user);
        } else {
            throw new UserAlreadyExistingException();
        }
    }
@Override
    public User UpdateRegisterUserDetails(String emailId, String name, String address, String phoneNumber, MultipartFile file) throws IdNotFoundException, IOException {
        if(userDetailsRepositry.findById(emailId).isPresent()){
            User user1=userDetailsRepositry.findById(emailId).get();
            user1.setName(name);
            user1.setAddress(address);
            user1.setPhoneNumber(phoneNumber);
            user1.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            return userDetailsRepositry.save(user1);
        }
        else throw new IdNotFoundException();

    }



    @Override
    public User getUserDetails(String emailId) throws UserAlreadyExistingException {
        if (userDetailsRepositry.findById(emailId).isPresent()) {
            return userDetailsRepositry.findById(emailId).get();
        } else {
            throw new UserAlreadyExistingException();
        }
    }


    //    CRUD OPERATION FOR KANBAN
    @Override
    public KanbanDetails saveNewKanban(KanbanDetails kanbanDetails) throws IdAlreadyExistingException {
//        if (kanbanRepositry.findById(kanbanDetails.getKanbanId()).isEmpty()) {
        return kanbanRepositry.insert(kanbanDetails);
//        } else {
//            throw new IdAlreadyExistingException();
//        }
    }


    @Override
    public List<KanbanDetails> getAllKanban() {
        return kanbanRepositry.findAll();
    }


    @Override
    public KanbanDetails updatekanban( String kanbanId,KanbanDetails kanbanDetails) throws IdNotFoundException {
        if (kanbanRepositry.findById(kanbanDetails.getKanbanId()).isPresent()) {
            KanbanDetails kanbanDetails1=kanbanRepositry.findById(kanbanId).get();
//            kanbanDetails1.setKanbanId(kanbanDetails.getKanbanId());
            kanbanDetails1.setKanbanTitle(kanbanDetails.getKanbanTitle());
            kanbanDetails1.setStatus(kanbanDetails.getStatus());
            kanbanDetails1.setMembers(kanbanDetails.getMembers());
            kanbanDetails1.setEmailId(kanbanDetails.getEmailId());

            return kanbanRepositry.save(kanbanDetails1);
        } else {
            throw new IdNotFoundException();

        }
    }


    @Override
    public boolean deleteKanbanById(String kanbanId) throws IdNotFoundException {
        boolean result = false;
        if (kanbanRepositry.findById(kanbanId).isPresent()) {
            kanbanRepositry.deleteById(kanbanId);
            result = true;
        } else {
            throw new IdNotFoundException();
        }
        return result;
    }


    @Override
    public Optional<KanbanDetails> getKanbanById(String id) throws IdNotFoundException {
        if (kanbanRepositry.findById(id).isPresent()) {
            return kanbanRepositry.findById(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    @Override
    public List<KanbanDetails> getByEmail(String emailId) {
        return kanbanRepositry.findByEmail(emailId);
    }


//CRUD OPERATION FOR STATUS

    @Override
    public KanbanDetails addNewStatusToKanban(String KanbanId, Status status) throws IdNotFoundException, IdAlreadyExistingException {
        KanbanDetails kanbanDetails = kanbanRepositry.findById(KanbanId).get();
        if (kanbanRepositry.findById(KanbanId).isPresent()) {
            List<Status> everyStatus = kanbanDetails.getStatus();
            if(everyStatus==null) {
                everyStatus = new ArrayList<Status>();
//                        allTask.add(task);
                kanbanDetails.addStatus(status);
//                        kanbanDetails.addStatus(s1);
                return kanbanRepositry.save(kanbanDetails);
            }
           boolean result=true;
            for (Status s1 : everyStatus) {
                if (s1.getStatusTitle().equals(status.getStatusTitle())) {
                    result=false;
                }
            }
              if (result){
                  kanbanDetails.addStatus(status);
                    return kanbanRepositry.save(kanbanDetails);
              }
              else {
                  throw new IdAlreadyExistingException();
              }
        }

        else {
            throw new IdNotFoundException();
        }

    }





    @Override
    public KanbanDetails removeStatusFromKanban(String kanbanId, Status status) throws IdAlreadyExistingException, IdNotFoundException {

        if (kanbanRepositry.findById(kanbanId).isPresent()) {
            boolean result=false;
            KanbanDetails kanbanDetails = kanbanRepositry.findById(kanbanId).get();
            List<Status> allStatus = kanbanDetails.getStatus();
            for (Status s1 : allStatus) {
                if (status.getStatusTitle().equals(s1.getStatusTitle())) {
                    result = true;
                }
//                else
//                {
//                    result = false;
//                }
            }
            if (result==true){
                kanbanDetails.getStatus().remove(status);
                return kanbanRepositry.save(kanbanDetails);
            }
            else {
                throw new IdNotFoundException();
            }
        } else {
            throw new IdNotFoundException();
        }

    }

//    @Override
//    public KanbanDetails updateStatusToKanban(String KanbanId,String statusTitle,Status status) throws IdNotFoundException {
//
//        if (kanbanRepositry.findById(KanbanId).isPresent()) {
//            boolean result=false;
//            KanbanDetails kanbanDetails = kanbanRepositry.findById(KanbanId).get();
//            List<Status> allStatus = kanbanDetails.getStatus();
//            for (Status s1 : allStatus) {
//                System.out.println((status.getStatusTitle().equals(s1.getStatusTitle())));
//                if (statusTitle.equals(s1.getStatusTitle())) {
//                    result = true;
//                }
////                else {
////
////                    result = false;
////                }
//            }
//            if (result==true){
//                List<Status> statusList = kanbanDetails.getStatus();
//                Status myStatus = null;
//                for (Status status1 : statusList) {
//                    if (status1.getStatusTitle().equals(status.getStatusTitle())) {
//                        myStatus = status1;
//                        break;
//                    }
//                }
//                statusList.remove(myStatus);
//                statusList.add(status);
//                kanbanDetails.setStatus(statusList);
//                return kanbanRepositry.save(kanbanDetails);
//            }
//            else {
//                throw new IdNotFoundException();
//            }
//        } else {
//            throw new IdNotFoundException();
//        }
//
//    }




//    public KanbanDetails updateStatusToKanban(String spaceId, Status status) {
//        KanbanDetails kanbanDetails = kanbanRepositry.findById(spaceId).get();
//        List<Status> statusList = kanbanDetails.getStatus();
//        Status myStatus = null;
//        for (Status status1 : statusList) {
//            if (status1.getStatusTitle().equals(status.getStatusTitle())) {
//                myStatus = status1;
//                break;
//            }
//        }
//        statusList.remove(myStatus);
//        statusList.add(status);
//        kanbanDetails.setStatus(statusList);
//        return kanbanRepositry.save(kanbanDetails);

//    }
@Override
    public KanbanDetails updateStatusToKanban(String kanbanId,String PriviousName ,Status status) {
        KanbanDetails kanbanDetails = kanbanRepositry.findById(kanbanId).get();
        List<Status> statusList = kanbanDetails.getStatus();
        Status myStatus = null;
        for (Status status1 : statusList) {
            if (status1.getStatusTitle().equals(PriviousName)) {
                myStatus = status1;
                break;
            }
        }
        statusList.remove(myStatus);
        statusList.add(status);
        kanbanDetails.setStatus(statusList);
        return kanbanRepositry.save(kanbanDetails);
    }


    @Override
    public List<Status> getAllStatusInKanban(String KanbanId) throws IdNotFoundException {
        if (kanbanRepositry.findById(KanbanId).isPresent()) {
            List<Status> status = new ArrayList<>();
            KanbanDetails kanbanDetails = kanbanRepositry.findById(KanbanId).get();
            status = kanbanDetails.getStatus();
            return status;
        }
        else {
            throw new IdNotFoundException();
        }
    }



//    CRUD OPERATION FOR TASK
//    @Override
//    public KanbanDetails addNewTaskToStatus(String KanbanId, String StatusTitle, Task task) throws IdNotFoundException, IdAlreadyExistingException {
//        if (kanbanRepositry.findById(KanbanId).isPresent()){
//            boolean result=false;
//            KanbanDetails kanbanDetails=kanbanRepositry.findById(KanbanId).get();
//            List<Status> allStatus=kanbanDetails.getStatus();
//            for(Status s1:allStatus){
//                if (s1.getStatusTitle().equals(StatusTitle)){
//
//                    List<Task> allTask=s1.getTask();
//                    if(allTask==null){
//                        allTask=new ArrayList<Task>();
////                        allTask.add(task);
//                        s1.addTask(task);
////                        kanbanDetails.addStatus(s1);
//                        return kanbanRepositry.save(kanbanDetails);
//
//                    }
//                    for (Task t1:allTask){
//                        if (t1.getTaskTitle().equals(task.getTaskTitle())){
//                            result=false;
//                            break;
//                        }
//                        else {
//                          result=true;
//                        }
//                    }
//                    if(result){
//                        s1.addTask(task);
//                        kanbanDetails.addStatus(s1);
//                        return kanbanRepositry.save(kanbanDetails);
//                    }
//
//                }
//
//
//            }
//        }else {
//            throw new IdNotFoundException();
//        }
//return null;
//    }

    @Override
    public KanbanDetails addNewTaskToStatus(String kanbanId, String StatusTitle, Task task) {
        KanbanDetails kanbanDetails = kanbanRepositry.findById(kanbanId).get();
        List<Status> status = kanbanDetails.getStatus();
        for (Status status1 : status) {
            if (status1.getStatusTitle().equals(StatusTitle)) {
                List<String> mem = task.getAssignTo();

                int count = mem.size();
                System.out.println(count);
                if (count >= 3) {
                    return null;
                } else {
                    status1.addTask(task);
//                List<Task> ass= status1.getTask();
//                for(Task t1:ass) {
//                    if(t1.getTaskTitle().equals(task.getTaskTitle())) {
//                        List<String> assignTo = t1.getAssignTo();
//                        for (String s:task.getAssignTo()){
//                            assignTo.add(s);
//                        }
//                    }
//                }
                }
            }
        }
        return kanbanRepositry.save(kanbanDetails);
    }

//        KanbanDetails kanbanDetails = kanbanRepositry.findById(spaceId).get();
//        List<Status> status = kanbanDetails.getStatus();
//        for (Status status1 : status) {
//            if (status1.getStatusTitle().equals(StatusTitle)) {
////                status1.addTask(task);
//                Status s1 = status1;
//                List<Task> allTask = s1.getTask();
//                if(allTask==null){
//                    allTask=new ArrayList<Task>();
//                    allTask.add(task);
//                    s1.setTask(allTask);
//                    status.add(s1);
//                    kanbanDetails.setStatus(status);
//                    return kanbanRepositry.save(kanbanDetails);
//
//                }
//                for(Task t1 : allTask){
//                    if(!t1.getTaskTitle().equals(task.getTaskTitle())){
//                        s1.addTask(task);
//                        status.add(s1);
//                        kanbanDetails.setStatus(status);
//                        return kanbanRepositry.save(kanbanDetails);
//                    }
//                }
//            }
//        }
//       return null;
//    }



    @Override
    public KanbanDetails removeTaskFromStatus(String kanbanId, String statusTitle, Task task) throws IdNotFoundException {

        if (kanbanRepositry.findById(kanbanId).isPresent()) {
            boolean result = false;
            KanbanDetails kanbanDetails = kanbanRepositry.findById(kanbanId).get();
            List<Status> allStatus = kanbanDetails.getStatus();
            for (Status s1 : allStatus) {
                if (statusTitle.equals(s1.getStatusTitle())) {
                    Status status = s1;
                    List<Task> allTask1 = s1.getTask();
                    for (Task t2 : allTask1) {
                        if (task.getTaskTitle().equals(t2.getTaskTitle())) {
                            result = true;
                        }
//                        else {
//                            result = false;
//                        }
                    }
                }
            }
            if (result == true) {
                List<Status> status = kanbanDetails.getStatus();
                for (Status status1 : status) {
                    if (status1.getStatusTitle().equals(statusTitle)) {
//                      status1.getTask().remove(task);
                        List<Task> tasks = status1.getTask();
                        ListIterator<Task> listIterator = tasks.listIterator();
                        if(tasks != null){


                            for(Task t1 : tasks){
                                listIterator.next();
                                if(t1.getTaskTitle().equals(task.getTaskTitle())){
                                    break;
                                }
                            }

                        }
                        listIterator.remove();
                        status1.setTask(tasks);
                    }
                }

                kanbanDetails.setStatus(status);
                return kanbanRepositry.save(kanbanDetails);
            } else {
                throw new IdNotFoundException();
            }
        } else {
            throw new IdNotFoundException();
        }


    }




//    @Override
//    public KanbanDetails removeTaskFromStatus(String kanbanId, String statusTitle, Task task) throws IdNotFoundException {
//
//        if (kanbanRepositry.findById(kanbanId).isPresent()) {
//            boolean result = false;
//            KanbanDetails kanbanDetails = kanbanRepositry.findById(kanbanId).get();
//            List<Status> allStatus = kanbanDetails.getStatus();
//            for (Status s1 : allStatus) {
//                if (statusTitle.equals(s1.getStatusTitle())) {
//                    Status status = s1;
//                    List<Task> allTask1 = s1.getTask();
//                    for (Task t2 : allTask1) {
//                        if (task.getTaskTitle().equals(t2.getTaskTitle())) {
//                            result = true;
//                        }
////                        else {
////                            result = false;
////                        }
//                    }
//                }
//            }
//            if (result == true) {
//                List<Status> status = kanbanDetails.getStatus();
//                for (Status status1 : status) {
//                    if (status1.getStatusTitle().equals(statusTitle)) {
//                      status1.getTask().remove(task);
//            }
//                }
//                 kanbanDetails.setStatus(status);
//                return kanbanRepositry.save(kanbanDetails);
//            } else {
//                throw new IdNotFoundException();
//            }
//        } else {
//            throw new IdNotFoundException();
//        }
//
//
//    }



//        KanbanDetails kanbanDetails = kanbanRepositry.findById(kanbanId).get();
//        List<Status> status = kanbanDetails.getStatus();
//        for (Status status1 : status) {
//            if (status1.getStatusId().equals(statusId)) {
//                status1.getTask().remove(task);
//            }
//        }
//
//        return kanbanRepositry.save(kanbanDetails);
//    }

    @Override
    public List<Task> getAllTaskInStatus(String KanbanId, String statusTitle) {
        List<Status> status1=new ArrayList<>();
        KanbanDetails kanbanDetails=kanbanRepositry.findById(KanbanId).get();
        status1=kanbanDetails.getStatus();
        List<Task> tasks =new ArrayList<>();
        for (Status  status2 :status1)
            if( status2.getStatusTitle().equals(statusTitle))
                tasks = status2.getTask();
        System.out.println(tasks);
        return tasks;
    }
//    @Override
//    public KanbanDetails updateTaskToStatus(String KanbanId, String statusTitle,Task task) throws IdNotFoundException {
//
//        if (kanbanRepositry.findById(KanbanId).isPresent()) {
//            boolean result = false;
//            KanbanDetails kanbanDetails = kanbanRepositry.findById(KanbanId).get();
//            List<Status> allStatus = kanbanDetails.getStatus();
//            for (Status s1 : allStatus) {
//                if (statusTitle.equals(s1.getStatusTitle())) {
//                    Status status1 = s1;
//                    List<Task> allTask1 = s1.getTask();
//                    for (Task t2 : allTask1) {
//                        if (task.getTaskTitle().equals(t2.getTaskTitle())) {
//                            result = true;
//                        }
////                        else {
////                            result = false;
////                        }
//                    }
//                }
//            }
//            if (result == true) {
//                Status myStatus = null;
//                Status status=new Status();
//                List<Status> statusList = kanbanDetails.getStatus();
//                for (Status status1 : statusList) {
//                    if (status1.getStatusTitle().equals(statusTitle)) {
//                        myStatus = status1;
//                        Task myTask = null;
//
//                        List<Task> tasks=status1.getTask();
//                        for(Task task1:tasks)
//                        {
//                            if(task1.getTaskTitle().equals(task.getTaskTitle()))
//                            {
//                                myTask=task1;
//                            }
//                        }
//                        tasks.remove(myTask);
//                        tasks.add(task);
//                        status.setStatusId(myStatus.getStatusId());
//                        status.setStatusTitle(myStatus.getStatusTitle());
//                        status.setTask(tasks);
//                        statusList.remove(status1);
//                        statusList.add(status);
//                        break;
//
//                    }
//                }
//                kanbanDetails.setStatus(statusList);
//
//                }
//            else {
//                throw new IdNotFoundException();
//            }
//            return kanbanRepositry.save(kanbanDetails);
//            } else {
//                throw new IdNotFoundException();
//            }
//
//        }




    @Override
//    public Workspace updateTaskToStatus(String spaceId, String statusName,String taskName, Task task) {
           public KanbanDetails updateTaskToStatus(String KanbanId, String statusTitle,String taskTitle,Task task) throws IdNotFoundException {

        KanbanDetails KanbanDetails=kanbanRepositry.findById(KanbanId).get();
        List<Status> statusList=KanbanDetails.getStatus();

        Task task1=null;
        for(Status status:statusList){
            if(status.getStatusTitle().equals(statusTitle)){
                for(Task t:status.getTask()){
                    if(t.getTaskTitle().equals(taskTitle))
                    {
                        task1=t;
                        System.out.println("T"+task1);
                        status.getTask().remove(task1);
                        status.getTask().add(task);

                        return kanbanRepositry.save(KanbanDetails);
                    }
                }
            }
        }
        return kanbanRepositry.save(KanbanDetails);
    }













//
//        KanbanDetails kanbanDetails = kanbanRepositry.findById(KanbanId).get();
//        List<Status> statusList = kanbanDetails.getStatus();
//        Status myStatus = null;
//        Status status=new Status();
//        for (Status status1 : statusList) {
//            if (status1.getStatusTitle().equals(statusTitle)) {
//                myStatus = status1;
//                Task myTask = null;
//
//                List<Task> tasks=status1.getTask();
//                for(Task task1:tasks)
//                {
//                   if(task1.getTaskTitle().equals(task.getTaskTitle()))
//                   {
//                      myTask=task1;
//                   }
//                }
//                tasks.remove(myTask);
//                tasks.add(task);
//                status.setStatusId(myStatus.getStatusId());
//                status.setStatusTitle(myStatus.getStatusTitle());
//                status.setTask(tasks);
//                statusList.remove(status1);
//                statusList.add(status);
//                break;
//            }
//        }
//
//
//        kanbanDetails.setStatus(statusList);
//        return kanbanRepositry.save(kanbanDetails);
//    }





//CRUD OPERATION FOR MEMBERS
    @Override
    public List<KanbanDetails> getByMembers(String emailId) {
        List<KanbanDetails> kanbanDetails = kanbanRepositry.findAll();
        List<KanbanDetails> list = new ArrayList<>();

        for (KanbanDetails details : kanbanDetails) {
            if (details.getMembers() != null) {
                List<String> strings = details.getMembers();
                for (String s : strings) {
                    if (s.equals(emailId)) {
                        list.add(details);
                    }
                }

            }

        }
        return list;
    }

    @Override
    public KanbanDetails addGuestMembers(String kanbanId, String emailId) {
        KanbanDetails kanbanDetails=kanbanRepositry.findById(kanbanId).get();
        kanbanDetails.addEmail(emailId);
        kanbanRepositry.save(kanbanDetails);
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("postamailtovp@gmail.com");
        mailMessage.setTo(emailId);
        mailMessage.setSubject("KANBAN");
        mailMessage.setText("CHECK YOUR KANBAN. NEW KANBAN IS ASSIGNED: ");
        javaMailSender.send(mailMessage);
        return kanbanDetails;
    }

    @Override
    public List<String> getAllRegisterUser() {
        List<User> users=userDetailsRepositry.findAll();
        List<String> useremail=new ArrayList<>();
        for(User user:users){
            useremail.add(user.getEmailId());
        }
        return useremail;
    }


    @Override
    public List<String> getAllMemberInKanban(String kanbanId) {
        KanbanDetails kanbanDetails=kanbanRepositry.findById(kanbanId).get();
        List<String> allmembersinkanban=new ArrayList<>();
        allmembersinkanban=kanbanDetails.getMembers();
        return allmembersinkanban;
    }

    @Override
    public KanbanDetails deleteGuestMembers(String kanbanId, String emailId) {
        KanbanDetails kanbanDetails=kanbanRepositry.findById(kanbanId).get();

        for(String email:kanbanDetails.getMembers()){


            if(email.equals(emailId)) {
                kanbanDetails.getMembers().remove(email);
                return kanbanRepositry.save(kanbanDetails);
            }
            if(kanbanDetails.getMembers()==null){
                return null;
            }



        }

        return kanbanDetails;
    }


    @Override
    public KanbanDetails addAssignToInTask(String kanbanId, String StatusTitle, String TaskTitle, String emailId) {
        KanbanDetails kanbanDetails=kanbanRepositry.findById(kanbanId).get();
        List<Status> allStatus=new ArrayList<>();
        allStatus=kanbanDetails.getStatus();
        for(Status s:allStatus){
            if(s.getStatusTitle().equals(StatusTitle)){
                List<Task> allTask=new ArrayList<>();
                allTask=s.getTask();
                for (Task t:allTask){
                    if (t.getTaskTitle().equals(TaskTitle)){
                        if(t.getAssignTo()==null){
                        t.setAssignTo(new ArrayList<String>());}
                        List<String> member=t.getAssignTo();
                       int count= member.size();
                        System.out.println(count);
                        if(count>=2){
                            return null;
                        }
                        else {
                            member.add(emailId);
                            t.setAssignTo(member);
                            s.getTask().remove(t);
                            s.addTask(t);
//                        kanbanDetails.getStatus().remove(s);
//                        kanbanDetails.addStatus(s);
                            SimpleMailMessage mailMessage1 = new SimpleMailMessage();
                            mailMessage1.setFrom("postamailtovp@gmail.com");
                            mailMessage1.setTo(emailId);
                            mailMessage1.setSubject("KANBAN");
                            mailMessage1.setText("CHECK YOUR KANBAN. NEW TASK IS ASSIGNED:" + TaskTitle);
                            javaMailSender.send(mailMessage1);
                            return kanbanRepositry.save(kanbanDetails);
                        }
                    }
                }
            }
        }
        return kanbanRepositry.save(kanbanDetails);
    }


    @Override
    public KanbanDetails deleteAssignToInTask(String kanbanId, String StatusTitle, String TaskTitle, String emailId) {
        KanbanDetails kanbanDetails=kanbanRepositry.findById(kanbanId).get();
        List<Status> allStatus=new ArrayList<>();
        allStatus=kanbanDetails.getStatus();
        for(Status s:allStatus){
            if(s.getStatusTitle().equals(StatusTitle)){
                List<Task> allTask=new ArrayList<>();
                allTask=s.getTask();
                for (Task t:allTask){
                    if (t.getTaskTitle().equals(TaskTitle)){
                        List<String> allMembers = t.getAssignTo();
                        for(String m:allMembers){
                        if(m.equals(emailId)){
                            allMembers.remove(m);
                            t.setAssignTo(allMembers);
//                            allTask.remove(t);
                            allTask.add(t);
                            s.setTask(allTask);
//                            allStatus.remove(s);
                            allStatus.add(s);
//                            kanbanDetails.getStatus().remove(allStatus);
                            kanbanDetails.setStatus(allStatus);
                            return kanbanRepositry.save(kanbanDetails);
                        }
                        }
                    }
                }
            }
        }
        return kanbanRepositry.save(kanbanDetails);
    }

    @Override
    public List<String> getAllAssignToInTask(String kanbanId, String StatusTitle, String TaskTitle) {
        KanbanDetails kanbanDetails=kanbanRepositry.findById(kanbanId).get();
        List<Status> allStatus=new ArrayList<>();
        allStatus=kanbanDetails.getStatus();
        for(Status s:allStatus){
            if(s.getStatusTitle().equals(StatusTitle)){
                List<Task> allTask=new ArrayList<>();
                allTask=s.getTask();
                for (Task t:allTask){
                    if (t.getTaskTitle().equals(TaskTitle)){
                        List<String> allAssignToMembers=new ArrayList<>();
                        allAssignToMembers=t.getAssignTo();
                        return allAssignToMembers;
                    }
                }
            }
        }
        return null;
    }
}



