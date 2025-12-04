package org.prova1;

import org.prova1.database.Database;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            var conn = Database.getConnection();
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
