package Mappers;

import Connection.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;


public abstract class AbstractMapper {
    PreparedStatement findResult=null;
    PreparedStatement insertResult=null;
    ResultSet rs=null;
    protected Map loadedMap = new HashMap();

    
    abstract protected String Statement(String clase);
    
    protected Object abstractFindCatalog(int id, String clase, int pos, int npro) throws NamingException, SQLException{       

        Object result = (Object) loadedMap.get(id);
        if(result != null) return result;
        
        Database bd = new Database();
        bd.connect();
        Connection con = bd.getConnection();
        
        findResult = con.prepareStatement(Statement(clase));
        if (clase.equals("album")){
            findResult.setInt(1,id);
        }
        else {
            findResult.setInt(1, pos);
            findResult.setInt(2, npro);
        }
        rs = findResult.executeQuery();
        
        result = load(rs, id, clase);
        
        rs.close();
        findResult.close();
        con.close();
        
        return result;
    }
    
    protected Object load(ResultSet r, int id, String clase) 
            throws SQLException, NamingException{

        Object result = (Object) loadedMap.get(id);
        if(result != null) return result;
        if (clase.equals("main")){
            result = doLoad(id, r);
        }
        else result = doLoadB(id, r);
        loadedMap.put(id, result);
        return result;
    }
    
    abstract protected Object doLoad(int id, ResultSet rs) throws SQLException, NamingException;
    abstract protected Object doLoadB(int id, ResultSet rs) throws SQLException, NamingException;
    
    protected void abstractInsert(String user, int idproduct) throws SQLException{
        Database bd = new Database();
        bd.connect();
        Connection con = bd.getConnection();
        
        insertResult = con.prepareStatement(Statement(user));
        insertResult.setString(1, user);
        insertResult.setInt(2, idproduct);
        insertResult.executeUpdate();
        
        insertResult.close();
        con.close();
     }
    
     protected Object abstractFindUser(String user, String pass, String clase) 
             throws NamingException, SQLException{       
        int id = 0;
        Database bd = new Database();
        bd.connect();
        Connection con = bd.getConnection();
        Object result;
        findResult = con.prepareStatement(Statement(clase));
        if (clase.equals("login")){ 
            findResult.setString(1, user);
            findResult.setString(2, pass);
            rs = findResult.executeQuery();
            result = doLoad(id ,rs);
        }
        else {
            findResult.setString(1, user);
            rs = findResult.executeQuery();
            result = doLoadB(id ,rs);
        }   
        
        rs.close();
        findResult.close();
        con.close();
        
        return result;
    }
}
