package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.BookMark;
import study.collector.entity.User;
import study.collector.repository.BookMarkRepository;
import study.collector.repository.ScheduleRepository;
import study.collector.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookMarkServiceTest {

    @Autowired
    BookMarkRepository bookMarkRepository;
    @Autowired
    BookMarkService bookMarkService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    //일정 추가
    @Test
    public void 북마크_추가() {
        //given
        User user = new User("abc", "qwe123");
        em.persist(user);

        //when
        bookMarkService.create("새로운 북마크", "http://url", "http://img.url", "category", user.getId());

        //then
        List<BookMark> bookMarks = bookMarkRepository.findAllByUserId(user.getId());
        assertThat(bookMarks.size()).isEqualTo(1);
    }
}
