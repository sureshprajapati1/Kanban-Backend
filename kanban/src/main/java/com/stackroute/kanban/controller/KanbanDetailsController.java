package com.stackroute.kanban.controller;

import com.stackroute.kanban.exception.IdAlreadyExistingException;
import com.stackroute.kanban.exception.IdNotFoundException;
import com.stackroute.kanban.exception.UserAlreadyExistingException;
import com.stackroute.kanban.model.*;
import com.stackroute.kanban.services.KanbanDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/kanbandetails")

public class KanbanDetailsController {
    @Autowired
    private KanbanDetailsService kanbanDetailsService;


    //    http://localhost:8888/kanbandetails/registerUserDetails
    @PostMapping("/registerUserDetails")
    public ResponseEntity<?> registerUserDetails(@RequestBody User user) throws UserAlreadyExistingException {
        try {
//            user.setKanbanDetails(new ArrayList<KanbanDetails>());
            return new ResponseEntity<>(kanbanDetailsService.registerUserDetails(user), HttpStatus.OK);
        }
        catch (UserAlreadyExistingException ex){
            ex.printStackTrace();
            throw new UserAlreadyExistingException();
        }

    }

    //    http://localhost:8888/kanbandetails/updateregisterUserDetails
    @PostMapping("/updateregisterUserDetails")
    public ResponseEntity<?> UpdateRegisterUserDetails(@RequestParam String emailId, @RequestParam String name,@RequestParam String address,@RequestParam String phoneNumber, @RequestParam MultipartFile file) throws IdNotFoundException, IOException {
        try {
//            user.setKanbanDetails(new ArrayList<KanbanDetails>());
            return new ResponseEntity<>(kanbanDetailsService.UpdateRegisterUserDetails(emailId, name, address, phoneNumber, file), HttpStatus.OK);
        }
        catch (IdNotFoundException ex){
            ex.printStackTrace();
            throw new IdNotFoundException();
        }
    }












        //    http://localhost:8888/kanbandetails/getuserdetails
    @GetMapping("/getuserdetails")
    public ResponseEntity<?> getUserDetails(HttpServletRequest request){
        String currentUser= (String) request.getAttribute("userdetailsemailid");
        System.out.println(currentUser);
        try {
            return new ResponseEntity<>(kanbanDetailsService.getUserDetails(currentUser),HttpStatus.OK);
        } catch (UserAlreadyExistingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    //    http://localhost:8888/kanbandetails/savenewkanban
    @GetMapping("/savenewkanban")
    public ResponseEntity<?> getKanbanById(@RequestBody KanbanDetails kanbanDetails) throws IdAlreadyExistingException {

        try {
            return new ResponseEntity<>(kanbanDetailsService.saveNewKanban(kanbanDetails),HttpStatus.OK);
        } catch (IdAlreadyExistingException e) {
            e.printStackTrace();
            throw new IdAlreadyExistingException();

        }

    }


    //    http://localhost:8888/kanbandetails/savenewkanban
    @PostMapping("/savenewkanban")
    public ResponseEntity<?> saveNewKanban(@RequestBody KanbanDetails kanbanDetails) throws IdAlreadyExistingException {

        try {
            return new ResponseEntity<>(kanbanDetailsService.saveNewKanban(kanbanDetails),HttpStatus.OK);
        } catch (IdAlreadyExistingException e) {
            e.printStackTrace();
            throw new IdAlreadyExistingException();

        }

    }

    //    http://localhost:8888/kanbandetails/getallkanban
    @GetMapping("/getallkanban")
    public ResponseEntity<?> getAllKanban( ){
        return new ResponseEntity<>(kanbanDetailsService.getAllKanban(),HttpStatus.OK);
    }


    //    http://localhost:8888/kanbandetails/updatekanban/{xxxx}
    @PostMapping("/updatekanban/{kanbanId}")
    public ResponseEntity<?> updateKanban( @PathVariable String kanbanId, @RequestBody KanbanDetails kanbanDetails) throws IdNotFoundException {
        try {
            return new ResponseEntity<>(kanbanDetailsService.updatekanban(kanbanId,kanbanDetails), HttpStatus.OK);
        }
        catch (IdNotFoundException e) {
            e.printStackTrace();
            throw new IdNotFoundException();
        }
    }

    //    http://localhost:8888/kanbandetails/deletekanban/{xxxx}
    @DeleteMapping("/deletekanban/{kanbanId}")
    public ResponseEntity<?> deleteKanban( @PathVariable String kanbanId) throws IdNotFoundException {
        try {
        return new ResponseEntity<>(kanbanDetailsService.deleteKanbanById(kanbanId),HttpStatus.OK);
        }
        catch (IdNotFoundException e) {
            e.printStackTrace();
            throw new IdNotFoundException();
        }
    }


    //    http://localhost:8888/kanbandetails/getkanbanbyemail/{xxxx}
    @GetMapping("/getkanbanbyemail/{emailId}")
    public ResponseEntity<?> GetKanbanByEmailId( @PathVariable String emailId){
        return new ResponseEntity<>(kanbanDetailsService.getByEmail(emailId),HttpStatus.OK);
    }



    ////CRUD OPERATION FOR STATUS

    //    http://localhost:8888/kanbandetails/addnewstatusintokanban/{xxxx}
    @PostMapping("/addnewstatusintokanban/{KanbanId}")
    public ResponseEntity<?> AddNewStatus(@PathVariable String KanbanId,@RequestBody Status status) throws IdNotFoundException ,IdAlreadyExistingException{
        try {
            return new ResponseEntity<>(kanbanDetailsService.addNewStatusToKanban(KanbanId,status),HttpStatus.OK);
        } catch (IdNotFoundException e) {
            throw new IdNotFoundException();
        } catch (IdAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
    }


    //    http://localhost:8888/kanbandetails/deletestatusintokanban/{xxxx}
    @PostMapping("/deletestatusintokanban/{KanbanId}")
    public ResponseEntity<?> deleteStatus(@PathVariable String KanbanId,@RequestBody Status status)throws IdNotFoundException,IdAlreadyExistingException{
        try {
            return new ResponseEntity<>(kanbanDetailsService.removeStatusFromKanban(KanbanId,status),HttpStatus.OK);
        }
        catch (IdNotFoundException e) {
            throw new IdNotFoundException();
        }
        catch (IdAlreadyExistingException e) {
            throw new IdAlreadyExistingException();
        }
    }


    //    http://localhost:8888/kanbandetails/updatestatusintokanban/{xxxx}
    @PostMapping("/updatestatusintokanban/{KanbanId}/{perviousName}")
    public ResponseEntity<?> UpdateStatus(@PathVariable String KanbanId,@PathVariable String perviousName,@RequestBody Status status){
        try {
            return new ResponseEntity<>(kanbanDetailsService.updateStatusToKanban(KanbanId,perviousName,status),HttpStatus.OK);
        } catch (IdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    //    http://localhost:8888/kanbandetails/getallstatusbykanbanid/{xxxx}
    @GetMapping("/getallstatusbykanbanid/{KanbanId}")
    public ResponseEntity<?> GetStatusByKanbanId( @PathVariable String KanbanId) throws IdNotFoundException {
        try {
            return new ResponseEntity<>(kanbanDetailsService.getAllStatusInKanban(KanbanId),HttpStatus.OK);
        } catch (IdNotFoundException e) {
            throw new IdNotFoundException();
        }
    }

    ////CRUD OPERATION FOR TASK

    //    http://localhost:8888/kanbandetails/addnewtaskintostatus/{xxxx}/{xxxx}
    @PostMapping("/addnewtaskintostatus/{KanbanId}/{StatusTitle}")
    public ResponseEntity<?> AddNewTask(@PathVariable String KanbanId,@PathVariable String StatusTitle,@RequestBody Task task) throws IdNotFoundException {
        try {
            return new ResponseEntity<>(kanbanDetailsService.addNewTaskToStatus(KanbanId,StatusTitle,task),HttpStatus.OK);
        } catch (IdNotFoundException e) {
            throw new IdNotFoundException();
        } catch (IdAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
    }

    //    http://localhost:8888/kanbandetails/deletetaskintostatus/{xxxx}/{xxxx}
    @PostMapping("/deletetaskintostatus/{KanbanId}/{StatusTitle}")
    public ResponseEntity<?> DeleteNewTask(@PathVariable String KanbanId,@PathVariable String StatusTitle,@RequestBody Task task) throws IdNotFoundException {
        try {
            return new ResponseEntity<>(kanbanDetailsService.removeTaskFromStatus(KanbanId,StatusTitle,task),HttpStatus.OK);
        } catch (IdNotFoundException e) {
            throw new IdNotFoundException();
        }
    }

    //    http://localhost:8888/kanbandetails/getalltaskbykanbanid/{xxxx}/{xxxx}
    @GetMapping("/getalltaskbykanbanid/{KanbanId}/{statusTitle}")
    public ResponseEntity<?> GetStatusByKanbanId( @PathVariable String KanbanId,@PathVariable String statusTitle){
        return new ResponseEntity<>(kanbanDetailsService.getAllTaskInStatus(KanbanId,statusTitle),HttpStatus.OK);
    }


    //        http://localhost:8888/kanbandetails/updatetaskintostatus/{xxxx}/{xxxx}
    @PostMapping("/updatetaskintostatus/{KanbanId}/{StatusTitle}/{taskTitle}")
    public ResponseEntity<?> UpdateNewTask(@PathVariable String KanbanId,@PathVariable String StatusTitle,@PathVariable String taskTitle,@RequestBody Task task) throws IdNotFoundException {
        return new ResponseEntity<>(kanbanDetailsService.updateTaskToStatus(KanbanId,StatusTitle,taskTitle,task),HttpStatus.OK);
    }


//ADDING AND GET MEMBERS

    //    http://localhost:8888/kanbandetails/getkanbanbymember/{xxxx}
    @GetMapping("/getkanbanbymember/{emailId}")
    public ResponseEntity<?> GetKanbanByMembers( @PathVariable String emailId){
        return new ResponseEntity<>(kanbanDetailsService.getByMembers(emailId),HttpStatus.OK);
    }


    //    http://localhost:8888/kanbandetails/addmemberinkanban/{xxxx}/{xxxx}
    @PostMapping("/addmemberinkanban/{KanbanId}/{emailId}")
    public ResponseEntity<?> AddMembersInKanban(@PathVariable String KanbanId, @PathVariable String emailId){
        return new ResponseEntity<>(kanbanDetailsService.addGuestMembers(KanbanId,emailId),HttpStatus.OK);
    }


    //    http://localhost:8888/kanbandetails/getalluser
    @GetMapping("/getalluser")
    public ResponseEntity<?> getAllUser( ){
        return new ResponseEntity<>(kanbanDetailsService.getAllRegisterUser(),HttpStatus.OK);
    }


    //    http://localhost:8888/kanbandetails/getallmembersinkanban/{xxx}
    @GetMapping("/getallmembersinkanban/{kanbanId}")
    public ResponseEntity<?> getAllMembersInKanban(@PathVariable String kanbanId){
        return new ResponseEntity<>(kanbanDetailsService.getAllMemberInKanban(kanbanId),HttpStatus.OK);
    }

    //    http://localhost:8888/kanbandetails/deletememberinkanban/{xxxx}/{xxxx}
    @PostMapping("/deletememberinkanban/{kanbanId}/{emailId}")
    public ResponseEntity<?> deleteMembersInKanban(@PathVariable String kanbanId, @PathVariable String emailId){
        return new ResponseEntity<>(kanbanDetailsService.deleteGuestMembers(kanbanId,emailId),HttpStatus.OK);
    }

    //    http://localhost:8888/kanbandetails/addassignmemberintask/{xxxx}/{xxxx}/{xxxx}/{xxxx}
    @PostMapping("/addassignmemberintask/{kanbanId}/{StatusTitle}/{TaskTitle}/{emailId}")
    public ResponseEntity<?> addAssignMemberInTask(@PathVariable String kanbanId, @PathVariable String StatusTitle,@PathVariable String TaskTitle,@PathVariable String emailId){
        return new ResponseEntity<>(kanbanDetailsService.addAssignToInTask(kanbanId,StatusTitle,TaskTitle,emailId),HttpStatus.OK);
    }

    //    http://localhost:8888/kanbandetails/deleteassignmemberintask/{xxxx}/{xxxx}/{xxxx}/{xxxx}
    @PostMapping("/deleteassignmemberintask/{kanbanId}/{StatusTitle}/{TaskTitle}/{emailId}")
    public ResponseEntity<?> deleteAssignMemberInTask(@PathVariable String kanbanId, @PathVariable String StatusTitle,@PathVariable String TaskTitle,@PathVariable String emailId){
        return new ResponseEntity<>(kanbanDetailsService.deleteAssignToInTask(kanbanId,StatusTitle,TaskTitle,emailId),HttpStatus.OK);
    }


    //    http://localhost:8888/kanbandetails/getallmembersinassignto/{xxx}/{xxx}/{xxx}
    @GetMapping("/getallmembersinassignto/{kanbanId}/{StatusTitle}/{TaskTitle}")
    public ResponseEntity<?> getAllMembersInKanban(@PathVariable String kanbanId,@PathVariable String StatusTitle,@PathVariable String TaskTitle){
        return new ResponseEntity<>(kanbanDetailsService.getAllAssignToInTask(kanbanId,StatusTitle,TaskTitle),HttpStatus.OK);
    }
}
