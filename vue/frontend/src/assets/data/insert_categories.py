import psycopg2
import json

path_to_file = './categories.json'
with open(path_to_file, 'r') as category_file:
    categories = json.load(category_file)

try:
	conn = psycopg2.connect(database="tool_library", user="capstone_owner", password="capstone_owner1",host="127.0.0.1",port="5432")
	print ("opened  database successfully")
	cur = conn.cursor()

	for category in categories:
		cur.execute("INSERT INTO category (name) VALUES (%s)", (category["categoryName"],))
	print("successfully inserted categories")

except psycopg2.Error as e:
	raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")