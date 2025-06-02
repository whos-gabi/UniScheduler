package persistence;

import domain.*;
import java.util.List;

/**
 * Repository interface for timetable-related operations
 */
public interface TimetableRepository extends GenericRepository<TimetableEntry> {
    
    /**
     * Find timetable entries by group name
     */
    List<TimetableEntry> findByGroupName(String groupName);
    
    /**
     * Find timetable entries by teacher ID
     */
    List<TimetableEntry> findByTeacherId(String teacherId);
    
    /**
     * Find timetable entries by room ID
     */
    List<TimetableEntry> findByRoomId(String roomId);
    
    /**
     * Find timetable entries by course ID
     */
    List<TimetableEntry> findByCourseId(String courseId);
    
    /**
     * Find timetable entries by day of week
     */
    List<TimetableEntry> findByDayOfWeek(String dayOfWeek);
    
    /**
     * Find timetable entries by week type
     */
    List<TimetableEntry> findByWeekType(String weekType);
} 