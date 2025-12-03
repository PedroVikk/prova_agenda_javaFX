package org.prova1;

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
