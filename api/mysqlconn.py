import mysql.connector
import json
import logging
import traceback
from mysql.connector import errorcode
from flask import jsonify
from pprint import pprint

class DbConnection:
    """
    This class manages the connection and simple querys execution for a MySql database.
    The database authentication data should be into a `dbauth.json` file with a content like
    this:

    {
      "user": "root",
      "password": "",
      "host": "127.0.0.1",
      "database": "dbname",
      "port": "3306"
    }
    """
    def __init__(self):
        self.connection = None
        authFile = open('dbauth.json', 'r')
        self.dbAuth = json.load(authFile)
        print('Database authentication file:')
        pprint(self.dbAuth)

    def connect(self):
        """
        Creates a connection with the database
        """

        dbth = self.dbAuth
        try:
            self.connection = mysql.connector.connect(user=dbth['user'], password=dbth['password'],
                                                      host=dbth['host'], database=dbth['database'],
                                                      port=dbth['port'])
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print('Something is wrong with the user name or password')
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print('Database does not exist')
            else:
                print(err)
        except Exception as e:
            logging.error(traceback.format_exc())

    def closeConn(self):
        """
        Wrapper to close the local connection
        """
        return self.connection.close()

    def getConn(self):
        """
        Returns a valid connection
        """
        if not self.connection or not self.connection.open:
            self.connect()
            return self.connection
        else:
            return self.connection

    def selectJson(self, query):
        """
        Executes a SELECT query and returns
        the result in a json like string
        """
        try:
            connection = self.getConn()
            cursor = connection.cursor()
            cursor.execute(query)
            json_data = self.dictfetchall(cursor)
            return jsonify(json_data)
        except:
            print(traceback.format_exc())
            return {'status': 'error'}

    def execute(self, query):
        try:
            connection = self.getConn()
            cursor = connection.cursor()
            cursor.execute(query)
            json_data = self.dictfetchall(cursor)
            return jsonify(json_data)
        except:
            print(traceback.format_exc())
            return {'status': 'error'}


    def insertByDict(self, query, insertData):
        """
        Executes an INSERT query with data inside a dictionary
        """
        connection = self.getConn()
        cursor = connection.cursor()
        cursor.execute(query, insertData)
        # Make sure data is committed to the database
        connection.commit()
        return {'status': 'success'}

    def insertReady(self, query):
        """
        Executes an INSERT query with a already formatted query string
        """
        connection = self.getConn()
        cursor = connection.cursor()
        cursor.execute(query)
        # Make sure data is committed to the database
        connection.commit()
        return {'status': 'success'}

    def insertPreparedMulti(self, query):
        """
        Executes an INSERT command with multiple lines
        """
        connection = self.getConn()
        cursor = connection.cursor()
        for result in cursor.execute(query, multi=True):
            pass
        # Make sure data is committed to the database
        connection.commit()
        return {'status': 'success'}

    def dictfetchall(self, cursor):
        """
        Returns all rows from a cursor as a list of dicts
        """
        desc = cursor.description
        rsp = [dict(zip([col[0] for col in desc], row)) for row in cursor.fetchall()]
        return rsp

