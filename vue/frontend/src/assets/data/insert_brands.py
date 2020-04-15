import psycopg2
import json

path_to_file = './brands.json'
with open(path_to_file, 'r') as brand_file:
    brands = json.load(brand_file)	

try:
	conn = psycopg2.connect(database="tool_library", user="capstone_owner", password="capstone_owner1",host="127.0.0.1",port="5432")
	print ("opened  database successfully")
	cur = conn.cursor()

	for brand in brands:
		cur.execute("INSERT INTO brands (name) VALUES (%s)", (brand["brandName"],))
	print("successfully inserted brands")

except psycopg2.Error as e:
	raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")