package info.androidhive.jsonparsing;

/**
 * Created by Francisco on 11/2/2015.
 */
public class Clientes {
    private int id;
    private String nombre, contenido, fecha;

    public Clientes(int id, String nombre, String contenido, String fecha){
        super();
        this.id = id;
        this.nombre = nombre;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public Clientes(String nombre, String contenido){
        super();
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getContenido(){
        return contenido;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }

    public String getFecha(){
        return fecha;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    @Override
    public String toString(){
        return this.nombre;
    }
}
