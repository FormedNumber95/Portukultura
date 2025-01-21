package com.icjardinapps.dm2.portukultura

import android.content.Context
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Properties

/**
 * Clase que gestiona las operaciones con la base de datos utilizando configuración de conexión
 * almacenada en un archivo de propiedades.
 *
 * Proporciona métodos para obtener registros y guardar nuevos datos en las tablas de la base de datos.
 *
 * @author Aketza
 * @version 1.1
 */
class BD (context: Context) {
    private val dbUrl:String
    private val dbUser:String
    private val dbPassword:String


    init {
        // Crear instancia de Properties
        val properties = Properties()

        // Cargar el archivo .properties
        try {
            // Usar el Context para acceder a assets
            val inputStream = context.assets.open("config.properties")
            properties.load(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Obtener valores de las propiedades
        dbUrl = properties.getProperty("db_url") ?: throw IllegalArgumentException("db_url no definido")
        dbUser = properties.getProperty("db_user") ?: throw IllegalArgumentException("db_user no definido")
        dbPassword = properties.getProperty("db_password") ?: throw IllegalArgumentException("db_password no definido")
    }

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @author Aketza
     * @return Una conexión a la base de datos o null si ocurre un error.
     */
    fun obtenerConexion(): Connection? {
        return try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Inserta un nuevo registro en la tabla "alumno".
     *
     * @author Aketza
     * @param usuario El nombre de usuario.
     * @param nombre El nombre del alumno.
     * @param anioNacimiento El año de nacimiento del alumno.
     * @return true si la inserción fue exitosa, false si hubo algún error.
     */
    fun guardarAlumnoEnMariaDB(usuario: String, nombre: String, anioNacimiento: Int): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query =
                    "INSERT INTO alumno (usuario, nombre, año_nacimiento, idioma, fecha_alta, fecha_baja) VALUES (?, ?, ?, ?, ?, ?)"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setString(2, nombre)
                statement.setInt(3, anioNacimiento)
                statement.setString(4, null)
                statement.setDate(5, null)
                statement.setDate(6, null)
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    /**
     * Inserta un nuevo registro en la tabla "puntuacion".
     *
     * @author Aketza
     * @param usuario El nombre de usuario.
     * @return true si la inserción fue exitosa, false si hubo algún error.
     */
    fun guardarPuntuacion(usuario:String):Boolean{
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query =
                    "INSERT INTO puntuacion (usuario,id_aplicacion) VALUES (?, ?)"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setInt(2,1)
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }
}