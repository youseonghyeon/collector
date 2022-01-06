package study.collector.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"tableName"})
public class BookMarkTable {

    @Id
    @GeneratedValue
    @Column(name= "bookmark_table_id")
    private Long id;
    private String tableName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "bookMarkTable", cascade = CascadeType.REMOVE)
    private List<BookMark> bookMarks = new ArrayList<>();

    public BookMarkTable(String tableName, User user) {
        this.tableName = tableName;
        if (user != null ) {
            assignUser(user);
        }
    }

    public void assignUser(User user) {
        this.user = user;
        user.getBookMarkTables().add(this);
    }

    public void changeTableName(String tableName) {
        this.tableName = tableName;
    }
}
