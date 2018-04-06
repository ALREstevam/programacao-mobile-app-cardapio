from flask import Flask, request, jsonify
from flask_restful import Resource, Api
from json import dumps
import json
import mysqlconn2
from pprint import pprint

app = Flask(__name__)
api = Api(app)


#db = mysqlconn.DbConnection()
db = mysqlconn2.DbConn()

class Products(Resource):
    def get(self):
        query = "SELECT " \
                "product_id AS produtctId, " \
                "product_name AS name, " \
                "product_type AS type, " \
                "product_description AS description, " \
                "product_picture AS picture, " \
                "CAST(product_price AS CHAR) AS price " \
                "FROM product ORDER BY type, price"
        rsp = db.select(query, 'Product')
        return rsp



class Restaurant(Resource):
    def get(self):
        query = "SELECT table_id AS id, table_name AS name, maximum_chairs AS chairs FROM restaurant_table ORDER BY chairs"
        rsp = db.select(query, 'Restaurant')
        return rsp


class Visit(Resource):
    def post(self):

        pprint(request.json)

        tabid = request.json['tableId']
        name = request.json['ClientName']
        clisex = request.json['ClientSex']
        peepontable = request.json['PeopleOnTable']


        #query = "INSERT INTO visit (FK_table, client_name, client_sex, people_on_table) VALUES (%(tableid)s, %(cliname)s, %(clisex)s, %(peopontable)s)"
        #query = "CALL sp_new_visit({}, \"{}\", '{}', {});".format(tabid, name, clisex, peepontable)

        query = "sp_new_visit"
        data = [tabid, name, clisex, peepontable]
        rsp = db.execute2(query, data)
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
        return db.insertMulti(query)


class Test(Resource):
    def get(self):
        return jsonify({"Status":"success"})

api.add_resource(Products, '/products') # Route_1
api.add_resource(Restaurant, '/restaurant') # Route_2
api.add_resource(Visit, '/visit') # Route_3
api.add_resource(Order, '/order') # Route_4
api.add_resource(Test, '/test') # Route_5


if __name__ == '__main__':
     app.run()
