package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.BookMark;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
}
