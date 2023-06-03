import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        //falta crear un usuario jugador para que no pueda modificar algunas tablas
        Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.45/splendor", "root", "");
        //ip casa: 192.168.1.45 ip clase: 172.16.268.77
        // hay que modificar la ip cuando se a necesario
        Scanner sc = new Scanner(System.in);
        Jugador jugador = new Jugador(con);
        logOut(jugador);
        byte elc;
        do {
            System.out.println("1 - Crear una partida");
            System.out.println("2 - Unirte a la partida");
            System.out.println("3 - Info de la cuenta");
            System.out.println("4 - Logout");
            System.out.println("5 - Salir");
            elc = sc.nextByte();
            Partida p = new Partida(con, jugador.getId());//falta que se introduzca el nombre de la partida
            if (elc == 1) {//crear la partida
                p.crear();//aquí se crea la partida
                do {
                    System.out.println("Id: " + p.getIdPart());//se imprime el ID de partida para que otros jugadores se puedan unir
                    System.out.println("1 - Para empezar partida");
                    System.out.println("2 - Mostrar jugadores");
                    elc = sc.nextByte();
                    if (elc == 1) {
                        p.empezar();
                    } else if (elc == 2) {
                        p.jugadores();
                        System.out.println(p.getJug1());
                        System.out.println(p.getJug2());
                        System.out.println(p.getJug3());
                        System.out.println(p.getJug4());
                    }
                } while (elc != 1);

            } else if (elc == 2) {//unirse a una partida ya creada
                System.out.println("Introduce el ID de la partida");
                if (p.unirse(sc.nextInt())) {
                    System.out.println("Te has unido a la partida");
                    System.out.println("Espera a que el host de la partida le de a empezar");
                } else {
                    System.out.println("No ha sido posible unirse a la partida, la partida esta llena");
                }
            } else if (elc == 3) {//información de la cuenta en la que está registrado
                System.out.println(jugador.info());
            } else if (elc == 4) {//salir de la cuenta y entrar o crear en otra cuenta
                logOut(jugador);
            }
        } while (elc != 5);
        con.close();
    }

    public static void logOut(Jugador jugador) throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
        byte elc;
        do {
            System.out.println("1 - Entrar en un Usuario");
            System.out.println("2 - Crear un usuario");
            elc = sc.nextByte();
            if (elc == 1) {
                System.out.println("Introduce tu nombre de usuario");
                String nick = sc.next();
                System.out.println("Introduce tu contraseña");
                String password = sc.next();
                if (!jugador.logIn(nick, password)) {
                    System.out.println("nombre de usuario o contraseña no valido");
                }
            } else if (elc == 2) {
                System.out.println("Introduce un nombre de usuario");
                String nick = sc.next();
                System.out.println("Introduce tu correo electrónico");
                String email = sc.next();
                System.out.println("Introduce la contraseña");
                String password = sc.next();
                jugador.singUp(nick, email, password);
            }
        } while (jugador.getId() != null);
    }
}