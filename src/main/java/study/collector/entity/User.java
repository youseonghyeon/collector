package study.collector.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String uid;
    private String password;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Calendar> calendars = new ArrayList<>();

    public User(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }
}
