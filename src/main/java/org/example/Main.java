package org.example;

import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        // Access token
        pgSimpleDataSource.setUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTg5NzY3MzQsImlhdCI6MTcxODk3MzEzNCwidXNlcklkIjoxfQ.jqjuwWK3t21eDfb8L_olhiDFXzRu4PEFfXol2vOLPIY");
        // Don't care
        pgSimpleDataSource.setPassword("");
        // Proxy host
        pgSimpleDataSource.setServerNames(new String[]{"localhost"});
        // Proxy port
        pgSimpleDataSource.setPortNumbers(new int[]{8082});
        // Datasource ID
        pgSimpleDataSource.setDatabaseName("1");

        pgSimpleDataSource.setSslMode("disable");
        System.out.println(pgSimpleDataSource.getURL());
        Connection connection = pgSimpleDataSource.getConnection();
        System.out.println(connection.getSchema());


        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
            statement.execute("CREATE TABLE  example (id SERIAL PRIMARY KEY, name VARCHAR NOT NULL)");
        } catch (PSQLException e) {
            System.out.println(e);
        }

        ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), connection.getSchema(), null, null);
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_NAME"));
        }
        rs.close();
        connection.close();
    }
}