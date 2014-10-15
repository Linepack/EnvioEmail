/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author DBS
 */
public class EntityManagerDAO {

    public static EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

}
