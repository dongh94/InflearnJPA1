package hellojpa;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
        EntityManager em = emf.createEntityManager();
        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        // tx
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // code
            // JPQL
            Member member = new Member();
            member.setUsername("member100");
            member.setAge(100);
            em.persist(member);
            // 타입이 명확할 때
            TypedQuery<Member> query = em.createQuery("select m from Member as m ", Member.class);
            TypedQuery<String> query1 = em.createQuery("select m.username from Member as m", String.class);
            // 타입이 명확하지 않을 때,
            Query query2 = em.createQuery("select m.username, m.age from Member as m");

            // 결과값이 딱 하나일 때만 (: 이것은 바인딩)
            TypedQuery<Member> query3 = em.createQuery("select m from Member as m where m.username = :username", Member.class);
            query3.setParameter("username", "member100");
            Member singleResult = query3.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());

            // 체인 버젼
            Member singleResult1 = em.createQuery("select m from Member as m where m.username = :username", Member.class)
                    .setParameter("username", "member100")
                    .getSingleResult();
            System.out.println("singleResult1 = " + singleResult1.getUsername());

            // 타입이 명확하지 않을 때 2
            // Object 로
            List resultList = em.createQuery("select m.username, m.age from Member as m").getResultList();
            Object o = resultList.get(0);
            Object[] result = (Object[]) o;
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);

            // Object[] 로
            List<Object[]> resultList1 = em.createQuery("select m.username, m.age from Member as m").getResultList();
            Object[] objects = resultList1.get(0);
            System.out.println("username = " + objects[0]);
            System.out.println("age = " + objects[1]);

            // new 생성자로
            List<MemberDTO> resultList2 = em.createQuery("select new hellojpa.MemberDTO(m.username, m.age) from Member as m", MemberDTO.class).getResultList();
            MemberDTO memberDTO = resultList2.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

            em.flush();
            em.clear();

            // 페이징
            for (int i = 0; i < 100; i++) {
                Member member1 = new Member();
                member1.setUsername("member"+i);
                member1.setAge(i);
                em.persist(member1);
            }

            List<Member> resultList3 = em.createQuery("select m from Member as m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
            System.out.println("resultList3 = " + resultList3.size());
            for (Member m : resultList3) {
                System.out.println("member1 = " + m.getUsername());
            }

            em.flush();
            em.clear();
            // 조인
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member2 = new Member();
            member2.setUsername("member101");
            member2.setAge(101);
            member2.setTeam(team);

            em.persist(member2);
            String innerjoin = "select m from Member as m inner join m.team t";
            String leftouterjoin = "select m from Member as m left outer join m.team t";
            String setajoin = "select m from Member m, Team t where m.username = t.name";
            List<Member> resultList4 = em.createQuery(leftouterjoin, Member.class)
                    .getResultList();
            // code
            // tx
            tx.commit(); // (SQL 필요한거 싹다 보낸다.)
        } catch (Exception e) {
            e.printStackTrace();
            // tx
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}