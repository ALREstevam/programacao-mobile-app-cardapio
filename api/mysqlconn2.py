import mysql.connector
import json
import logging
import traceback
from mysql.connector import errorcode
from flask import jsonify
from pprint import pprint

class DbConn:
    def __init__(self):
        with open('dbauth.json', 'r') as file:
            self.auth = json.load(file)

        print('Database authentication file:')
        pprint(self.auth)


    def connect(self):
        return mysql.connector.connect(user=self.auth['user'], password=self.auth['password'], host=self.auth['host'], database=self.auth['database'], port=self.auth['port'])


    def select(self, query, objectName):
        connection = self.connect()
        try:
            cursor = connection.cursor()
            cursor.execute(query)
            return jsonify(self.dictfetchall(cursor))
        except:
            print(traceback.format_exc())
            return {'status': 'error'}
        finally:
            connection.close()


    def insertSingle(self, query):
        connection = self.connect()
        try:
            cursor = connection.cursor()
            cursor.execute(query)
            # Make sure data is committed to the database
            connection.commit()
            return {'status': 'success'}
        except:
            print(traceback.format_exc())
            return {'status': 'error'}
        finally:
            connection.close()

    def insertMulti(self, query):
        connection = self.connect()
        try:
            cursor = connection.cursor()
            for result in cursor.execute(query, multi=True):
                pass
            connection.commit()
            return {'status': 'success'}
        except:
            print(traceback.format_exc())
            return {'status': 'error'}
        finally:
            connection.close()

    def execute(self, query):
        connection = self.connect()
        try:
            cursor = connection.cursor()
            cursor.execute(query)
            return jsonify(self.dictfetchall(cursor))
        except:
            print(traceback.format_exc())
            return {'status': 'error'}
        finally:
            connection.close()

    def execute2(self, procedure, procargs):
        connection = self.connect()
        cursor = connection.cursor()
        cursor.callproc(procedure, procargs)

        res = []
        desc = []

        for result in cursor.stored_results():
            res += result.fetchall()
            desc += result.description


        rsp = jsonify([dict(zip([col[0] for col in desc], row)) for row in res])
        return rsp

    def executeMulti(self, query):
        try:
            connection = self.connect()
            cursor = connection.cursor(buffered=True, dictionary=True)

            # cursor.execute(query)
            for result in cursor.execute(query, multi=True):
                print("***************************")
                print(result)

            json_data = self.dictfetchall(cursor)
            return jsonify({'rsp': json_data})
        except:
            print(traceback.format_exc())
            return {'status': 'error'}


    def dictfetchall(self, cursor):
        """
        Returns all rows from a cursor as a list of dicts
        """
        desc = cursor.description
        rsp = [dict(zip([col[0] for col in desc], row)) for row in cursor.fetchall()]
        return rsp


