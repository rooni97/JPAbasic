package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] arg) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("city1", "street", "10000"));
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            Team team = new Team();
            team.setName("dream");
            em.persist(team);

            Member newMember = new Member();
            newMember.setUsername("newMember");
            newMember.setTeam(team);
            em.persist(newMember);

            em.flush();
            em.clear();


            Member newFindMember = em.find(Member.class, newMember.getId());
            Team getTeam = newFindMember.getTeam();
            System.out.println("getTeam.getClass() = " + getTeam.getClass());

            System.out.println("=====================start====================");

            System.out.println("getTeam.getName() = " + getTeam.getName());

            System.out.println("=====================end====================");

            System.out.println("getTeam.getClass() = " + getTeam.getClass());

//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass());
//
//            em.detach(refMember);
////            em.clear();
//
//            System.out.println("refMember.getUsername() = " + refMember.getUsername());
            
//            findMember.getHomeAddress().setCity("noew"); XXXXXXX
//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));
//
//            // 치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10300"));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
