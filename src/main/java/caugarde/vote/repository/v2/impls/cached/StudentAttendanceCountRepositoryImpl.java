package caugarde.vote.repository.v2.impls.cached;

import caugarde.vote.model.entity.cached.StudentAttendanceCount;
import caugarde.vote.repository.v2.interfaces.cached.StudentAttendanceCountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.array.ArrayToStringFunction;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class StudentAttendanceCountRepositoryImpl implements StudentAttendanceCountRepository {

    private static final String attendanceKey = "studentAttendanceCount";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<StudentAttendanceCount> findTop10s() {
        Set<Object> allObjects = redisTemplate.opsForZSet().reverseRange(attendanceKey, 0, -1);
        return allObjects == null || allObjects.isEmpty() ? List.of() : allObjects.stream().map(this::convertToStudentAttendanceCount).toList();
    }

    @Override
    public List<StudentAttendanceCount> findTop1s() {
        Set<Object> top1Objects = redisTemplate.opsForZSet().reverseRange(attendanceKey, 0, 0);
        if (top1Objects == null || top1Objects.isEmpty()) {
            return List.of();
        }
        double topScore = Optional.ofNullable(redisTemplate.opsForZSet().score(attendanceKey, top1Objects.iterator().next()))
                .orElse(0.0);

        Set<Object> result = redisTemplate.opsForZSet().reverseRangeByScore(attendanceKey, topScore, topScore);
        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }

        return result.stream()
                .map(this::convertToStudentAttendanceCount)
                .toList();
    }

    @Override
    public void saveStudentAttendanceCounts(List<StudentAttendanceCount> attendanceCounts) {
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
            for (StudentAttendanceCount attendanceCount : attendanceCounts) {
                zSetOps.add(attendanceKey, attendanceCount, attendanceCount.getCount());
            }
            return null;
        });
    }

    @Override
    public void clearAllCache() {
        redisTemplate.delete(attendanceKey);
    }

    private StudentAttendanceCount convertToStudentAttendanceCount(Object obj) {
        if (obj instanceof StudentAttendanceCount) {
            return (StudentAttendanceCount) obj;
        }
        return null;
    }
}
