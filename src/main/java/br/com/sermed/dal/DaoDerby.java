/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sermed.dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author joão.furtado
 */
public class DaoDerby {

    private String url;
    private String username;
    private String password;

    public DaoDerby(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            return con;

        } catch (Exception e) {

        }
        return null;
    }
}
