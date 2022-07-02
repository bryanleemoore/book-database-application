import java.sql.*;
import java.util.Scanner;

public class JDBCProject {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    static final String displayFormat="%-20s%-20s%-20s%-20s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";


    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    public static void listWritingGroups(Connection conn,Statement stmt)
    {
        try {
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT GroupName FROM WritingGroups";
                ResultSet rs = stmt.executeQuery(sql);

                //STEP 5: Extract data from result set
                System.out.println("Writing Groups:");
                while (rs.next()) 
                {
                    //Retrieve by column name
                    String name = rs.getString("GroupName");
                    System.out.println(name);
                }
                rs.close();
                stmt.close();
        }
       catch (Exception e) 
       {
            //Handle errors for Class.forName
            e.printStackTrace();
       }
    }
   public static void listPublishers(Connection conn,Statement stmt)
    {
       try {
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT PublisherName FROM Publisher";
                ResultSet rs = stmt.executeQuery(sql);

                //Extract data from result set
                System.out.println("Publishers:");
                while (rs.next()) 
                {
                    String pubname = rs.getString("PublisherName");
                    System.out.println(pubname);
                }
                rs.close();
                stmt.close();
        }
       catch (Exception e) 
       {
            e.printStackTrace();
       }
    }
   public static void listBooks(Connection conn,Statement stmt)
    {
        try {
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT BookTitle FROM Book";
                ResultSet rs = stmt.executeQuery(sql);

   
                System.out.println("Book titles:");
                while (rs.next()) 
                {
        
                    String title = rs.getString("BookTitle");
                    System.out.println(title);
                }
                rs.close();
                stmt.close();
        }
       catch (Exception e) {

            e.printStackTrace();
            }
    }
    public static void listSpecificWritingGroupData(Connection conn, PreparedStatement pstmt, String GroupName)
    {
        try 
        {
            
             String SQL = "SELECT * FROM WritingGroups INNER JOIN Book ON WritingGroups.GROUPNAME = Book.GROUPNAME INNER JOIN Publisher ON Book.PUBLISHERNAME = Publisher.PUBLISHERNAME WHERE WritingGroups.GROUPNAME = ?";
             pstmt = conn.prepareStatement(SQL);
             pstmt.setString(1, GroupName);
             ResultSet rs = pstmt.executeQuery();
                
            //STEP 5: Extract data from result set
            if(rs.next() != false) 
            {
                System.out.println("---------------------------");
                System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                //Retrieve by column name
                String gn = rs.getString("GroupName");
                String hw = rs.getString("HeadWriter");
                String yf = rs.getString("YearFormed");
                String sub = rs.getString("Subject");
                //Display values
                System.out.printf(displayFormat,
                        dispNull(gn), dispNull(hw), dispNull(yf), dispNull(sub));
                System.out.println("\nBooks by " + GroupName + ":");
          
                String format = "%-35s%-35s%-20s%-20s%-30s%-20s%-20s\n";
                System.out.printf(format, "Book Title", "Publisher Name", "Year Published", "Number Pages", "Publisher Address", "Publisher Phone", "Publisher Email");
                String title = rs.getString("BookTitle");
                String publisherName = rs.getString("PublisherName");
                String yearPublished = rs.getString("YearPublished");
                String numPages = rs.getString("NumberPages");
                String publisherAddress = rs.getString("PublisherAddress");
                String publisherPhone = rs.getString("PublisherPhone");
                String publisherEmail = rs.getString("PublisherEmail");
                System.out.printf(format,dispNull(title), dispNull(publisherName), dispNull(yearPublished), dispNull(numPages),dispNull(publisherAddress),
                            dispNull(publisherPhone),dispNull(publisherEmail) );
                while(rs.next() != false)
                {
                title = rs.getString("BookTitle");
                publisherName = rs.getString("PublisherName");
                yearPublished = rs.getString("YearPublished");
                numPages = rs.getString("NumberPages");
                publisherAddress = rs.getString("PublisherAddress");
                publisherPhone = rs.getString("PublisherPhone");
                publisherEmail = rs.getString("PublisherEmail");
                System.out.printf(format,dispNull(title), dispNull(publisherName), dispNull(yearPublished), dispNull(numPages),dispNull(publisherAddress),
                           dispNull(publisherPhone), dispNull(publisherEmail));
                }
                System.out.println("---------------------------");
            }
            else
            {
                rs.close();
                pstmt.close();
                
                
                
                SQL = "SELECT * FROM WritingGroups WHERE GroupName = ?";
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, GroupName);
                rs = pstmt.executeQuery();
                
 
                if(rs.next() == false) 
                {
                    System.out.println(GroupName + " is not a valid writing group, please try again!");
                    rs.close();
                    pstmt.close();
                    return;
                }
                else
                {
                    try
                    {
                        System.out.println("---------------------------");
                        System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                        //Retrieve by column name
                        String gn = rs.getString("GroupName");
                        String hw = rs.getString("HeadWriter");
                        String yf = rs.getString("YearFormed");
                        String sub = rs.getString("Subject");
                        //Display values
                        System.out.printf(displayFormat,
                                dispNull(gn), dispNull(hw), dispNull(yf), dispNull(sub));
                        System.out.println("\n" + GroupName + " has no books!");
                    }
                    catch(Exception e) 
                    {
                       pstmt.close();
                       return;
                    }
                }
            }
          rs.close();
          pstmt.close(); 
        }
       catch (Exception e) 
       {
            //Handle errors for Class.forName
            e.printStackTrace();
       }
    }
   public static void listSpecificPublisherData(Connection conn, PreparedStatement pstmt, String PublisherName)
    {
        try 
        {
            
             String SQL = "SELECT * FROM Publisher INNER JOIN Book ON Book.PUBLISHERNAME = Publisher.PUBLISHERNAME INNER JOIN WritingGroups ON "
                     + "WritingGroups.GROUPNAME = Book.GROUPNAME WHERE Publisher.PUBLISHERNAME = ?";
             pstmt = conn.prepareStatement(SQL);
             pstmt.setString(1, PublisherName);
             ResultSet rs = pstmt.executeQuery();
                
            //STEP 5: Extract data from result set
            if(rs.next() != false) 
            {
                String format = "%-35s%-30s%-20s%-20s\n";
                System.out.println("---------------------------");
                System.out.printf(format, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
                //Retrieve by column name
                String publisherName = rs.getString("PublisherName");
                String publisherAddress = rs.getString("PublisherAddress");
                String publisherPhone = rs.getString("PublisherPhone");
                String publisherEmail = rs.getString("PublisherEmail");
                //Display values
                System.out.printf(format,
                        dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
                System.out.println("\nBooks published by " + PublisherName + ":");
          
                format = "%-35s%-16s%-15s%-20s%-25s%-20s%-20s\n";
                System.out.printf(format, "Book Title", "Year Published", "Number Pages", "Group Name", "Head Writer", "Year Group Formed", "Subject" );
                String title = rs.getString("BookTitle");
                String yearPublished = rs.getString("YearPublished");
                String numPages = rs.getString("NumberPages");
                String groupName = rs.getString("GroupName");
                String headWriter = rs.getString("HeadWriter");
                String yearFormed = rs.getString("YearFormed");
                String subject = rs.getString("Subject");
                 System.out.printf(format,dispNull(title), dispNull(yearPublished), dispNull(numPages), dispNull(groupName),dispNull(headWriter),
                           dispNull(yearFormed), dispNull(subject));
                while(rs.next() != false)
                {
                    title = rs.getString("BookTitle");
                    yearPublished = rs.getString("YearPublished");
                    numPages = rs.getString("NumberPages");
                    groupName = rs.getString("GroupName");
                    headWriter = rs.getString("HeadWriter");
                    yearFormed = rs.getString("YearFormed");
                    subject = rs.getString("Subject");
                
                System.out.printf(format,dispNull(title), dispNull(yearPublished), dispNull(numPages), dispNull(groupName),dispNull(headWriter),
                           dispNull(yearFormed), dispNull(subject));
                }
                System.out.println("---------------------------");
            }
            else
            {
                rs.close();
                pstmt.close();
                
                
                
                SQL = "SELECT * FROM Publisher WHERE PublisherName = ?";
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, PublisherName);
                rs = pstmt.executeQuery();
                
 
                if(rs.next() == false) 
                {
                    System.out.println(PublisherName + " is not a valid publisher, please try again!");
                    rs.close();
                    pstmt.close();
                    return;
                }
                else
                {
                    try
                    {
                        String format = "%-35s%-30s%-20s%-20s\n";
                        System.out.println("---------------------------");
                        System.out.printf(format, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
                        //Retrieve by column name
                        String publisherName = rs.getString("PublisherName");
                        String publisherAddress = rs.getString("PublisherAddress");
                        String publisherPhone = rs.getString("PublisherPhone");
                        String publisherEmail = rs.getString("PublisherEmail");
                        //Display values
                        System.out.printf(format,
                                dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
                        System.out.println("\n" + PublisherName + " has no books!");
                        System.out.println("---------------------------");
                    }
                    catch(Exception e) 
                    {
                       pstmt.close();
                       return;
                    }
                }
            }
          rs.close();
          pstmt.close(); 
        }
       catch (Exception e) 
       {
            //Handle errors for Class.forName
            e.printStackTrace();
       }
    }
    public static void listSpecificBookData(Connection conn, PreparedStatement pstmt, String BookTitle, String WritingGroup)
    {
         try 
        {
            
             String SQL = "SELECT * FROM Book INNER JOIN Publisher ON Book.PUBLISHERNAME = Publisher.PUBLISHERNAME INNER JOIN "
                     + "WritingGroups ON WritingGroups.GROUPNAME = Book.GROUPNAME WHERE Book.BOOKTITLE = ? AND Book.GROUPNAME = ?";
             pstmt = conn.prepareStatement(SQL);
             pstmt.setString(1, BookTitle);
             pstmt.setString(2, WritingGroup);
             ResultSet rs = pstmt.executeQuery();
                
            //STEP 5: Extract data from result set
            if(rs.next() != false) 
            {
                System.out.println("---------------------------");                
                String format = "%-35s%-25s%-25s\n";
                System.out.printf(format, "Book Title", "Year Published", "Number Pages");
                String title = rs.getString("BookTitle");
                String yearPublished = rs.getString("YearPublished");
                String numPages = rs.getString("NumberPages");
                System.out.printf(format,
                        dispNull(BookTitle), dispNull(yearPublished), dispNull(numPages));
                

                
                System.out.println("\nBook, " + BookTitle + "'s Writing Group information: ");
                format = "%-20s%-25s%-20s%-20s\n";
                System.out.printf(format, "Group Name", "Head Writer", "Year Group Formed", "Subject" );
                String groupName = rs.getString("GroupName");
                String headWriter = rs.getString("HeadWriter");
                String yearFormed = rs.getString("YearFormed");
                String subject = rs.getString("Subject");
                 System.out.printf(format,dispNull(groupName),dispNull(headWriter),
                           dispNull(yearFormed), dispNull(subject));
                
                
                System.out.println("\nBook, " + BookTitle + "'s publisher information: ");
                format = "%-35s%-30s%-20s%-20s\n";
                System.out.printf(format, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
                //Retrieve by column name
                String publisherName = rs.getString("PublisherName");
                String publisherAddress = rs.getString("PublisherAddress");
                String publisherPhone = rs.getString("PublisherPhone");
                String publisherEmail = rs.getString("PublisherEmail");
                //Display values
                System.out.printf(format,
                        dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
                
      
          
                 
                 
            System.out.println("---------------------------");
               }
                
            else
            {
                System.out.println("There is no book title or writing group combination that you entered, please try again!");
            }
          rs.close();
          pstmt.close(); 
        }
       catch (Exception e) 
       {
            //Handle errors for Class.forName
            e.printStackTrace();
       }            
    }
    public static void insertBook(Connection conn, PreparedStatement pstmt, String BookTitle, String GroupName, String PublisherName, int YearPublished, int NumberPages)
    {
         try 
        {
            
            String SQL = "SELECT * FROM Book WHERE BookTitle = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, BookTitle);
            ResultSet rs = pstmt.executeQuery();
                
 
            if(rs.next() == true) 
            {
                System.out.println("Book name already exists, try again!");
                rs.close();
                pstmt.close();
                return;
            }
            else
            {
                rs.close();
                pstmt.close();
                try
                {
                    SQL = "INSERT INTO Book(GroupName, PublisherName, BookTitle, YearPublished, NumberPages) VALUES (?,?,?,?,?)";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, GroupName);
                    pstmt.setString(2, PublisherName);
                    pstmt.setString(3, BookTitle);
                    pstmt.setInt(4, YearPublished);
                    pstmt.setInt(5, NumberPages);
                    pstmt.executeUpdate();  
                }
                catch(Exception e) 
                {
                   System.out.println("Invalid Group Name and/or Publisher Name, please try again!");
                   pstmt.close();
                   return;
                }
            }
          System.out.println("Book inserted!");
          pstmt.close(); 
        }
       catch (Exception e) 
       {
            e.printStackTrace();
       }         
        
    }
    public static void insertPublisher(Connection conn, PreparedStatement pstmt, String PublisherName, String PublisherAddress, String PublisherPhone, String PublisherEmail, String oldPublisher)
    {
         try 
        {
            
            String SQL = "SELECT * FROM Publisher WHERE PublisherName = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, PublisherName);
            ResultSet rs = pstmt.executeQuery();
                
 
            if(rs.next() == true) 
            {
                System.out.println("Publisher already exists, please try again!");
                rs.close();
                pstmt.close();
                return;
            }
            else
            {
                rs.close();
                pstmt.close();
                
                
                
                SQL = "SELECT * FROM Publisher WHERE PublisherName = ?";
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, oldPublisher);
                rs = pstmt.executeQuery();
                
 
                if(rs.next() == false) 
                {
                    System.out.println("Publisher you want to replace does not exist, please try again!");
                    rs.close();
                    pstmt.close();
                    return;
                }
                else
                {
                    rs.close();
                    pstmt.close();
                    try
                    {
                        SQL = "INSERT INTO Publisher(PublisherName,PublisherAddress,PublisherPhone, PublisherEmail) VALUES (?,?,?,?)";
                        pstmt = conn.prepareStatement(SQL);
                        pstmt.setString(1, PublisherName);
                        pstmt.setString(2, PublisherAddress);
                        pstmt.setString(3, PublisherPhone);
                        pstmt.setString(4, PublisherEmail);
                        pstmt.executeUpdate();  
                        pstmt.close();

                        SQL = "UPDATE Book SET PublisherName = ? WHERE PublisherName = ?";
                        pstmt = conn.prepareStatement(SQL);
                        pstmt.setString(1, PublisherName);
                        pstmt.setString(2, oldPublisher);
                        pstmt.executeUpdate(); 
                        
                    }
                    catch(Exception e) 
                    {
                       pstmt.close();
                       return;
                    }
                }
            }
          System.out.println("Publisher inserted!");
          pstmt.close(); 
        }
       catch (Exception e) 
       {
            e.printStackTrace();
       }        
    }
    public static void removeBook(Connection conn, PreparedStatement pstmt, String BookTitle, String GroupName)
    {
       try 
       {
            String SQL = "SELECT * FROM Book WHERE BookTitle = ? AND GroupName = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, BookTitle);
            pstmt.setString(2, GroupName);
            ResultSet rs = pstmt.executeQuery();
                
    
            if(rs.next() == false) 
            {
                System.out.println("Book you want to remove does not exist with that book title and group name combintion, please try again!");
                rs.close();
                pstmt.close();
                return;
            }
            else
            {
                rs.close();
                pstmt.close();
                try
                 {
                   SQL = "DELETE FROM Book WHERE BookTitle = ? AND GroupName = ?";
                   pstmt = conn.prepareStatement(SQL);
                   pstmt.setString(1, BookTitle);
                   pstmt.setString(2, GroupName);
                   pstmt.executeUpdate(); 
                     
                 }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }  
            }
            pstmt.close();
            System.out.println("Book removed!");
       }
       catch (Exception e) 
       {
            e.printStackTrace();
       }    
    }
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        PreparedStatement pstmt = null;
        //Constructing the database URL connection string
        if(USER.length() < 1)
        {
            DB_URL = DB_URL + DBNAME;
        }
        else
        {
            DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        }
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Select an option from the menu:");      
            //STEP 4: Execute a query
            Boolean quit = false;
            while(quit == false)
            {
                System.out.println("----MENU----");
                System.out.println("1. List all writing groups");
                System.out.println("2. List all data for a specific group");
                System.out.println("3. List all publishers");
                System.out.println("4. List all the data for a specific publisher");
                System.out.println("5. List all book titles");
                System.out.println("6. List all data for a specific book");
                System.out.println("7. Insert a new book");
                System.out.println("8. Insert a new publisher, update all books by one publisher to be published by this new publisher");
                System.out.println("9. Remove a specific book");
                System.out.println("10. Quit");
                System.out.println("Select an option from the menu:");

                int input = -1;
                    
                 try
                    {
                            input = in.nextInt();
                            if(input < 1 || input > 10)
                    {
                                System.out.println("Must be an integer between 1-10 please try again!");
                     }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Must be an integer between 1-10 please try again!");    
                    }
                 
                String selection;
                String BookTitle;
                String GroupName;
                String PublisherName;
                int YearPublished;
                int NumberPages;
                in.nextLine();
                switch(input)
                {
                    case 1:
                        listWritingGroups(conn,stmt);
                        break;
                    case 2:
                        //
                        System.out.println("Which group's data would you like to list:");
                        selection = in.nextLine();
                        listSpecificWritingGroupData(conn,pstmt,selection);
                        
                        break;                        
                    case 3:
                        listPublishers(conn, stmt);
                        break; 
                    case 4:
                        System.out.println("Which publishers's data would you like to list:");
                        selection = in.nextLine();
                        listSpecificPublisherData(conn,pstmt,selection);
                        break; 
                    case 5:
                        listBooks(conn, stmt);
                        break; 
                    case 6:
                        System.out.println("Please enter a book title:");
                        selection = in.nextLine();
                        System.out.println("Please enter the writing group for that book:");
                        String selection2 = in.nextLine();
                        listSpecificBookData(conn,pstmt,selection,selection2);
                        break; 
                    case 7:
                        System.out.println("Please enter a book title:");
                        BookTitle = in.nextLine();
                        System.out.println("Please enter a group name:");
                        GroupName = in.nextLine();
                        System.out.println("Please enter a publisher name");
                        PublisherName = in.nextLine();
                        System.out.println("Please enter year published:");
                        try
                        {
                            YearPublished = in.nextInt();
                            if(YearPublished < 0)
                            {
                                System.out.println("Must be an integer greater than 0, please restart and try again!");
                                break;
                            }
                        }
                        catch(Exception e)
                        {
                             System.out.println("Must be an integer greater than 0, please restart and try again!");    
                             break;
                        }
                        System.out.println("Please enter a number of pages:");
                        try
                        {
                            NumberPages = in.nextInt();
                            if(NumberPages < 0)
                            {
                                System.out.println("Must be an integer greater than 0, please restart and try again!");
                                break;
                            }
                        }
                        catch(Exception e)
                        {
                             System.out.println("Must be an integer greater than 0, please restart and try again!");    
                             break;
                        }
                        insertBook(conn, pstmt, BookTitle, GroupName, PublisherName, YearPublished, NumberPages);

                        
                        break; 
                    case 8: 
                        System.out.println("Please enter a publisher name:");
                        PublisherName = in.nextLine();
                        System.out.println("Please enter a address:");
                        String PublisherAddress = in.nextLine();
                        System.out.println("Please enter a phone number");
                        String PublisherPhone = in.nextLine();
                        System.out.println("Please enter a email:");
                        String PublisherEmail = in.nextLine();
                        System.out.println("Please enter the publisher you want to replace:");
                        String oldPublisher = in.nextLine();
                        insertPublisher(conn, pstmt, PublisherName, PublisherAddress, PublisherPhone, PublisherEmail, oldPublisher);
                        break; 
                    case 9:
                        System.out.println("Please enter a book title of the book you want to remove:");
                        BookTitle = in.nextLine();
                        System.out.println("Please enter the writing group for that book:");
                        GroupName = in.nextLine();
                        removeBook(conn,pstmt,BookTitle,GroupName);
                        break; 
                    case 10:
                        quit = true;
                        break;
                    default:
                        break;
                }
            }
            //Clean up enviorment
            conn.close();
            in.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}