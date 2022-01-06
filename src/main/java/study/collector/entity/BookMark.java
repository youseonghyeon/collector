package study.collector.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "url"})
public class BookMark {

    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;
    private String name;
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookmark_table_id")
    private BookMarkTable bookMarkTable;

    public BookMark(String name, String url, BookMarkTable bookMarkTable) {
        this.name = name;
        this.url = url;
        if (bookMarkTable != null) {
            assignBookMarkTable(bookMarkTable);
        }
    }

    public void assignBookMarkTable(BookMarkTable bookMarkTable) {
        this.bookMarkTable = bookMarkTable;
        bookMarkTable.getBookMarks().add(this);
    }

    public void changeBookMark(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
