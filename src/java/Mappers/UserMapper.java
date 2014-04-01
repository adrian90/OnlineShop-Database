package Mappers;

import Clases.Product;
import Interfaces.IProduct;
import Interfaces.IUser;
import Interfaces.UserInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
public class UserMapper extends AbstractMapper implements IUser {
    @Override
    protected String Statement(String clase){
        if (clase.equals("login"))
            return "SELECT * FROM usuarios WHERE Nick = ? AND Password = ?";
        else {
            return "SELECT * FROM productos INNER JOIN productousuario "
                    + "ON idProduct = Id AND idUser= ?";
        }
    }   
    //Función que carga el usuario
    @Override
    public UserInterface findUser(String user, String pass, String clase)
            throws NamingException, SQLException {
        return (UserInterface) abstractFindUser(user, pass, clase);
    }
    //Función que carga los productos comprados del usuario
    @Override
    public ArrayList<IProduct> findProducts(String user, String pass, String clase) 
            throws NamingException, SQLException {
        return (ArrayList<IProduct>) abstractFindUser(user, pass, clase);
    }

    @Override
    protected Object doLoad(int id, ResultSet rs) throws SQLException, NamingException{
        InitialContext ctx = new InitialContext();
        UserInterface user = (UserInterface)ctx.lookup("java:global/TiendaOnline"
                + "/TiendaOnline-ejb/User!Interfaces.UserInterface");
        rs.next();
        if ( rs.getRow() != 0 ) {
            user.setNick(rs.getString(1));
            user.setPassword(rs.getString(2));    
            user.setCorreo(rs.getString(3));
            user.setNombre(rs.getString(4));
            user.setApellidos(rs.getString(5));
            user.setEdad(rs.getInt(6));
            user.setPais(rs.getString(7));
            user.setLogin();
        }
        return user;
    }

    @Override
    protected Object doLoadB(int id, ResultSet rs) 
            throws SQLException, NamingException {
        ArrayList<IProduct> products = new ArrayList<IProduct>();
        while (rs.next()){
            IProduct pro = new Product();
            pro.setID(rs.getInt(1));   
            pro.setName(rs.getString(2));    
            pro.setAuthor(rs.getString(3));
            pro.setPrice(rs.getFloat(5));
            pro.setTime(rs.getTime(6));
            products.add(pro);
        }
        return products;
    }
}
