from flask import Flask, request, jsonify
from flask_restful import Resource, Api
from json import dumps
import json
import mysqlconn2
from pprint import pprint
import requests

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


class Product(Resource):
    def post(self):
        prodid = request.json['productId']

        query = "SELECT " \
                "product_id AS produtctId, " \
                "product_name AS name, " \
                "product_type AS type, " \
                "product_description AS description, " \
                "product_picture AS picture, " \
                "CAST(product_price AS CHAR) AS price " \
                "FROM product WHERE product_id = {};".format(prodid);

        pprint(query)

        rsp = db.select(query, 'Product')

        return rsp



class Restaurant(Resource):
    def get(self):
        query = "SELECT table_id AS id, table_name AS name, maximum_chairs AS chairs FROM restaurant_table ORDER BY chairs"
        rsp = db.select(query, 'Restaurant')
        return rsp


class Visit(Resource):
    def post(self):

        #pprint(request.json)

        tabid = request.json['tableId']
        name = request.json['clientName']
        clisex = request.json['clientSex']
        peepontable = request.json['peopleOnTable']

        #query = "INSERT INTO visit (FK_table, client_name, client_sex, people_on_table) VALUES (%(tableid)s, %(cliname)s, %(clisex)s, %(peopontable)s)"
        #query = "CALL sp_new_visit({}, \"{}\", '{}', {});".format(tabid, name, clisex, peepontable)

        query = "sp_new_visit"
        data = [tabid, name, clisex, peepontable]
        rsp = db.execute2(query, data)
        return rsp


class OrderState(Resource):
    def post(self):
        return ""

        


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



class StatusUpdate(Resource):
    def post(self):
        orderContId = request.json['orderContainsId']
        orderStatusNum = request.json['orderStatus']

        orderStatus = "****"
        if(orderStatusNum == 0):
            orderStatus = "****"
        elif(orderStatusNum == 1):
            orderStatus = "Não pedido"
        elif(orderStatusNum == 2):
            orderStatus = "Enviando..."
        elif(orderStatusNum == 3):
            orderStatus = "Enviado para a cozinha"
        elif(orderStatusNum == 4):
            orderStatus = "Preparando"
        elif(orderStatusNum == 5):
            orderStatus = "Pronto!"
        elif(orderStatusNum == 6):
            orderStatus = "Entregue"
        elif(orderStatusNum == 7):
            orderStatus = "Pedido antigo (não considerado no pagamento)"

        db.insertSingle("UPDATE order_contains SET order_status=\"{}\" WHERE order_contains_id = {}".format(orderStatus, orderContId))


        query = "SELECT local_id, order_status, firebase_user_token FROM order_contains JOIN client_order ON order_contains.FK_order_id = client_order.order_id JOIN visit ON visit.visit_id = client_order.FK_visit_id WHERE order_contains.order_contains_id = {}".format(orderContId)
        print(query)

        dataResult = db.select(query, 'Dados');
        jsonData = dataResult.get_data()
        dictData = json.loads(jsonData)

        info = dictData[0]

        serverkey = "AAAA6Pfis6I:APA91bGyI5dYtWEUxibEPLeVqM2TpuCbTQ1oihFCC8mLnf5TAA0KMJ1flAE7lnIuz6CBizXAvcLVAqnYvAIEMunuVbEp26MnNjhVrDGvliwqDZBGnDJuDJaBjglfHPlw2M1yhM0TGs8i"
        userToken = info['firebase_user_token']
        url = 'https://fcm.googleapis.com/fcm/send'


        body = {  
        "data":{  
           "local_id":info['local_id'],
           "order_status":info['order_status']
        },
        "notification":{  
          "title":"Restaurante",
          "body":"Um prato está no estado \"{}\"".format(orderStatus) ,
          "content_available": "true"
        },
         "to":userToken
         }

        headers = {"Content-Type":"application/json","Authorization": "key={}".format(serverkey)}
        result = requests.post(url, data=json.dumps(body), headers=headers)

        print(result)

        return jsonify({"Result":str(result)})
    


api.add_resource(Products, '/products') # Route_1
api.add_resource(Restaurant, '/restaurant') # Route_2
api.add_resource(Visit, '/visit') # Route_3
api.add_resource(Order, '/order') # Route_4
api.add_resource(Test, '/test') # Route_5
api.add_resource(Product, '/product') # Route_6
api.add_resource(StatusUpdate, '/product_upadate_status') # Route_6


if __name__ == '__main__':
     app.run()
