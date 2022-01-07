package study.collector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.BookMark;
import study.collector.entity.BookMarkTable;
import study.collector.entity.Schedule;
import study.collector.entity.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            User user = new User("test", "test");
            persist(user);

            BookMarkTable table1 = new BookMarkTable("table1", user);
            BookMarkTable table2 = new BookMarkTable("table2", user);
            persist(table1, table2);

            BookMark bookMark1 = new BookMark("네이버", "https://www.naver.com/", table1);
            BookMark bookMark2 = new BookMark("구글", "https://www.google.co.kr/", table1);
            BookMark bookMark3 = new BookMark("유튜브", "https://www.youtube.com/", table2);
            BookMark bookMark4 = new BookMark("강남대", "https://web.kangnam.ac.kr/", table2);
            persist(bookMark1, bookMark2, bookMark3, bookMark4);

            Schedule sc = new Schedule(LocalDate.now(), "스케줄1", user);
            em.persist(sc);
        }

        public void persist(Object... objects) {
            for (Object object : objects) {
                em.persist(object);
            }
        }

    }

}
