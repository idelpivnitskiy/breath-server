package models;

import java.util.List;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * UserTask entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "usertask_seq", sequenceName = "usertask_seq")
public class UserTask implements PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usertask_seq")
    public Long id; 

    @Constraints.Required
    public Long profileId;

    @Constraints.Required
    public Long taskId;
    
    @Constraints.Required
    public Date datetime;

    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer approved;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer rejected;

    @Constraints.Required
    public String status;

    @Constraints.Required
    @Constraints.MaxLength(value=100)
    public String image;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer liked;

    public UserTask() {
    }

    /**
    * Default constructor for adding new UserTask
    * that set profileId, taskId and name of image
    */
    public UserTask(Long profileId, Long taskId, String image) {
        this.datetime = new Date();
        this.approved = 0;
        this.rejected = 0;
        this.status = "pending";
        this.image = image;
        this.liked = 0;
    }

    /**
     * Find a UserTask by id.
     */
    public static UserTask findById(Long id) {
        return JPA.em().find(UserTask.class, id);
    }

    /**
    * Find a UserTask by profileId
    */
    public static List<UserTask> findByProfileId(Long profileId, Integer page) {
        List<UserTask> listOfTasks = JPA.em()
        .createQuery("from UserTask where profileId = :pi order by datetime desc")
        .setParameter("pi", profileId)
        .setFirstResult((page - 1) * PAGESIZE)
        .setMaxResults(PAGESIZE)
        .getResultList();
        return listOfTasks;
    }
    
    /**
     * Insert this new UserTask.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this UserTask.
     */
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Delete this UserTask.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
