package study.collector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.BookMark;
import study.collector.entity.BookMarkTable;
import study.collector.entity.User;
import study.collector.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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
            em.persist(user);
            BookMarkTable table1 = new BookMarkTable("table1", user);
            BookMarkTable table2 = new BookMarkTable("table2", user);
            em.persist(table1);
            em.persist(table2);

            BookMark bookMark1 = new BookMark("bookMark1", "http:ss", "http:ss", table1);
            BookMark bookMark2 = new BookMark("bookMark2", "http:ss", "http:ss", table1);
            BookMark bookMark3 = new BookMark("bookMark3", "http:ss", "http:ss", table2);
            BookMark bookMark4 = new BookMark("bookMark4", "http:ss", "http:ss", table2);
            em.persist(bookMark1);
            em.persist(bookMark2);
            em.persist(bookMark3);
            em.persist(bookMark4);
        }

    }

}
