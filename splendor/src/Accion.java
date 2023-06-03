import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Accion {
    public static boolean pass(int idPartida, int idJugador, Connection con) throws SQLException {
        int idNext = getNext(idPartida, con);
        if (idNext == idJugador) {
            CallableStatement cs;
            cs = con.prepareCall("call novaaccpass(?)");
            cs.setInt(1, idPartida);
            cs.execute();
            cs.close();
            return true;
        }
        return false;
    }

    public static boolean take2(Color color, int idPartida, int idJugador, Connection con) throws SQLException {
        int idNext = getNext(idPartida, con);
        if (idNext == idJugador) {
            CallableStatement cs = con.prepareCall("CALL take2(?,?)");
            cs.setInt(1, idPartida);
            cs.setString(2, String.valueOf(color));
            cs.execute();
            cs.close();
            return true;
        }
        return false;
    }

    public static boolean take3(Color color1, Color color2, Color color3, int idPartida, int idJugador, Connection con) throws SQLException {
        int idNext = getNext(idPartida, con);
        if (idNext == idJugador) {
            CallableStatement cs = con.prepareCall("Call take3(?,?,?,?)");
            cs.setInt(1, idPartida);
            cs.setString(2, String.valueOf(color1));
            cs.setString(3, String.valueOf(color2));
            cs.setString(4, String.valueOf(color3));
            cs.execute();
            cs.close();
            return true;
        }
        return false;
    }

    public static boolean Hold(String idCarta, int idJugador, int idPartida, Connection con) throws SQLException {
        int idNext = getNext(idPartida, con);
        if (idNext == idJugador) {
            CallableStatement cs = con.prepareCall("Call novaacchold(?,?)");
            cs.setInt(1, idPartida);
            cs.setString(2, idCarta);
            cs.execute();
            cs.close();
            return true;
        }
        return false;
    }

    public static boolean BlindHold(int nivel, int idJugador, int idPartida, Connection con) throws SQLException {
        int idNext = getNext(idPartida, con);
        if (idNext == idJugador) {
            CallableStatement cs = con.prepareCall("Call novaaccblindhold(?,?)");
            cs.setInt(1, idPartida);
            cs.setInt(2, nivel);
            cs.execute();
            cs.close();
            return true;
        }
        return false;
    }

    public static boolean buyCarta(String idCarta, int idJugador, int idPartida, Connection con) throws SQLException {
        int idNext = getNext(idPartida, con);
        if (idNext == idJugador) {
            CallableStatement cs = con.prepareCall("call novaaccbuycarta(?,?)");
            cs.setInt(1, idPartida);
            cs.setString(2, idCarta);
            cs.execute();
            cs = con.prepareCall("call novaaccbuynoble(?)");
            cs.setInt(1, idPartida);
            cs.close();
            return true;
        }
        return false;
    }

    private static int getNext(int idPartida, Connection con) throws SQLException {
        CallableStatement cs = con.prepareCall("call getaccnext(?,?,?)");
        cs.setInt(1, idPartida);
        cs.registerOutParameter(2, Types.INTEGER);
        cs.registerOutParameter(3, Types.INTEGER);
        cs.execute();
        return cs.getInt(2);
    }
}
