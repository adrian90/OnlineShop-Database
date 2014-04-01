package Mappers;

import Interfaces.ICart;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.naming.NamingException;

@Stateless
public class UserProductMapper extends AbstractMapper implements Interfaces.ISaver{
    @Override
    protected String Statement(String clase){
        return "INSERT INTO productousuario VALUES (?,?)";
    }
    @Override
    public void saveProducts(ICart cart, String user) throws NamingException, SQLException {
        for (int i=0; i<cart.getContents().size();i++){
            abstractInsert(user, cart.getContents().get(i).getID());
        }
    }

    @Override
    protected Object doLoad(int id, ResultSet rs) throws SQLException, NamingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object doLoadB(int id, ResultSet rs) throws SQLException, NamingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
