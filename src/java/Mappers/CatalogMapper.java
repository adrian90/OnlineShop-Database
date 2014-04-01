package Mappers;

import Clases.Album;
import Clases.Cancion;
import Clases.Product;
import Interfaces.IAlbum;
import Interfaces.ICatalog;
import Interfaces.IProduct;
import Interfaces.ISong;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.naming.NamingException;

@Stateful
public class CatalogMapper extends AbstractMapper implements ICatalog {
    private int NPages = 4;
    private int id = 0;
    private int Pos = 0;
    private int Npro = 4;
    private ArrayList<IAlbum> cat = new ArrayList<>();

    @Override
    protected String Statement(String clase){
        switch (clase){
            case "main":
                return "SELECT * " +
                    "FROM as.productos " +
                    "WHERE IsAlbum = true " +
                        "LIMIT ?,?";
            case "album":
                return "SELECT * " +
                    "FROM productos INNER JOIN albumsong " + 
                    "ON Id=idCancion AND idAlbum=?";
        }
        return "SELECT * " +
            "FROM as.productos " +
            "WHERE IsAlbum = true";
    }
    @Override
    public ICatalog findCat(int id, String clase) throws NamingException, SQLException {
        switch(id){
            case 1:
                Pos-=Npro;
                id=this.id+1;
                if(Pos<0){
                    Pos=0;
                    id=0;
                }
                break;
            case 2:
                Pos+=Npro;
                id=this.id-1;
                if (Pos > (NPages*Npro-1)){
                    Pos = Pos-Npro;
                    id=(-NPages)+1;
                }
                break;
            default:
                Pos = (-id)*Npro;
                break;
                
        }
        this.id=id;
        return (ICatalog) abstractFindCatalog(id, clase, Pos, Npro);
    }
    @Override
    public IAlbum findAlb(int id, String clase) throws NamingException, SQLException {
        return (IAlbum) abstractFindCatalog(id, clase, Pos, Npro);
    }

    @Override
    protected Object doLoad(int id, ResultSet rs) throws SQLException, NamingException{
        ICatalog cat = new CatalogMapper();
        while (rs.next()){
            IAlbum alb = new Album();
            alb.setID(rs.getInt(1));   
            alb.setName(rs.getString(2));    
            alb.setAuthor(rs.getString(3));
            alb.setPhoto(rs.getString(4));
            alb.setPrice(rs.getFloat(5));
            alb.setTime(rs.getTime(6));
            cat.setAddAlbum(alb);
        }
        cat.setID(id);
        return cat;
    }
    
    @Override
    protected Object doLoadB(int id, ResultSet rs) throws SQLException, NamingException{
        rs.next();
        IAlbum alb = new Album();
        alb.setID(rs.getInt(1));   
        alb.setName(rs.getString(2));    
        alb.setAuthor(rs.getString(3));
        alb.setPhoto(rs.getString(4));
        alb.setPrice(rs.getFloat(5));
        alb.setTime(rs.getTime(6));
        while (rs.next()){
            ISong Cancion = new Cancion();
            Cancion.setID(rs.getInt(1));   
            Cancion.setName(rs.getString(2));    
            Cancion.setAuthor(rs.getString(3));
            Cancion.setPrice(rs.getFloat(5));
            Cancion.setTime(rs.getTime(6));
            alb.setAddSong(Cancion);
        }
        return alb;
    }
    
    
    @Remove
    public void remove(){}

    @Override
     public void setAddAlbum(IAlbum alb){
         cat.add(alb);
     }
     
     @Override
     public void setID(int ID){
         id = ID;
     }
     @Override
     public void setPosition(int pos){
         this.Pos = pos;
     }
     
     @Override
     public void setNproducts(int npro){
         this.Npro = npro;
     }
     
     @Override
     public ArrayList getCatalog(){
         return this.cat;
     }
     
     @Override
     public IProduct getProduct(String name, float price){
        IProduct P = new Product();
        P.setName(name);
        P.setPrice(price);
        return P;
     }
     
     @Override
     public int getID(){
         return id;
     }
     
     @Override
     public int getPosition(){
         return this.Pos;
     }
     
     @Override
     public int getNproducts(){
         return this.Npro;
     }
     @Override
     public int getNPages(){
         return this.NPages;
     }
}
