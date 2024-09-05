package project.chattingService.domain.member;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
//레파지토리임을 의미하는 애노테이션이다. 클래스가 스프링의 bean으로 등록되어 component scan을 통해 자동으로 인식된다.
//회원가입한 멤버들이 저장되는 레파지토리이다.
public class MemberRepository {
    private static final Map<String, Member> store = new HashMap<>();  // 멤버 저장소로 사용할 map 객체이다.

    public Member save(Member member){
        // 회원가입시 호출되는 메소드이다. map에 멤버의 아이디와 멤버객체를 저장한다.
        store.put(member.getId(),member);
        return member;
    }

    public Optional<Member> findById(String id){
        //인자로 받은 id와 일치하는 멤버를 반환한다. 없을 경우 null을 반환한다.
        return findAll().stream()
                .filter(m->m.getId().equals(id))
                .findFirst();
    }
    public List<Member> findAll(){
        // 맵의 value를 리스트로 만들어 반환한다.
        return new ArrayList<>(store.values());
    }

    public void deleteMember(String id){
        //구현해놓은 회원탈퇴 메소드이다.
        store.remove(id);
    }

}
