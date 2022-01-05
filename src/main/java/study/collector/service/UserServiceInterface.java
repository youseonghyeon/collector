package study.collector.service;

import study.collector.entity.User;

public interface UserServiceInterface {

    //loginId로 조회
    User searchByLoginId(String loginId);
    //조회
    User searchById(Long id);
    //회원가입
    Long join(User user);
    //아이디 중복검사
    boolean validateDuplLoginId(String userId);
    //탈퇴
    void removeById(Long userId);
    //메모저장
    void saveMemo(Long id, String newMemo);
    //로그인
    User login(String loginId, String password);
}
