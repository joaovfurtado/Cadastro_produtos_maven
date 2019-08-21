/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sermed.dal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


/**
 *
 * @author joão.furtado
 */
public class EntityManagerHelper<T> {
    @PersistenceContext(unitName = "estoquePU")
    private EntityManager entityManager;

    public EntityManagerHelper() {
        //this.entityManager = EntityManagerFactoryUtil.getFactory().createEntityManager();
    }

    public void close() {
        entityManager.close();
    }
    public boolean saveOrUpdate(T objeto) {
        try {
            entityManager.getTransaction().begin();
            if (idExist(objeto)) {
                entityManager.merge(objeto);
            } else {
                entityManager.persist(objeto);
            }
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {

        }
        return false;
    }

    private boolean idExist(T objeto) {
        try {
            return Arrays.asList(objeto.getClass().getDeclaredFields()).stream().filter(fld -> {
                fld.setAccessible(true);
                return fld.isAnnotationPresent(Id.class);
            }).anyMatch(fld -> {
                try {
                    fld.setAccessible(true);
                    return fld.get(objeto) != null && !"".equals(fld.get(objeto));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });
        } catch (Exception e) {
        }
        return false;
    }

    public void delete(T object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }

    public List<T> getList(String sql) {
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery(sql);
        List<T> l = q.getResultList();
        entityManager.getTransaction().commit();
        return l;
    }

    /**
     * faz uma pesquisa no banco usando uma named query
     *
     * @param name nome da named query
     * @param params caso nao nulo seta valores dos parametros na query
     * @return lista
     */
    public List<T> getListV2(String name, Map<String, Object> params) {
        entityManager.getTransaction().begin();
        Query q = entityManager.createNamedQuery(name);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
        List<T> l = q.getResultList();
        entityManager.getTransaction().commit();
        return l;
    }

}
