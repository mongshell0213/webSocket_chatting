package project.chattingService.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.chattingService.domain.member.Member;
import project.chattingService.domain.member.MemberRepository;

import java.util.Optional;

public class MemberRepositoryTest { // MemberRepository의 메소드를 테스트하는 코드입니다.
    private static final MemberRepository memberRepository = new MemberRepository();

    @Test
    public void saveFindTest(){
        Member member = new Member("1234","12345678"); //임의의 멤버를 생성합니다.
        memberRepository.save(member); //멤버를 리파지토리에 저장합니다.
        Assertions.assertThat(memberRepository.findById("1234").get()).isEqualTo(member);
        //멤버 리파지토리에서 아이디를 기반으로 멤버를 찾아오고, 생성해둔 멤버와 비교합니다. 일치할 경우 테스트가 통과됩니다.
    }

    @Test
    public void findAllTest(){
        Member m1 = new Member("1234","1234");
        Member m2 = new Member("5678","5678");
        memberRepository.save(m1);
        memberRepository.save(m2); //임의의 멤버 2개를 생성하여 리파지토리에 저장합니다.
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(2);
        //findAll()로 꺼내온 컬렉션의 사이즈가 2라면 테스트가 통과됩니다.
    }

    @Test
    public void deleteTest(){
        Member m1 = new Member("1234","1234");
        memberRepository.save(m1); //임의의 멤버를 생성하여 리파지토리에 저장합니다.
        memberRepository.deleteMember("1234"); //리파지토리에서 아이디를 기반으로 멤버를 삭제합니다.
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(0); //컬렉션의 사이즈가 0인 경우,
        Assertions.assertThat(memberRepository.findById("1234")).isEqualTo(Optional.empty());
        // 1234로 찾은 결과가 optional.empty()인 경우
        // 위 2가지 경우 모두 성립 시 테스트가 통과됩니다.
    }
}
