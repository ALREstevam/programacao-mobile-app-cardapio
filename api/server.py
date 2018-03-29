from flask import Flask, request, jsonify
from flask_restful import Resource, Api
#from sqlalchemy import create_engine
from json import dumps
import json
import mysqlconn
from pprint import pprint

app = Flask(__name__)
api = Api(app)


db = mysqlconn.DbConnection()


class Products(Resource):
    def get(self):
        query = "SELECT " \
                "product_id AS id, " \
                "product_name AS name, " \
                "product_type AS type, " \
                "product_description AS descr, " \
                "product_picture AS prodpic, " \
                "CAST(product_price AS CHAR) AS price " \
                "FROM product ORDER BY type, price"
        rsp = db.selectJson(query)
        return rsp



class Restaurant(Resource):
    def get(self):
        query = "SELECT table_id AS id, table_name AS name, maximum_chairs AS chairs FROM restaurant_table ORDER BY chairs"
        rsp = db.selectJson(query)
        return rsp


class Visit(Resource):
    def post(self):

        pprint(request.json)

        tabid = request.json['tableId']
        name = request.json['ClientName']
        clisex = request.json['ClientSex']
        peepontable = request.json['PeopleOnTable']


        query = "INSERT INTO visit (FK_table, client_name, client_sex, people_on_table) VALUES (%(tableid)s, %(cliname)s, %(clisex)s, %(peopontable)s)"

        data = {
            'tableid': tabid,
            'cliname': name,
            'clisex': clisex,
            'peopontable': peepontable,
        }

        rsp = db.insertByDict(query, data)
        return rsp


class Order(Resource):
    def post(self):
        pprint(request.json)

        visid = request.json['visitId']
        products = request.json['products']


        query = 'INSERT INTO client_order (order_id, FK_visit_id) VALUES (NULL, {});' \
                'SET @ordid = LAST_INSERT_ID();'.format(visid)



        for productId in products:
            query += 'INSERT INTO order_contains (FK_product_id, FK_order_id) VALUES ({}, @ordid);'.format(productId)

        print(query)
        return db.insertReadyMulti(query)

        



api.add_resource(Products, '/products') # Route_1
api.add_resource(Restaurant, '/restaurant') # Route_2
api.add_resource(Visit, '/visit') # Route_3
api.add_resource(Order, '/order') # Route_4


if __name__ == '__main__':
     app.run()
