/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sermed.dal;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author joão.furtado
 */
public class EntityManagerFactoryUtil{
    private static final EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("estoquePU");
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
    public static void close(){
        factory.close();
    }
    
    
}
