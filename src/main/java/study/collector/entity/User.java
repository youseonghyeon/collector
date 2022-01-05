package study.collector.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String memo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<BookMarkTable> bookMarkTables = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Schedule> schedules = new ArrayList<>();

    public User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public User(String loginId, String password, String email) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public void changeMemo(String memo) {
        this.memo = memo;
    }
}
