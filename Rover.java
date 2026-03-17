import java.util.ArrayList;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Rover{
    
    private String codigo;
    private String nombre;
    private double potenciaInicial;
    private double potenciaDisponible;
    private int recargasDisponibles = 5;
    private int registroRecargas;
    private int deteccionesCalor;
    private int posicionInicialX;
    private int posicionInicialY;
    private int posicionX;
    private int posicionY;
    private ArrayList<String> listaMandatosExitosos;
    private ArrayList<String> listaMandatosFallidos;
    private static int totalRovers;
    private double costoMovimiento = 0.5;
    private double costoDeteccionCalor = 0.25;
    
    public Rover(String nombre){
        this(nombre, 100);
    }
    
    public Rover(String nombre, double potencia) {
        this.nombre = nombre;
        this.potenciaInicial = potencia;
        this.potenciaDisponible = potencia;
        this.listaMandatosExitosos = new ArrayList<>();
        this.listaMandatosFallidos = new ArrayList<>();
        totalRovers = totalRovers + 1;
        this.codigo = "R" + totalRovers;
    }
    
    public void moverAdelante(){
        if (!validarPotenciaActual()){
            registrarMandato("moverAdelante", "no posible");
            return;
        }
        if (detectarFugaCalor()){
            registrarMandato("moverAdelante", "no posible");
            return;
        }
        
        posicionY += 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("moverAdelante", "exitoso");
    }
    
    public void moverAtras(){
        if(!validarPotenciaActual()){
            registrarMandato("moverAtras", "no posible");
            return;
        }
        if(detectarFugaCalor()){
            registrarMandato("moverAtras", "no posible");
            return;
        }
        
        posicionY -= 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("moverAtras", "exitoso");  
    }
    
    public void moverDerecha(){
        if(!validarPotenciaActual()){
            registrarMandato("moverDerecha", "no posible");
            return;
        }
        if(detectarFugaCalor()){
            registrarMandato("moverDerecha", "no posible");
            return;
        }
        
        posicionX += 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("moverDerecha", "exitoso");
        
    }
    
    public void moverIzquierda(){
        if(!validarPotenciaActual()){
            registrarMandato("moverIzquierda", "no posible");
            return;
        }
        if(detectarFugaCalor()){
            registrarMandato("moverIzquierda", "no posible");
            return;
        }
        
        posicionX -= 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("moverIzquierda", "exitoso");
    }
    
    public boolean detectarFugaCalor(){
        if( potenciaDisponible < costoDeteccionCalor) {
            return false;
        }
        potenciaDisponible -= costoDeteccionCalor;
        deteccionesCalor = deteccionesCalor + 1;
        
        Random random = new Random();
        double numero = random.nextDouble();
        
        return numero >= 0.5;
    }

    public String getPosicionActual(){
        return "(" + posicionX + "," + posicionY + ")";
    }
    
    public double getPotenciaDisponible(){
        return potenciaDisponible;
    }
    
    public void recargaPotencia(double unidades) {
        if(!validarRecargas()){
            registrarMandato("recargar", "no posible");
            return;
        }
        potenciaDisponible += unidades;
        registroRecargas = registroRecargas + 1;
        registrarMandato("recargar", "exitoso");
    }
    
    public boolean validarPotenciaActual(){
        return potenciaDisponible >= costoMovimiento;
    }
    public boolean validarRecargas(){
        return registroRecargas < recargasDisponibles;
    }
    
    public String obtenerEstadoRover(){
        return "Codigo: " + codigo +
        "Nombre: " + nombre +
        "Potencia inicial: " + potenciaInicial +
        "Potencia disponible: " + potenciaDisponible +
        "Recargas realizadas: " + registroRecargas +
        "Detecciones de calor: " + deteccionesCalor +
        "Posicion Inicial: " + "(" + posicionInicialX + "," +posicionInicialY + ")" +
        "Posicion actual: " + "(" + posicionX + "," + posicionY + ")";
    }
    
    public int obtenerCantidadRovers(){
        return totalRovers;
    }
    
    public void registrarMandato(String tipoMandato, String estatusMandato){
        
        String registro = tipoMandato + " - " + estatusMandato;
        if(estatusMandato.equals("exitoso")){
            listaMandatosExitosos.add(registro);
        } else {
            listaMandatosFallidos.add(registro);
        }
    }
}
