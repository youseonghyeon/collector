package study.collector.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.BookMark;
import study.collector.entity.User;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class BookMarkRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    BookMarkRepository bookMarkRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 북마크_추가() {
        //given
        User user = createUser("user", "qwe123");
        BookMark bookMark = new BookMark("https://www.naver.com", "123.img", user);
        bookMarkRepository.save(bookMark);

        //when
        BookMark findBookMark = bookMarkRepository.findById(bookMark.getId()).get();

        //then
        assertThat(findBookMark).isEqualTo(bookMark);
    }

    @Test
    public void 북마크_삭제() {
        //given
        User user = createUser("user", "qwe123");
        BookMark bookMark = new BookMark("https://www.naver.com", "123.img", user);
        bookMarkRepository.save(bookMark);
        em.flush();
        em.clear();

        //when
        bookMarkRepository.delete(bookMark);
        em.flush();
        em.clear();

        //then
        List<BookMark> result = bookMarkRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }

    private User createUser(String uid, String password) {
        User user = new User(uid, password);
        return userRepository.save(user);
    }
}
