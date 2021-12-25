package study.collector.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import study.collector.entity.Schedule;

import javax.persistence.EntityManager;
import java.util.List;

import static study.collector.entity.QSchedule.schedule;

public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScheduleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 유저 아이디로 일정 조회
    @Override
    public List<Schedule> searchByUserId(Long userId) {
        return queryFactory
                .selectFrom(schedule)
                .where(schedule.user.id.eq(userId))
                .fetch();
    }
}
