package study.collector.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.collector.dto.bookmarkdto.BookMarkResponse;
import study.collector.entity.BookMark;
import study.collector.entity.BookMarkTable;
import study.collector.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
public class BookMarkServiceTest {

    @Autowired
    BookMarkService bookMarkService;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void init() {
    }

    //일정 추가
    @Test
    @Commit
    public void 북마크_검색() {
        //given
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

        List<BookMarkResponse> aa = bookMarkService.search(user.getId());
        //then
    }

    @Test
    void test() {

    }
}

