import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbHandler {

    private String CON_STR = ""; //""jdbc:sqlite:C:/prod/myfin.db";
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance(String CON_STR) throws SQLException {
        if (instance == null)
            instance = new DbHandler(CON_STR);
        return instance;
    }

    private Connection connection;

    private DbHandler(String CON_STR) throws SQLException {
        this.CON_STR = CON_STR;
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<FileListWithCounter> getAllProducts(String tableName) {

        try (Statement statement = this.connection.createStatement()) {
            List<FileListWithCounter> fileListWithCounters = new ArrayList<FileListWithCounter>();
            ResultSet resultSet = statement.executeQuery("SELECT fileName, filePostsCount FROM " + tableName);

            while (resultSet.next()) {
                fileListWithCounters.add(new FileListWithCounter(resultSet.getString("fileName"),
                        resultSet.getInt("filePostsCount")
                ));
            }
            // Возвращаем наш список
            return fileListWithCounters;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    public void addFileListWithCounter(FileListWithCounter fileListWithCounter, String tableName) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT OR IGNORE INTO " + tableName + "(`fileName`, `filePostsCount`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, fileListWithCounter.fileName);
            statement.setObject(2, fileListWithCounter.filePostsCount);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteFileFromDB(int fileId, String tableName) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM " + tableName + " WHERE fileId = ?")) {
            statement.setObject(1, fileId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName){
        /*String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + " id integer PRIMARY KEY,\n"
                + "	good text NOT NULL,\n"
                + "	price real NOT NULL,\n"
                + "	category_name text NOT NULL\n"
                + ");";*/


        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	fileName text NOT NULL,\n"
                + " filePostsCount integer NULL,\n"
                + " UNIQUE(fileName));";


        ///ResultSet resultSet = statement.executeQuery("SELECT id, good, price, category_name FROM products");

        try (
             Statement stmt = this.connection.createStatement()) {
            // create a new table
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableWithDate(String tableName){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	fileId integer PRIMARY KEY,\n"
                + "	fileName text NOT NULL,\n"
                + " filePostDate TIMESTAMP NOT NULL,\n"
                + " UNIQUE(fileName));";

        try (
                Statement stmt = this.connection.createStatement()) {
            // create a new table
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFileWithDate(FileWithDateWhenItPosted fileWithDateWhenItPosted, String tableName) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT OR IGNORE INTO " + tableName + "(`fileName`, `filePostDate`) " +
                        "VALUES(?, CURRENT_TIMESTAMP)")) {
            statement.setObject(1, fileWithDateWhenItPosted.fileName);
            //statement.setObject(2, fileWithDateWhenItPosted.filePostDate);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FileWithDateWhenItPosted returnLastFileWithDateWhenItPosted(String tableName){

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY filePostDate ASC LIMIT 1");

            //System.out.println(resultSet.getString("fileName"));
            var s = resultSet.getString("fileName");
            String[] result = s.split(" ");
            return new FileWithDateWhenItPosted(resultSet.getInt("fileId"), resultSet.getString("fileName"), resultSet.getString("filePostDate"));


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FileWithDateWhenItPosted> returnLastPostWithDateWhenItPosted(String tableName){

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY filePostDate ASC LIMIT 1");

            System.out.println(resultSet.getString("filenames"));

            FileWithDateWhenItPosted lklklkl =  new FileWithDateWhenItPosted(resultSet.getInt("fileId"), resultSet.getString("filenames"), resultSet.getString("filePostDate"));

            System.out.println(lklklkl.fileName.split(" "));

        return null;



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDateToCurrent(FileWithDateWhenItPosted fileWithDateWhenItPosted, String tableName){
        System.out.println("endlessDir db clean: " + fileWithDateWhenItPosted + ":" + tableName);

        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE " + tableName + " SET filePostDate = CURRENT_TIMESTAMP WHERE fileId = ?;" )) {
            statement.setObject(1, fileWithDateWhenItPosted.fileId);
            //statement.setObject(2, fileWithDateWhenItPosted.filePostDate);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDatabaseMetaData(){
        try {

            DatabaseMetaData dbmd = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}