package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import lombok.Value;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBHelper {
    private static String dbUrl = System.getProperty("db.url");
    private static String dbUser = System.getProperty("db.user");
    private static String dbPass = System.getProperty("db.pass");

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    @SneakyThrows
    public static void clearDB() {
        var runner = new QueryRunner();
        var cleanOrder = "DELETE FROM order_entity;";
        var cleanPayment = "DELETE FROM payment_entity;";
        var cleanCredit = "DELETE FROM credit_request_entity;";
        try (
                var conn = getConnection();
        ) {
            runner.update(conn, cleanOrder);
            runner.update(conn, cleanPayment);
            runner.update(conn, cleanCredit);
        }
    }

    @SneakyThrows
    public static String getStatus(String tableName) {
        var codeSQL = "SELECT status FROM " + tableName + " order by created DESC limit 1;";
        var runner = new QueryRunner();
        try (
                var conn = getConnection();
        ) {
            var status = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return status;
        }

    }

    @SneakyThrows
    public static String getID(String columnName, String tableName) {
        var codeSQL = "SELECT " + columnName + " FROM " + tableName + " order by created DESC limit 1;";
        var runner = new QueryRunner();
        try (
                var conn = getConnection();
        ) {
            var id = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return id;
        }

    }


    public static String getTransactionPaymentStatus() {
        String status = getStatus("payment_entity");
        return status;
    }

    public static String getTransactionCreditRequestStatus() {
        String status = getStatus("credit_request_entity");
        return status;
    }

    public static String getTransactionId() {
        String transactionId = getID("transaction_id", "payment_entity");
        return transactionId;
    }

    public static String getBankId() {
        String bankId = getID("bank_id", "credit_request_entity");
        return bankId;
    }

    public static String getPaymentId() {
        String paymentId = getID("payment_id", "order_entity");
        return paymentId;
    }

    public static String getCreditId() {
        String creditId = getID("credit_id", "order_entity");
        return creditId;
    }
}