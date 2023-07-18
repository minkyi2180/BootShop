//package com.mysite.prac.auth;
//
//import java.util.Optional;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.stereotype.Repository;
//
//import com.mysite.prac.user.SiteUser;
//
//import lombok.RequiredArgsConstructor;
//
//@Repository
//@RequiredArgsConstructor
//public class UserRepositoryImpl {
//
//	private static EntityManager em;
//	
//	public static Optional<SiteUser> findOneByEmail(String email){
//		return em.createQuery("select * from SiteUser  where email = :email", SiteUser.class)
//				.setParameter("email", email)
//				.getResultList()
//				.stream().findAny();
//	}
//	
//	public SiteUser save(SiteUser user) {
//		if(user.getId() == null) {
//			
//			em.persist(user);
//		} else {
//			em.merge(user);
//		}
//		return user;
//	}
//}
