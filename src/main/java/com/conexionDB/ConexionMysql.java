package com.conexionDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class ConexionMysql {

    private String user="root";
    private String password="1234";
    private String bd="depositopf";
    private String ip="127.0.0.1";
    private String puerto="3306";
    private String cadena="jdbc:mysql://"+ip+":"+puerto+"/"+bd;




    private Connection connection;//Creando la variable que va a almacenar los datos de la coneccion
    private Statement statement; //Creando la variable Statement por si la necesitaba xd
    private  ResultSet resultSet;// Crseando la variable resultset por si la necesitaba xd
    private PreparedStatement preparedStatement; /// con esta variable inserto los datos en la db





    private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String formattedDate;




    public void createConexionMysql() {
        try {

            this.connection = DriverManager.getConnection(cadena, user, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createStatement() {
        try {
            this.statement= connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConectios(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void personaCsvToDB(){
        try {
            String cvsRoute = "TPFinalLabo2/src/personas.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cvsRoute));

            String linea;
            bufferedReader.readLine();

            String sqlQuery="INSERT INTO Personas(nombre,apellido,fkIdDomicilio,dni,tipoPersona,email,activo)  VALUES (?,?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlQuery);

            while ((linea = bufferedReader.readLine()) != null) {
                String[] data = linea.split(",");

                preparedStatement.setString(1, data[0]);
                preparedStatement.setString(2, data[1]);
                preparedStatement.setInt(3, Integer.parseInt(data[2]));
                preparedStatement.setString(4,data[3]);
                preparedStatement.setString(5,data[4]);
                preparedStatement.setString(6,data[5]);
                preparedStatement.setBoolean(7,Boolean.parseBoolean(data[6]));

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("las personas fueron cargadas correctamente en la base de datos.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cuentasCsvToDB(){
        try {
            String cvsRoute = "TPFinalLabo2/src/cuentas.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cvsRoute));

            String linea;
            bufferedReader.readLine();

            String sqlQuery="INSERT INTO Cuentas(fkIdPersona,saldo,tipoCuenta,activa)  VALUES (?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlQuery);

            while ((linea = bufferedReader.readLine()) != null) {
                String[] data = linea.split(",");

                preparedStatement.setInt(1,Integer.parseInt(data[0]));
                preparedStatement.setDouble(2, Double.parseDouble(data[1]));
                preparedStatement.setString(3, data[2]);
                preparedStatement.setBoolean(4,Boolean.parseBoolean(data[3]));

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("las cuentas fueron cargadas correctamente en la base de datos.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void balancesCsvToDB(){
        try {
            String cvsRoute = "TPFinalLabo2/src/balances.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cvsRoute));

            String linea;
            bufferedReader.readLine();

            String sqlQuery="INSERT INTO Balances(debe,haber,tipoCuenta,activa,descripcion,nombre,numeroDocumento)  VALUES (?,?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlQuery);

            while ((linea = bufferedReader.readLine()) != null) {
                String[] data = linea.split(",");

                preparedStatement.setDouble(1,Double.parseDouble(data[0]));
                preparedStatement.setDouble(2, Double.parseDouble(data[1]));
                preparedStatement.setString(3, data[2]);
                preparedStatement.setBoolean(4,Boolean.parseBoolean(data[3]));
                preparedStatement.setString(5, data[4]);
                preparedStatement.setString(6, data[5]);
                preparedStatement.setString(7, data[6]);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("los balances fueron cargados correctamente en la base de datos.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void movimientosCsvToDB(){
        try {
            String cvsRoute = "TPFinalLabo2/src/movimientos.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cvsRoute));

            String linea;
            bufferedReader.readLine();

            String sqlQuery="INSERT INTO Movimientos(fecha,fkIdCuenta,fkIdPedido,montoTotal,saldoAnterior,saldoModificado,descripcion)  VALUES (?,?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlQuery);

            while ((linea = bufferedReader.readLine()) != null) {
                String[] data = linea.split(",");


                // Convertir la fecha al formato correcto
                formattedDate = LocalDate.parse(data[0], inputFormatter).format(outputFormatter);

                preparedStatement.setDate(1, java.sql.Date.valueOf(formattedDate));
                preparedStatement.setInt(2, Integer.parseInt(data[1]));
                preparedStatement.setInt(3, Integer.parseInt(data[2]));
                preparedStatement.setDouble(4, Double.parseDouble(data[3]));
                preparedStatement.setDouble(5,Double.parseDouble(data[4]));
                preparedStatement.setDouble(6,Double.parseDouble(data[5]));
                preparedStatement.setString(7, data[6]);



                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("los movimientos fueron cargados correctamente en la base de datos.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void pedidosCsvToDB(){
        try {
            String cvsRoute = "TPFinalLabo2/src/pedidos.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cvsRoute));

            String linea;
            bufferedReader.readLine();

            String sqlQuery="INSERT INTO Pedidos(tipoDePedido,montoTotal,fkIdCuenta,ejecutado)  VALUES (?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlQuery);

            while ((linea = bufferedReader.readLine()) != null) {
                String[] data = linea.split(",");

                preparedStatement.setString(1, data[0]);
                preparedStatement.setDouble(2, Double.parseDouble(data[1]));
                preparedStatement.setInt(3, Integer.parseInt(data[2]));
                preparedStatement.setBoolean(4,Boolean.parseBoolean(data[3]));



                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("los Pedidos fueron cargados correctamente en la base de datos.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void productosCsvToDB(){
        try {
            String cvsRoute = "TPFinalLabo2/src/pedidos.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cvsRoute));

            String linea;
            bufferedReader.readLine();

            String sqlQuery="INSERT INTO Productos(nombreProd,marcaProd,categoriaProd,stock,precioDeCompra,precioDeVenta,FechaVen,porcentajeVenta,fkIdProvedor)  VALUES (?,?,?,?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlQuery);

            while ((linea = bufferedReader.readLine()) != null) {
                String[] data = linea.split(",");
                formattedDate = LocalDate.parse(data[6], inputFormatter).format(outputFormatter);


                preparedStatement.setString(1, data[0]);
                preparedStatement.setString(2, data[1]);
                preparedStatement.setString(3, data[2]);
                preparedStatement.setInt(4, Integer.parseInt(data[3]));
                preparedStatement.setDouble(5, Double.parseDouble(data[4]));
                preparedStatement.setDouble(6, Double.parseDouble(data[5]));
                preparedStatement.setDate(7, java.sql.Date.valueOf(formattedDate));
                preparedStatement.setDouble(8, Double.parseDouble(data[7]));
                preparedStatement.setInt(9, Integer.parseInt(data[8]));



                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("los Pedidos fueron cargados correctamente en la base de datos.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

/*

idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombreProd VARCHAR(50),
    marcaProd VARCHAR(50),
	categoriaProd ENUM ("CAT1","CAT2","CAT3"),
	stock INT,
    precioDeCompra DOUBLE,
    precioDeVenta DOUBLE,
    FechaVen VARCHAR(50),
    porcentajeVenta INT,
    fkIdProvedor INT NOT NULL,
	CONSTRAINT FOREIGN KEY(fkIdProvedor) REFERENCES proveedores(idProveedor)




idPedido int auto_increment primary key,
    tipoDePedido ENUM("VENTA","COMPRA","BAJA","ALTA","INTERNO"),
	montoTotal DOUBLE,
    fkIdCuenta INT NOT NULL,
    ejecutado BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT FOREIGN KEY (fkIdCuenta) REFERENCES cuenta(idCuenta)



Codigo de ejemplo para el resulset
try{


        resultSet= statement.executeLECQuery("SELECT * FROM USERS");
        while(resultSet.next()){
        System.out.println(resultSet.getString("user"));
        System.out.println(resultSet.getString("password"));
        }

        }catch(SQLException ex){
        ex.printStackTrace();
        }



metodo para crar tabla

          public void createTable() {
        try {
            this.resultSet = statement.executeQuery("SELECT * FROM Personas");
            while(resultSet.next()){
                System.out.println(resultSet.getString("idPersona"));
                System.out.println(resultSet.getString("nombre"));
                System.out.println(resultSet.getString("apellido"));
                System.out.println(resultSet.getString("fechaNac"));
                System.out.println(resultSet.getString("email"));
                System.out.println(resultSet.getString("dni"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    */