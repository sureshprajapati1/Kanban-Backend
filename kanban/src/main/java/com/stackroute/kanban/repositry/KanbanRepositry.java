package com.stackroute.kanban.repositry;

import com.stackroute.kanban.model.KanbanDetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KanbanRepositry extends MongoRepository<KanbanDetails,String> {
    Optional<KanbanDetails> findByKanbanTitle(String kanbanTitle);
    Optional<KanbanDetails> findByKanbanId(String kanbanId);
    @Query("{'emailId' : ?0}")
    List<KanbanDetails> findByEmail(String emailId);

    @Query("{'assignTo': ?0}")
    List<KanbanDetails> findByAssignTo(String assignTo);



}
