package jdbc;
import java.sql.*;
import java.util.Scanner;

public class JdbcCrudDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "@shree101";

        Scanner sc = new Scanner(System.in);

        try {
            // 1️⃣ Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Connect to Database
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Database Connected Successfully!");

            while (true) {
                System.out.println("\n==== Student Menu ====");
                System.out.println("1. Insert");
                System.out.println("2. Display");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        PreparedStatement ins = con.prepareStatement("INSERT INTO student VALUES(?,?,?)");
                        ins.setInt(1, id);
                        ins.setString(2, name);
                        ins.setInt(3, age);
                        ins.executeUpdate();
                        System.out.println("Record Inserted!");
                        ins.close();
                        break;

                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM student");
                        System.out.println("\nID\tName\tAge");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
                        }
                        rs.close();
                        st.close();
                        break;

                    case 3:
                        System.out.print("Enter ID to Update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Age: ");
                        int newAge = sc.nextInt();

                        PreparedStatement up = con.prepareStatement("UPDATE student SET name=?, age=? WHERE id=?");
                        up.setString(1, newName);
                        up.setInt(2, newAge);
                        up.setInt(3, uid);
                        up.executeUpdate();
                        System.out.println("Record Updated!");
                        up.close();
                        break;

                    case 4:
                        System.out.print("Enter ID to Delete: ");
                        int did = sc.nextInt();

                        PreparedStatement del = con.prepareStatement("DELETE FROM student WHERE id=?");
                        del.setInt(1, did);
                        del.executeUpdate();
                        System.out.println("Record Deleted!");
                        del.close();
                        break;

                    case 5:
                        System.out.println("Goodbye!");
                        con.close();
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}